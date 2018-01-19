//socket
var shadowObj,toyNo,toyNowCoin,toyName,toyRoomImg,luckyNum,coinObj,curNumObj,addLuckyNum,addLuckyNumFontObj,timePngTxtObj,singlePlayFonttxt,newSuccessInfo,newSuccessTxt;
var socketJs = (function(_super){
    function socketJs(){
        socketJs.super(this);

    }
    //注册类
    Laya.class(socketJs,"socketJs", _super);
    var _proto = socketJs.prototype;
    var curPlayer = 0;
    _proto.toyInit = function(data){
        this.toyImg = new Laya.Sprite();
        this.toyImg.loadImage(data.result.toyRoomImg);
        this.addChild(this.toyImg);
        if(toySize[0] == 0){
            var size = this.toyImg.getBounds();
            toySize[0] = size.width;
            toySize[1] = size.height;
        }
        toyNo = data.result.toyNo;
        toyNowCoin = data.result.toyNowCoin;
        toyName = data.result.toyName;
        toyRoomImg = data.result.toyRoomImg;
        luckyNum = data.result.userGameRoomLuckyNumB;
        curPlayer = data.result.curPlayer

    }
    _proto.toyShadow = function(data){
        this.toyShadow = new Laya.Animation();
        Laya.Animation.createFrames(["comp/wawadi.png"],"shadowStop");
        Laya.Animation.createFrames(["comp/di-ready.png"],"shadowSelect");
        this.toyShadow.scaleX = (data[0]/499);
        this.addChild(this.toyShadow);
        shadowObj = this.toyShadow;
        shadowObj.play(0,false,'shadowStop');
    }

    _proto.broadcast = function(data){
        this.broadcastBack = new Laya.Sprite();
        this.broadcastBack.graphics.drawRect(0, 0, Laya.stage.width, 50, "#909090");
        this.broadcastBack.alpha = 0.5;
        this.addChild(this.broadcastBack);

        this.broadcasttxt = new Laya.Text();
        this.broadcasttxt.color = "#000000";
        this.broadcasttxt.font = "Impact";
        this.broadcasttxt.fontSize = 32;
        this.broadcasttxt.width = Laya.stage.width;
        this.broadcasttxt.wordWrap = true;
        this.broadcasttxt.text = data.str;
        this.broadcasttxt.leading = 5;
        this.addChild(this.broadcasttxt);
    }

    function refreshCurNum(data){
        console.log('刷新在线人数');
        //$(".curPlayer").html('在线人数:'+data.count);
    }
    Laya.class(refreshCurNum,"refreshCurNum", _super);

    function catchStatus(data){
        if(data.result.catch_result == 'catch success'){
            catch_status = 1;
        }else{
            catch_status = 0;
            luckyNum = parseInt(luckyNum)+parseInt(data.result.addNum);
            addLuckyNum = data.result.addNum;
        }
        upToyX = 0;
        upToyY = 0;
        upToyDiff = 0;
        resetx = resety = 0;
        sin = 0;
        sin_right = 0;
        resetarmleft = resetarmright = false;
        catchToy();
    }
    Laya.class(catchStatus,"catchStatus", _super);

    function startGame(){
        if(failInfoObj.visible == true || successInfoObj.visible == true){
            return;
        }
        toyNowCoin = toyNowCoin;
        canMoveStatus = 1;
        var sign = 'start&'+uId+"&"+toyNowCoin+"&"+rId;
        sign = hex_md5(sign);
        var json = {};
        json["type"] = "start";
        json["uId"] = uId;
        json["rId"] = rId;
        json["coin"] = toyNowCoin;
        json["sign"] = sign;
        socket.send(JSON.stringify(json));
    }
    Laya.class(startGame,"startGame", _super);

    function getCatchStatus(){
        canMoveStatus = 0;
        Laya.timer.clear(this,startGameDjs);

        if(selectToy > -1){
            var status_toy = 1;
        }else{
            var status_toy = 0;
        }
        var sign = "catch&"+uId+"&"+rId;
        sign = hex_md5(sign);
        var json = {};
        json["type"] = "catch";
        json["uId"] = uId;
        json["rId"] = rId;
        json["sign"] = sign;
        json["status"] = status_toy;
        json["uName"] = userName;
        json["tName"] = toyName;
        json["userImg"] = userImg;
        socket.send(JSON.stringify(json));
    }
    Laya.class(getCatchStatus,"getCatchStatus", _super);

    var startdjs = 30;
    function startGameStatus(data){
        if(data.is_success == 'fail'){
            gameRechargeInit(); 
        }else{
            if(data.result.result == "success"){
                startdjs = 30;
                selectToy = -1;
                coinObj.text = sessionStorage["toiletCatUserCoin"]-toyNowCoin;
                sessionStorage["toiletCatUserCoin"] -= toyNowCoin;
                //切换按钮
                startGameButton();
                canPlayMyBroadcast = 0;
                shadowBounds = shadow.getBounds();
                //倒计时
                timepng.visible = true;
                curnum.visible = false;
                curNumObj.visible = false;
                singlePlayFonttxt.visible = false;
                timePngTxtObj.visible = true;
                Laya.timer.loop(1000,this,startGameDjs);
                //扣除页面游戏币和 session中游戏币 

            }else{
                gameRechargeInit();
            }
        }
    }
    Laya.class(startGameStatus,"startGameStatus", _super);

    function startGameDjs(){
        if(startdjs > 0){
            startdjs--;
            timePngTxtObj.text = startdjs;
        }else{
            getCatchStatus();
        }
    }

    function coinFont (){
        coinFont.super(this);
        this.txt = new Laya.Text();
        this.txt.color = "#FFFFFF";
        this.txt.font = "Impact";
        this.txt.fontSize = 30;
        this.txt.wordWrap = true;
        this.txt.text = sessionStorage["toiletCatUserCoin"];
        this.txt.leading = 5;
        coinObj = this.txt;
        this.addChild(this.txt);
    }
    Laya.class(coinFont,"coinFont", _super);

    function curNumFont(){
        curNumFont.super(this);
        this.txt = new Laya.Text();
        this.txt.color = "#FFFFFF";
        this.txt.font = "Impact";
        this.txt.width = 200;
        this.txt.fontSize = 25;
        this.txt.wordWrap = true;
        this.txt.text = "在线人数:"+curPlayer;
        this.txt.leading = 5;
        curNumObj = this.txt;
        this.addChild(this.txt);
    }
    Laya.class(curNumFont,"curNumFont", _super);

    function singlePlayFont(){
        singlePlayFont.super(this);
        this.txt = new Laya.Text();
        this.txt.color = "#FFFFFF";
        this.txt.font = "Impact";
        this.txt.fontSize = 25;
        this.txt.wordWrap = true;
        this.txt.text = data.result.toyNowCoin+"/局";
        this.txt.leading = 5;
        singlePlayFonttxt = this.txt;
        this.addChild(this.txt);
    }
    Laya.class(singlePlayFont,"singlePlayFont", _super);

    function addLuckyNumFont(){
        addLuckyNumFont.super(this);
        this.txt = new Laya.Text();
        this.txt.color = "#FFFFFF";
        this.txt.font = "Impact";
        this.txt.fontSize = 40;
        this.txt.wordWrap = true;
        this.txt.text = "+6";
        this.txt.leading = 5;
        addLuckyNumFontObj = this.txt;
        this.addChild(this.txt);
    }
    Laya.class(addLuckyNumFont,"addLuckyNumFont", _super);

    function timePngTxt(){
        timePngTxt.super(this);
        this.txt = new Laya.Text();
        this.txt.color = "#FFFFFF";
        this.txt.font = "Impact";
        this.txt.fontSize = 35;
        this.txt.wordWrap = true;
        this.txt.text = "30";
        this.txt.leading = 5;
        this.txt.visible = false;
        timePngTxtObj = this.txt;
        this.addChild(this.txt);
    }
    Laya.class(timePngTxt,"timePngTxt", _super);

    function newSuccess(){
        newSuccess.super(this);
        this.againButton = new Laya.Sprite();
        this.againButton.graphics.drawRect(0, 0, Laya.stage.width, 100, "#ccc");
        this.againButton.alpha = 0.8;
        this.addChild(this.againButton);

        this.successInfo = new Laya.Sprite();
        this.successInfo.loadImage("comp/ji.png");
        this.successInfo.alpha = 1;
        this.successInfo.scale(0.35,0.35);
        newSuccessInfo = this.successInfo;
        this.addChild(this.successInfo);

        this.successtxt = new Laya.Text();
        this.successtxt.color = "#FF3B30";
        this.successtxt.font = "Impact";
        this.successtxt.fontSize = 35;
        this.successtxt.width = Laya.stage.width-90;
        this.successtxt.x = 90;
        this.successtxt.wordWrap = true;
        this.successtxt.stroke = 2;
        this.successtxt.strokeColor = "#E6DB74";
        this.successtxt.text = "恭喜哈哈哈哈哈哈哈 获得了方熊暖手抱枕（随机）";
        this.successtxt.leading = 5;
        newSuccessTxt = this.successtxt;
        this.addChild(this.successtxt);
    }
    Laya.class(newSuccess,"newSuccess",_super);

    return socketJs;
})(Laya.Sprite);
