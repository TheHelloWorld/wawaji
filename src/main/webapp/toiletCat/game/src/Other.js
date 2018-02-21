var leftButton,rightButton,upButton,downButton,catchButton,startButton,luckyNumPNG,againtxt,curnumpng,timepng;
    var Other = (function(_super){
    function Other(){
        Other.super(this);
        
        this.coin = new Laya.Sprite();
        this.coin.loadImage("comp/coin.png");
        this.coin.x = 130;
        this.coin.y = 10;
        this.addChild(this.coin);

        this.curnum = new Laya.Sprite();
        this.curnum.loadImage("comp/curnum.png");
        this.curnum.x = 290;
        this.curnum.y = 16;
        curnum = this.curnum;
        this.addChild(this.curnum);

        this.time = new Laya.Sprite();
        this.time.loadImage("comp/time.png");
        this.time.x = 340;
        this.time.y = 16;
        this.time.visible = false;
        timepng = this.time;
        this.addChild(this.time);

        this.ingback = new Laya.Sprite();
        this.ingback.loadImage("comp/ingback.png");
        this.ingback.x = 20;
        this.ingback.y = 870;
        this.addChild(this.ingback);

        this.prize = new Laya.Sprite();
        this.prize.loadImage("comp/prize.png");
        this.prize.x = 350;
        this.prize.y = 870;
        this.prize.on(Laya.Event.MOUSE_UP, this, prize);
        this.addChild(this.prize);

        this.recharge = new Laya.Sprite();
        this.recharge.loadImage("comp/recharge.png");
        this.recharge.x = 270;
        this.recharge.y = 870;
        this.recharge.on(Laya.Event.MOUSE_UP, this, gameRechargeInit);
        this.addChild(this.recharge);

        this.my = new Laya.Sprite();
        this.my.loadImage("comp/my.png");
        this.my.x = 470;
        this.my.y = 870;
        this.my.on(Laya.Event.MOUSE_UP, this, clickMy);
        this.addChild(this.my);
        
        this.go = new Laya.Sprite();
        //Laya.Animation.createFrames(["comp/go1.png"],"goStop");
        //Laya.Animation.createFrames(["comp/go1.png","comp/go2.png"],"goAct");
        this.go.loadImage("comp/go1.png");
        this.go.x = 210;
        this.go.y = 770;
        this.go.on(Laya.Event.MOUSE_UP, this, startGame);
        //this.go.play(0,false,"goStop");
        startButton = this.go;
        this.addChild(this.go);

        this.back = new Laya.Sprite();
        this.back.loadImage("comp/back.png");
        this.back.x = 20;
        this.back.y = 16;
        this.back.on(Laya.Event.MOUSE_UP, this, returnToIndex);
        backButton = this.back;
        this.addChild(this.back);
        
        this.up = new Laya.Sprite();
        //Laya.Animation.createFrames(["comp/up1.png"],"upStop");
        //Laya.Animation.createFrames(["comp/up1.png","comp/up2.png"],"upAct");
        this.up.loadImage("comp/up1.png");
        this.up.pos(150, 770);
        this.up.visible = false;
        this.up.on(Laya.Event.MOUSE_DOWN, this, moveSmall);
        this.up.on(Laya.Event.MOUSE_UP, this, stopMoveSmall);
        this.up.on(Laya.Event.MOUSE_OUT, this, stopMoveSmall);
        upButton = this.up;
        //this.up.play(0,false,'upStop');
        this.addChild(this.up);

        this.left = new Laya.Sprite();
        //Laya.Animation.createFrames(["comp/left1.png"],"leftStop");
        //Laya.Animation.createFrames(["comp/left1.png","comp/left2.png"],"leftAct");
        this.left.loadImage("comp/left1.png");
        this.left.pos(70, 800);
        this.left.visible = false;
        this.left.on(Laya.Event.MOUSE_DOWN, this, moveLeft);
        this.left.on(Laya.Event.MOUSE_UP, this, stopMoveLeft);
        this.left.on(Laya.Event.MOUSE_OUT, this, stopMoveLeft);
        leftButton = this.left;
        //leftButton.play(0,false,'leftStop');
        this.addChild(this.left);

        this.right = new Laya.Sprite();
        //Laya.Animation.createFrames(["comp/right1.png"],"rightStop");
        //Laya.Animation.createFrames(["comp/right1.png","comp/right2.png"],"rightAct");
        this.right.loadImage("comp/right1.png");
        this.right.pos(230, 800);
        this.right.visible = false;
        this.right.on(Laya.Event.MOUSE_DOWN, this, moveRight);
        this.right.on(Laya.Event.MOUSE_UP, this, stopMoveRight);
        this.right.on(Laya.Event.MOUSE_OUT, this, stopMoveRight);
        rightButton = this.right;
        //rightButton.play(0,false,'rightStop');
        this.addChild(this.right);
        
        this.down = new Laya.Sprite();
        //Laya.Animation.createFrames(["comp/down1.png"],"downStop");
        //Laya.Animation.createFrames(["comp/down1.png","comp/down2.png"],"downAct");
        this.down.loadImage("comp/downbutton1.png");
        this.down.pos(150, 820);
        this.down.visible = false;
        this.down.on(Laya.Event.MOUSE_DOWN, this, moveBig);
        this.down.on(Laya.Event.MOUSE_UP, this, stopMoveBig);
        this.down.on(Laya.Event.MOUSE_OUT, this, stopMoveBig);
        downButton = this.down;
        //downButton.play(0,false,'downStop');
        this.addChild(this.down);

        this.catch = new Laya.Sprite();
        //Laya.Animation.createFrames(["comp/catch1.png"],"catchStop");
        //Laya.Animation.createFrames(["comp/catch1.png","comp/catch2.png"],"catchAct");
        this.catch.loadImage("comp/catch1.png");
        this.catch.on(Laya.Event.MOUSE_UP, this, getCatchStatus);
        this.catch.pos(380, 770);
        this.catch.visible = false;
        catchButton = this.catch;
        this.addChild(this.catch);

        this.luckyNum = new Laya.Sprite();
        this.luckyNum.loadImage("comp/ing.png");
        this.luckyNum.y = 881;
        //this.luckyNum.x = (30-(luckyNum/100)*20);
        this.luckyNum.scale(luckyNum/100,1);
        this.luckyNum.x = 32;
        //this.luckyNum.scale(0.05,1);
        luckyNumPNG = this.luckyNum;
        this.addChild(this.luckyNum);
    }
    
    function OtherCave(){
        OtherCave.super(this);
        this.glass = new Laya.Sprite();
        this.glass.loadImage("comp/glass.png");
        this.glass.zOrder = 151;
        this.glass.pos(60,610);
        this.addChild(this.glass);

        this.cave = new Laya.Sprite();
        this.cave.loadImage("comp/cave.png");
        this.cave.zOrder = 81;
        this.cave.pos(5,685);
        this.addChild(this.cave);
    }

    //点击我的
    function clickMy(){
        if(startButton.visible == true){
            toUserIndex();
        }
    }

    function prize(){
        if(startButton.visible == true){
            toUserToy();
        }
    }

    function returnToIndex(){
        location.href="/";
    }

    function startGameButton(){
        leftButton.visible = true;
        rightButton.visible = true;
        upButton.visible = true;
        downButton.visible = true;
        catchButton.visible = true;
        startButton.visible = false;
        backButton.visible = false;
    }
    
    function resetGameButton(){
        leftButton.visible = false;
        rightButton.visible = false;
        upButton.visible = false;
        downButton.visible = false;
        catchButton.visible = false;
        startButton.visible = true;
        backButton.visible = true;
    }
    Laya.class(resetGameButton,"resetGameButton", _super);

    function zzc(){
        zzc.super(this);
        this.zzc = new Laya.Sprite();
        this.zzc.graphics.drawRect(0, 0, Laya.stage.width, Laya.stage.height, "#000000");
        this.zzc.alpha = 0.7;
        this.addChild(this.zzc);
    }
    Laya.class(zzc,"zzc", _super);

    function failInfo(){
        failInfo.super(this);
        this.failInfo = new Laya.Sprite();
        this.failInfo.loadImage("comp/ku.png");
        this.failInfo.alpha = 1;
        this.failInfo.pos(200,270);
        this.addChild(this.failInfo);

        this.failtxt = new Laya.Text();
        this.failtxt.color = "#FFFFFF";
		this.failtxt.font = "Impact";
        this.failtxt.fontSize = 40;
        this.failtxt.width = 300;
		this.failtxt.wordWrap = true;
		this.failtxt.text = "蓝瘦~~香菇！！";
		this.failtxt.leading = 5;
        this.failtxt.pos(170,480);
        this.addChild(this.failtxt);
        
        this.againButton = new Laya.Sprite();
        this.againButton.graphics.drawRect(180, 560, 200, 50, "#FF3968");
        this.againButton.graphics.drawRect(180, 630, 200, 50, "#FF3968");
        this.againButton.alpha = 1;
        this.addChild(this.againButton);

        this.againtxt = new Laya.Text();
        this.againtxt.color = "#FFFFFF";
		this.againtxt.font = "Impact";
        this.againtxt.fontSize = 30;
        this.againtxt.width = 300;
		this.againtxt.wordWrap = true;
		this.againtxt.text = "再来一次 (10)";
        this.againtxt.on(Laya.Event.CLICK,this,playAgain);
		this.againtxt.leading = 5;
        this.againtxt.pos(190,565);
        againtxt = this.againtxt
        this.addChild(this.againtxt);

        this.closetxt = new Laya.Text();
        this.closetxt.color = "#FFFFFF";
		this.closetxt.font = "Impact";
        this.closetxt.fontSize = 28;
        this.closetxt.width = 300;
		this.closetxt.wordWrap = true;
		this.closetxt.text = "一会再玩";
        this.closetxt.on(Laya.Event.CLICK,this,closeFailInfo);
		this.closetxt.leading = 5;
        this.closetxt.pos(210,640);
        this.addChild(this.closetxt);
    }
    Laya.class(failInfo,"failInfo", _super);

    function closeFailInfo(){
        failInfoObj.visible = false;
        successInfoObj.visible = false;
        zzcObj.visible = false;
        againtxt.text = "再来一次 (10)";
    }
    Laya.class(closeFailInfo,"closeFailInfo", _super);

    function playAgain(){
        closeFailInfo();
        startGame();
    }

    function successInfo(){
        successInfo.super(this);
        this.successInfo = new Laya.Sprite();
        this.successInfo.loadImage("http://www.9w83c6.cn"+setInitData.result.toyRoomImg);
        this.successInfo.alpha = 1;
        this.successInfo.pos(200,300);
        this.addChild(this.successInfo);

        this.successtxt = new Laya.Text();
        this.successtxt.color = "#FFFFFF";
		this.successtxt.font = "Impact";
        this.successtxt.fontSize = 40;
        this.successtxt.width = 300;
		this.successtxt.wordWrap = true;
		this.successtxt.text = "恭喜您获得~~";
		this.successtxt.leading = 5;
        this.successtxt.pos(180,500);
        this.addChild(this.successtxt);

        this.againButton = new Laya.Sprite();
        this.againButton.graphics.drawRect(180, 560, 200, 50, "#FF3968");
        this.againButton.graphics.drawRect(180, 630, 200, 50, "#FF3968");
        this.againButton.alpha = 1;
        this.addChild(this.againButton);

        this.againtxt = new Laya.Text();
        this.againtxt.color = "#FFFFFF";
		this.againtxt.font = "Impact";
        this.againtxt.fontSize = 30;
        this.againtxt.width = 300;
		this.againtxt.wordWrap = true;
		this.againtxt.text = "查看战利品";
        this.againtxt.on(Laya.Event.CLICK,this,seeMyPrize);
		this.againtxt.leading = 5;
        this.againtxt.pos(205,570);
        this.addChild(this.againtxt);

        this.closetxt = new Laya.Text();
        this.closetxt.color = "#FFFFFF";
		this.closetxt.font = "Impact";
        this.closetxt.fontSize = 30;
        this.closetxt.width = 300;
		this.closetxt.wordWrap = true;
		this.closetxt.text = "关闭";
        this.closetxt.on(Laya.Event.CLICK,this,closeFailInfo);
		this.closetxt.leading = 5;
        this.closetxt.pos(250,640);
        this.addChild(this.closetxt);
    }
    Laya.class(successInfo,"successInfo", _super);

    function seeMyPrize(){
        toUserToy();
    }

    function addToyBackground(){
        addToyBackground.super(this);
        this.backgroundToy = new Laya.Sprite();
        var length = setInitData.result.toyDesc.length;
        this.backgroundToy.loadImage("http://www.9w83c6.cn"+setInitData.result.toyDesc[length-1]);
        this.addChild(this.backgroundToy);
    }
    Laya.class(addToyBackground,"addToyBackground", _super);

    function gameRechargeInit(){
        gameRechargeObj.visible =  true;
        Laya.timer.frameLoop(1,this,gameRechargeshow);
    }
    Laya.class(gameRechargeInit,"gameRechargeInit", _super);

    function gameRechargeshow(){
        if(gameRechargeObj.y < 0 ){
            gameRechargeObj.y = 0;
            Laya.timer.clear(this,gameRechargeshow);
        }else{
            gameRechargeObj.y -=30;
        }
    }

    Laya.class(startGameButton,"startGameButton", _super);
    Laya.class(Other,"Other", _super);
    Laya.class(OtherCave,"OtherCave", _super);
    return Other;
})(Laya.Sprite);
