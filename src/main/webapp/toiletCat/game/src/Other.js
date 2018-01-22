var leftButton,rightButton,upButton,downButton,catchButton,startButton,luckyNumPNG,againtxt,curnumpng,timepng;
    var Other = (function(_super){
    function Other(){
        Other.super(this);
        
        this.coin = new Laya.Sprite();
        this.coin.loadImage("comp/coin.png");
        this.coin.x = 20;
        this.coin.y = 20;
        this.addChild(this.coin);

        this.curnum = new Laya.Sprite();
        this.curnum.loadImage("comp/curnum.png");
        this.curnum.x = 400;
        this.curnum.y = 20;
        curnum = this.curnum;
        this.addChild(this.curnum);

        this.time = new Laya.Sprite();
        this.time.loadImage("comp/time.png");
        this.time.x = 440;
        this.time.y = 20;
        this.time.visible = false;
        timepng = this.time;
        this.addChild(this.time);

        this.ingback = new Laya.Sprite();
        this.ingback.loadImage("comp/ingback.png");
        this.ingback.x = 20;
        this.ingback.y = 990;
        this.addChild(this.ingback);

        this.prize = new Laya.Sprite();
        this.prize.loadImage("comp/prize.png");
        this.prize.x = 380;
        this.prize.y = 980;
        this.prize.on(Laya.Event.MOUSE_UP, this, prize);
        this.addChild(this.prize);

        this.recharge = new Laya.Sprite();
        this.recharge.loadImage("comp/recharge.png");
        this.recharge.x = 500;
        this.recharge.y = 980;
        this.recharge.on(Laya.Event.MOUSE_UP, this, gameRechargeInit);
        this.addChild(this.recharge);

        this.my = new Laya.Sprite();
        this.my.loadImage("comp/my.png");
        this.my.x = 620;
        this.my.y = 980;
        this.my.on(Laya.Event.MOUSE_UP, this, clickMy);
        this.addChild(this.my);
        
        this.go = new Laya.Sprite();
        this.go.loadImage("comp/go.png");
        this.go.x = 260;
        this.go.y = 810;
        this.go.on(Laya.Event.MOUSE_UP, this, startGame);
        startButton = this.go;
        this.addChild(this.go);
        
        this.up = new Laya.Sprite();
        this.up.loadImage("comp/up.png");
        this.up.pos(180, 845);
        this.up.visible = false;
        this.up.on(Laya.Event.MOUSE_DOWN, this, moveSmall);
        this.up.on(Laya.Event.MOUSE_UP, this, stopMoveSmall);
        this.up.on(Laya.Event.MOUSE_OUT, this, stopMoveSmall);
        upButton = this.up;
        this.addChild(this.up);

        this.left = new Laya.Sprite();
        this.left.loadImage("comp/left.png");
        this.left.pos(60, 875);
        this.left.visible = false;
        this.left.on(Laya.Event.MOUSE_DOWN, this, moveLeft);
        this.left.on(Laya.Event.MOUSE_UP, this, stopMoveLeft);
        this.left.on(Laya.Event.MOUSE_OUT, this, stopMoveLeft);
        leftButton = this.left;
        this.addChild(this.left);

        this.right = new Laya.Sprite();
        this.right.loadImage("comp/right.png");
        this.right.pos(300, 875);
        this.right.visible = false;
        this.right.on(Laya.Event.MOUSE_DOWN, this, moveRight);
        this.right.on(Laya.Event.MOUSE_UP, this, stopMoveRight);
        this.right.on(Laya.Event.MOUSE_OUT, this, stopMoveRight);
        rightButton = this.right;
        this.addChild(this.right);

        this.down = new Laya.Sprite();
        this.down.loadImage("comp/down.png");
        this.down.pos(180, 905);
        this.down.visible = false;
        this.down.on(Laya.Event.MOUSE_DOWN, this, moveBig);
        this.down.on(Laya.Event.MOUSE_UP, this, stopMoveBig);
        this.down.on(Laya.Event.MOUSE_OUT, this, stopMoveBig);
        downButton = this.down;
        this.addChild(this.down);

        this.catch = new Laya.Sprite();
        this.catch.loadImage("comp/catch.png");
        this.catch.on(Laya.Event.MOUSE_UP, this, getCatchStatus);
        this.catch.pos(480, 845);
        this.catch.visible = false;
        catchButton = this.catch;
        this.addChild(this.catch);

        this.luckyNum = new Laya.Sprite();
        this.luckyNum.loadImage("comp/ing.png");
        this.luckyNum.x = 20;
        this.luckyNum.y = 942;
        this.luckyNum.x = (30-(luckyNum/100)*20);
        this.luckyNum.scale(luckyNum/100,1);
        luckyNumPNG = this.luckyNum;
        this.addChild(this.luckyNum);
    }
    
    function OtherCave(){
        OtherCave.super(this);
        this.glass = new Laya.Sprite();
        this.glass.loadImage("comp/glass.png");
        this.glass.zOrder = 151;
        this.glass.pos(115,605);
        this.addChild(this.glass);

        this.cave = new Laya.Sprite();
        this.cave.loadImage("comp/cave.png");
        this.cave.zOrder = 81;
        this.cave.pos(75,730);
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

    function startGameButton(){
        leftButton.visible = true;
        rightButton.visible = true;
        upButton.visible = true;
        downButton.visible = true;
        catchButton.visible = true;
        startButton.visible = false;
    }
    
    function resetGameButton(){
        leftButton.visible = false;
        rightButton.visible = false;
        upButton.visible = false;
        downButton.visible = false;
        catchButton.visible = false;
        startButton.visible = true;
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
        this.failInfo.pos(280,300);
        this.addChild(this.failInfo);

        this.failtxt = new Laya.Text();
        this.failtxt.color = "#FFFFFF";
		this.failtxt.font = "Impact";
        this.failtxt.fontSize = 40;
        this.failtxt.width = 300;
		this.failtxt.wordWrap = true;
		this.failtxt.text = "蓝瘦~~香菇！！";
		this.failtxt.leading = 5;
        this.failtxt.pos(250,600);
        this.addChild(this.failtxt);
        
        this.againButton = new Laya.Sprite();
        this.againButton.graphics.drawRect(230, 700, 300, 80, "#FF3968");
        this.againButton.graphics.drawRect(230, 800, 300, 80, "#FF3968");
        this.againButton.alpha = 1;
        this.addChild(this.againButton);

        this.againtxt = new Laya.Text();
        this.againtxt.color = "#FFFFFF";
		this.againtxt.font = "Impact";
        this.againtxt.fontSize = 40;
        this.againtxt.width = 300;
		this.againtxt.wordWrap = true;
		this.againtxt.text = "再来一次（10）";
        this.againtxt.on(Laya.Event.CLICK,this,playAgain);
		this.againtxt.leading = 5;
        this.againtxt.pos(240,710);
        againtxt = this.againtxt
        this.addChild(this.againtxt);

        this.closetxt = new Laya.Text();
        this.closetxt.color = "#FFFFFF";
		this.closetxt.font = "Impact";
        this.closetxt.fontSize = 40;
        this.closetxt.width = 300;
		this.closetxt.wordWrap = true;
		this.closetxt.text = "一会再玩";
        this.closetxt.on(Laya.Event.CLICK,this,closeFailInfo);
		this.closetxt.leading = 5;
        this.closetxt.pos(300,810);
        this.addChild(this.closetxt);
    }
    Laya.class(failInfo,"failInfo", _super);

    function closeFailInfo(){
        failInfoObj.visible = false;
        successInfoObj.visible = false;
        zzcObj.visible = false;
        againtxt.text = "再来一次（10）";
    }
    Laya.class(closeFailInfo,"closeFailInfo", _super);

    function playAgain(){
        closeFailInfo();
        startGame();
    }

    function successInfo(){
        successInfo.super(this);
        this.successInfo = new Laya.Sprite();
        this.successInfo.loadImage(setInitData.result.toyRoomImg);
        this.successInfo.alpha = 1;
        this.successInfo.pos(280,300);
        this.addChild(this.successInfo);

        this.successtxt = new Laya.Text();
        this.successtxt.color = "#FFFFFF";
		this.successtxt.font = "Impact";
        this.successtxt.fontSize = 40;
        this.successtxt.width = 300;
		this.successtxt.wordWrap = true;
		this.successtxt.text = "恭喜您获得~~";
		this.successtxt.leading = 5;
        this.successtxt.pos(250,550);
        this.addChild(this.successtxt);

        this.againButton = new Laya.Sprite();
        this.againButton.graphics.drawRect(230, 700, 300, 80, "#FF3968");
        this.againButton.graphics.drawRect(230, 800, 300, 80, "#FF3968");
        this.againButton.alpha = 1;
        this.addChild(this.againButton);

        this.againtxt = new Laya.Text();
        this.againtxt.color = "#FFFFFF";
		this.againtxt.font = "Impact";
        this.againtxt.fontSize = 40;
        this.againtxt.width = 300;
		this.againtxt.wordWrap = true;
		this.againtxt.text = "查看战利品";
        this.againtxt.on(Laya.Event.CLICK,this,seeMyPrize);
		this.againtxt.leading = 5;
        this.againtxt.pos(280,710);
        this.addChild(this.againtxt);

        this.closetxt = new Laya.Text();
        this.closetxt.color = "#FFFFFF";
		this.closetxt.font = "Impact";
        this.closetxt.fontSize = 40;
        this.closetxt.width = 300;
		this.closetxt.wordWrap = true;
		this.closetxt.text = "关闭";
        this.closetxt.on(Laya.Event.CLICK,this,closeFailInfo);
		this.closetxt.leading = 5;
        this.closetxt.pos(340,810);
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
        this.backgroundToy.loadImage(setInitData.result.toyDesc[length-1]);
        this.addChild(this.backgroundToy);
    }
    Laya.class(addToyBackground,"addToyBackground", _super);

    function gameRechargeInit(){
        gameRechargeObj.visible =  true;
        Laya.timer.frameLoop(1,this,gameRechargeshow);
    }
    Laya.class(gameRechargeInit,"gameRechargeInit", _super);

    function gameRechargeshow(){
        if(gameRechargeObj.y < 20 ){
            Laya.timer.clear(this,gameRechargeshow);
        }else{
            gameRechargeObj.y -=20;
        }
    }

    Laya.class(startGameButton,"startGameButton", _super);
    Laya.class(Other,"Other", _super);
    Laya.class(OtherCave,"OtherCave", _super);
    return Other;
})(Laya.Sprite);
