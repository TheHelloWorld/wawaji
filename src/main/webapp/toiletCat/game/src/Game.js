var ufoAll,shadow;
var Event  = Laya.Event;
var Socket = Laya.Socket;
var Byte   = Laya.Byte;
var toySize = [0,0];
var toyShadow = [];var toyShadowCurPos = [];
var toyImgCreate = [];
var socket,failInfoObj,zzcObj,successInfoObj;
var setInitData;
var uId;
var rId;
var coin;
var time1;
var toyImg = new Laya.Sprite();
var toyNo;
var toyNowCoin;
var toyName;
var toyRoomImg;
var luckyNum;
var catchId;
var gameStatus=0;
var userNo;
var canPlayMyBroadcast = 1;

var toyinfo_position = [[120,430,70],[310,430,70],[230,530,75],[420,530,75]];
var defaultSize=[185, 235];
var addX = addY = 0;
var broadcastSuccessList = [];

    connect();

    function connect()
	{
		socket = new Socket();
		socket.connectByUrl("ws://45.76.51.107:20002");

		output = socket.output;

		socket.on(Event.OPEN, this, onSocketOpen);
		socket.on(Event.CLOSE, this, onSocketClose);
		socket.on(Event.MESSAGE, this, onMessageReveived);
		socket.on(Event.ERROR, this, onConnectError);
	}

	function onSocketOpen()
	{
		uId = getQueryString('userNo');
        rId = getQueryString('gameRoomNo');
        userName = sessionStorage["toiletCatUserName"];
        userImg = sessionStorage["toiletCatUserImg"];

        var json = {};
        json["type"] = "init";
        json["uId"] = uId;
        json["rId"] = rId;

        socket.send(JSON.stringify(json));
	}

	function onSocketClose()
	{
		console.log("Socket closed");
	}

	function onMessageReveived(message)
	{
		data = eval('('+message+')');
        
        if(data.type == 'init'){
            setInitData = data;
            setInit(data);
        }else if(data.type == 'successList'){
            //successList(data);
        }else if(data.type == 'start'){
            startGameStatus(data);
        }else if(data.type == 'catchStatus'){
            catchStatus(data);
        }else if(data.type == 'broadcast'){
            broadcastFun(data);
        }else if(data.type == 'success'){
            newSuccessPush();
        }else if(data.type == 'refreshCurNum'){
            refreshCurNum(data);
        }
	}

	function onConnectError(e)
	{
		console.log("error");
	}

    function setInit(data){
        Laya.init(753, 1092);//宽高，渲染方式
        //初始化引擎，设置游戏宽高
        Laya.stage.scaleMode = "showall";
        Laya.stage.alignH = "center";
        //地址 回调 加载进度
        
        Laya.loader.load("http://45.77.16.55"+data.result.toyRoomImg,Laya.Handler.create(this, createPlayToy));
        //创建背景
        this.bg1 = new Background1();
        this.bg1.zOrder = 100;

        this.bg2 = new Background2();
        this.bg2.zOrder = 10;
        //背景显示到舞台
        Laya.stage.addChild(this.bg1);
        Laya.stage.addChild(this.bg2);

        //加载文字
        this.coinFont = new coinFont(data);
        this.coinFont.zOrder = 105;
        this.coinFont.pos(110,47)
        Laya.stage.addChild(this.coinFont);

        this.singlePlayFont = new singlePlayFont();
        this.singlePlayFont.zOrder = 105;
        this.singlePlayFont.pos(450,49);
        Laya.stage.addChild(this.singlePlayFont);

        this.curNumFont = new curNumFont(data);
        this.curNumFont.zOrder = 105;
        this.curNumFont.pos(530,49);
        Laya.stage.addChild(this.curNumFont);

        this.addLuckyNumFont = new addLuckyNumFont();
        this.addLuckyNumFont.zOrder = 105;
        this.addLuckyNumFont.pos(120,1000);
        this.addLuckyNumFont.visible = false;
        Laya.stage.addChild(this.addLuckyNumFont);

        this.timePngTxt = new timePngTxt();
        this.timePngTxt.zOrder = 105;
        this.timePngTxt.pos(540,49);
        Laya.stage.addChild(this.timePngTxt);

        //广播
        Laya.timer.frameLoop(1,this,onLoopbroadcast);
    }

    function onLoaded(){
        //加载其他零碎 元素
        this.Other = new Other();
        this.Other.zOrder = 101;
        Laya.stage.addChild(this.Other);

        this.OtherCave = new OtherCave();
        this.OtherCave.zOrder = 90;
        Laya.stage.addChild(this.OtherCave);
        //加载 爪子元素
        this.Ufo = new Ufo();
        this.Ufo.zOrder = 80;
        Laya.stage.addChild(this.Ufo);
        this.Ufo.pivot(-80, -125);
        ufoAll = this.Ufo;

        this.UfoShadow = new UfoShadow();
        this.UfoShadow.zOrder = 60;
        this.UfoShadow.pivot(-100, -730);
        Laya.stage.addChild(this.UfoShadow);
        shadow = this.UfoShadow;
        shadowBounds = this.UfoShadow.getBounds();
        //加载娃娃元素
        createPlayToyShadow();

        //加载背景
        this.zzc = new zzc();
        this.zzc.zOrder = 200
        this.zzc.visible = false;
        zzcObj = this.zzc;
        Laya.stage.addChild(this.zzc);

        this.failInfo = new failInfo();
        this.failInfo.zOrder = 210;
        this.failInfo.visible = false;
        failInfoObj = this.failInfo;
        Laya.stage.addChild(this.failInfo);

        this.successInfo = new successInfo();
        this.successInfo.zOrder = 210;
        this.successInfo.visible = false;
        successInfoObj = this.successInfo;
        Laya.stage.addChild(this.successInfo);

        this.newSuccess = new newSuccess();
        this.newSuccess.zOrder = 210;
        this.newSuccess.pos(0,-100);
        Laya.stage.addChild(this.newSuccess);
        Laya.timer.frameLoop(1,this,onLoopnewSuccess);
    }

    function createPlayToy(){
        for(var i =0 ;i<4;i++){

            toyImgCreate[i] = Laya.Pool.getItemByClass('toyImg',socketJs);
            toyImgCreate[i].toyInit(setInitData);
            if(toySize[0] < defaultSize[0]){
                addX = defaultSize[0]-toySize[0];
            }
            if(toySize[1] < defaultSize[1]){
                addY = defaultSize[1]-toySize[1];
            }

            toyImgCreate[i].pos(toyinfo_position[i][0]+addX,toyinfo_position[i][1]+addY);
            toyImgCreate[i].zOrder=toyinfo_position[i][2];
            Laya.stage.addChild(toyImgCreate[i]);
        }
        Laya.loader.load("res/atlas/comp.atlas", Laya.Handler.create(this, onLoaded), null, Laya.Loader.ATLAS);
    }

    function createPlayToyShadow(){
        for(var i =0 ;i<4;i++){
            toyShadow[i] = Laya.Pool.getItemByClass('toyShadow',socketJs);
            toyShadow[i].toyShadow(toySize);
            toyShadow[i].pos(toyinfo_position[i][0]+addX,toyinfo_position[i][1]+toySize[1]-30+addY);
            zOrder = parseInt(toyinfo_position[i][2])-1;
            toyShadow[i].zOrder=zOrder;
            Laya.stage.addChild(toyShadow[i]);
            toyShadowCurPos[i] = [];
            toyShadowCurPos[i]['x'] = toyinfo_position[i][0]+addX;
            toyShadowCurPos[i]['y'] = toyinfo_position[i][1]+toySize[1]-30+addY;
            toyShadowCurPos[i]['height'] = 45;
            toyShadowCurPos[i]['width'] = toySize[0];
            toyShadowCurPos[i]['obj'] = shadowObj;
        }
    }

    function refreshCurNum(data){
        curNumObj.text = "在线人数："+data.count;
    }
var broadcastFunI = -1,broadcast = [];
    function broadcastFun(data){
        broadcastFunI++;
        //this.broadcast[broadcastFunI] = new broadcast(data);
        if(data.isMe != 1){
            broadcast[broadcastFunI] = Laya.Pool.getItemByClass("toyShadow",socketJs);
            broadcast[broadcastFunI].broadcast(data);
            broadcast[broadcastFunI].zOrder = 150;
            broadcast[broadcastFunI].x = Laya.stage.width;
            broadcast[broadcastFunI].y = data.top;
            Laya.stage.addChild(broadcast[broadcastFunI]);
        }else{
            myBroadcast = Laya.Pool.getItemByClass("toyShadow",socketJs);
            myBroadcast.broadcast(data);
            myBroadcast.zOrder = 150;
            myBroadcast.x = Laya.stage.width;
            myBroadcast.y = data.top;
            Laya.stage.addChild( myBroadcast);
        }
    }

    function onLoopbroadcast(){
        for(var key in broadcast){
            if(broadcast[key].x > -Laya.stage.width){
                broadcast[key].x-=3;
            }else{
                broadcast[key].visible = false;
                Laya.Pool.recover("broadcast",broadcast[key]);
                broadcast.splice(key,1);
            }
        }
    }

    function myBroadcastLoop(){
        if(this.x > -Laya.stage.width){
            this.x -=3;
        }else{
            this.visible = false;
            Laya.timer.clear(this,myBroadcastLoop);
        }
    }

    function newSuccessPush(data){
        var newArr = [];
        newArr['str'] = data.str;
        newArr['userImg'] = data.userImg;
        newArr['is_me'] = data.isMe;
        broadcastSuccessList.push(newArr);
    }
var is_finish = 1;
    function onLoopnewSuccess(){
        if(broadcastSuccessList.length > 0 && is_finish == 1){
            if( (broadcastSuccessList[0]['is_me'] == 0) || (broadcastSuccessList[0]['is_me'] == 1 && canPlayMyBroadcast == 1)){
                if(newSuccessTxt.text != broadcastSuccessList[0]['str'] ){
                    newSuccessTxt.text = broadcastSuccessList[0]['str'];
                    newSuccessInfo.loadImage(broadcastSuccessList[0]['userImg']);
                }
                if(this.newSuccess.y < 200){
                    this.newSuccess.y +=5;
                }else{
                    is_finish = 0;
                    Laya.timer.loop(1500,this,stopnewSuccess);
                }
            }
        }
    }

    function stopnewSuccess(){
        Laya.timer.clear(this,stopnewSuccess);
        Laya.timer.frameLoop(1,this,upNewSuccess);
    }

    function upNewSuccess(){
        if(this.newSuccess.y > -100){
            this.newSuccess.y -=5;
        }else{
            broadcastSuccessList.splice(0,1);
            is_finish = 1;
            Laya.timer.clear(this,upNewSuccess);
        }
    }