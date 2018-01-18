    var Recharge = (function(_super){
    function Recharge(){
        Recharge.super(this);
        this.bgcolor = new Laya.Sprite();
        this.bgcolor.graphics.drawRect(0, 0, 753, 1092, "#FEFE9E");
        this.addChild(this.bgcolor);

        this.back = new Laya.Sprite();
        this.back.loadImage("comp/returnButton.ico");
        this.back.pos(50,90);
        this.back.rotation = -90;
        this.back.on(Laya.Event.CLICK,this,gameRechargeBack);
        this.addChild(this.back);

        this.coinImg = new Laya.Sprite();
        this.coinImg.loadImage("comp/rechargeCoin.png");
        this.coinImg.pos(280,20);
        this.addChild(this.coinImg);

        this.coinImg1 = new Laya.Sprite();//图
        this.coinImg1.loadImage("comp/rechargeCoin.png");
        this.coinImg1.pos(70,170);
        this.addChild(this.coinImg1);
        this.curCoin1 = new Laya.Text();//文字
        this.curCoin1.color = "#000000";
		this.curCoin1.font = "Impact";
        this.curCoin1.fontSize = 40;
        this.curCoin1.width = 300;
		this.curCoin1.wordWrap = true;
		this.curCoin1.text = "100";
		this.curCoin1.leading = 5;
        this.curCoin1.pos(170,180);
        this.addChild(this.curCoin1);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "充值100马桶币";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(120,250);
        this.addChild(this.curCoin2);
        //充值按钮背景图
        this.bgcolor1 = new Laya.Sprite();
        this.bgcolor1.zOrder=1;
        this.bgcolor1.graphics.drawPath(100, 310, [
            ["moveTo", 20, 0],
            ["lineTo", 200, 0],
            ["arcTo", 210, 0, 210, 20, 20],
            ["lineTo", 210, 30],
            ["arcTo", 210, 60, 200, 60, 20],
            ["lineTo", 20, 60],
            ["arcTo", 0, 60, 0, 30, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#FEFE9E"
        });
        this.addChild(this.bgcolor1);
        this.rechargeFont1 = new Laya.Text();//文字
        this.rechargeFont1.color = "#FF0000";
		this.rechargeFont1.font = "Impact";
        this.rechargeFont1.fontSize = 30;
        this.rechargeFont1.width = 300;
		this.rechargeFont1.wordWrap = true;
		this.rechargeFont1.text = "￥10.00";
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2
        this.rechargeFont1.pos(150,320);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,['10.00']);
        this.addChild(this.rechargeFont1);

        this.coinImg2 = new Laya.Sprite();//图
        this.coinImg2.loadImage("comp/rechargeCoin.png");
        this.coinImg2.pos(420,170);
        this.addChild(this.coinImg2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 40;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "200+10";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(520,180);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "充值210马桶币";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(470,250);
        this.addChild(this.curCoin2);
        this.bgcolor1.graphics.drawPath(450, 310, [
            ["moveTo", 20, 0],
            ["lineTo", 200, 0],
            ["arcTo", 210, 0, 210, 20, 20],
            ["lineTo", 210, 30],
            ["arcTo", 210, 60, 200, 60, 20],
            ["lineTo", 20, 60],
            ["arcTo", 0, 60, 0, 30, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#FEFE9E"
        });
        this.addChild(this.bgcolor1);
        this.rechargeFont1 = new Laya.Text();//文字
        this.rechargeFont1.color = "#FF0000";
		this.rechargeFont1.font = "Impact";
        this.rechargeFont1.fontSize = 30;
        this.rechargeFont1.width = 300;
		this.rechargeFont1.wordWrap = true;
		this.rechargeFont1.text = "￥20.00";
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2
        this.rechargeFont1.pos(500,320);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,['20.00']);
        this.addChild(this.rechargeFont1);

        this.coinImg3 = new Laya.Sprite();//图
        this.coinImg3.loadImage("comp/rechargeCoin.png");
        this.coinImg3.pos(70,440);
        this.addChild(this.coinImg3);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 40;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "300+30";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(170,450);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "充值330马桶币";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(120,520);
        this.addChild(this.curCoin2);
        this.bgcolor1.graphics.drawPath(100, 580, [
            ["moveTo", 20, 0],
            ["lineTo", 200, 0],
            ["arcTo", 210, 0, 210, 20, 20],
            ["lineTo", 210, 30],
            ["arcTo", 210, 60, 200, 60, 20],
            ["lineTo", 20, 60],
            ["arcTo", 0, 60, 0, 30, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#FEFE9E"
        });
        this.addChild(this.bgcolor1);
        this.rechargeFont1 = new Laya.Text();//文字
        this.rechargeFont1.color = "#FF0000";
		this.rechargeFont1.font = "Impact";
        this.rechargeFont1.fontSize = 30;
        this.rechargeFont1.width = 300;
		this.rechargeFont1.wordWrap = true;
		this.rechargeFont1.text = "￥30.00";
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2
        this.rechargeFont1.pos(150,590);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,['30.00']);
        this.addChild(this.rechargeFont1);

        this.coinImg4 = new Laya.Sprite();//图
        this.coinImg4.loadImage("comp/rechargeCoin.png");
        this.coinImg4.pos(420,440);
        this.addChild(this.coinImg4);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 40;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "500+50";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(520,450);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "充值550马桶币";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(470,520);
        this.addChild(this.curCoin2);
        this.bgcolor1.graphics.drawPath(450, 580, [
            ["moveTo", 20, 0],
            ["lineTo", 200, 0],
            ["arcTo", 210, 0, 210, 20, 20],
            ["lineTo", 210, 30],
            ["arcTo", 210, 60, 200, 60, 20],
            ["lineTo", 20, 60],
            ["arcTo", 0, 60, 0, 30, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#FEFE9E"
        });
        this.addChild(this.bgcolor1);
        this.rechargeFont1 = new Laya.Text();//文字
        this.rechargeFont1.color = "#FF0000";
		this.rechargeFont1.font = "Impact";
        this.rechargeFont1.fontSize = 30;
        this.rechargeFont1.width = 300;
		this.rechargeFont1.wordWrap = true;
		this.rechargeFont1.text = "￥50.00";
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2
        this.rechargeFont1.pos(500,590);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,['50.00']);
        this.addChild(this.rechargeFont1);

        this.coinImg5 = new Laya.Sprite();//图
        this.coinImg5.loadImage("comp/rechargeCoin.png");
        this.coinImg5.pos(70,710);
        this.addChild(this.coinImg5);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 40;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "1000+180";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(170,720);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "充值1180马桶币";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(120,790);
        this.addChild(this.curCoin2);
        this.bgcolor1.graphics.drawPath(100, 850, [
            ["moveTo", 20, 0],
            ["lineTo", 200, 0],
            ["arcTo", 210, 0, 210, 20, 20],
            ["lineTo", 210, 30],
            ["arcTo", 210, 60, 200, 60, 20],
            ["lineTo", 20, 60],
            ["arcTo", 0, 60, 0, 30, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#FEFE9E"
        });
        this.addChild(this.bgcolor1);
        this.rechargeFont1 = new Laya.Text();//文字
        this.rechargeFont1.color = "#FF0000";
		this.rechargeFont1.font = "Impact";
        this.rechargeFont1.fontSize = 30;
        this.rechargeFont1.width = 300;
		this.rechargeFont1.wordWrap = true;
		this.rechargeFont1.text = "￥100.00";
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2
        this.rechargeFont1.pos(150,860);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,['100.00']);
        this.addChild(this.rechargeFont1);

        this.coinImg6 = new Laya.Sprite();//图
        this.coinImg6.loadImage("comp/rechargeCoin.png");
        this.coinImg6.pos(420,710);
        this.addChild(this.coinImg6);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 40;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "2000+240";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(520,720);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = "充值2240马桶币";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(470,790);
        this.addChild(this.curCoin2);
        this.bgcolor1.graphics.drawPath(450, 850, [
            ["moveTo", 20, 0],
            ["lineTo", 200, 0],
            ["arcTo", 210, 0, 210, 20, 20],
            ["lineTo", 210, 30],
            ["arcTo", 210, 60, 200, 60, 20],
            ["lineTo", 20, 60],
            ["arcTo", 0, 60, 0, 30, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#FEFE9E"
        });
        this.addChild(this.bgcolor1);
        this.rechargeFont1 = new Laya.Text();//文字
        this.rechargeFont1.color = "#FF0000";
		this.rechargeFont1.font = "Impact";
        this.rechargeFont1.fontSize = 30;
        this.rechargeFont1.width = 300;
		this.rechargeFont1.wordWrap = true;
		this.rechargeFont1.text = "￥200.00";
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2
        this.rechargeFont1.pos(500,860);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,['200.00']);
        this.addChild(this.rechargeFont1);

        this.bgcolor.graphics.drawPath(280, 20, [
            ["moveTo", 20, 0],
            ["lineTo", 200, 0],
            ["arcTo", 210, 0, 210, 20, 20],
            ["lineTo", 210, 50],
            ["arcTo", 210, 70, 200, 70, 20],
            ["lineTo", 20, 70],
            ["arcTo", 0, 70, 0, 50, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#ffffff"
        });

        this.curCoin = new Laya.Text();
        this.curCoin.color = "#000000";
		this.curCoin.font = "Impact";
        this.curCoin.fontSize = 40;
        this.curCoin.width = 300;
		this.curCoin.wordWrap = true;
		this.curCoin.text = sessionStorage["toiletCatUserCoin"]; 
		this.curCoin.leading = 5;
        this.curCoin.pos(380,30);
        this.addChild(this.curCoin);

        this.bgcolor.graphics.drawPath(50, 150, [
            ["moveTo", 20, 0],
            ["lineTo", 300, 0],
            ["arcTo", 310, 0, 310, 20, 20],
            ["lineTo", 310, 200],
            ["arcTo", 310, 230, 300, 230, 20],
            ["lineTo", 20, 230],
            ["arcTo", 0, 230, 0, 50, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#ffffff"
        });
        this.bgcolor.graphics.drawPath(400, 150, [
            ["moveTo", 20, 0],
            ["lineTo", 300, 0],
            ["arcTo", 310, 0, 310, 20, 20],
            ["lineTo", 310, 200],
            ["arcTo", 310, 230, 300, 230, 20],
            ["lineTo", 20, 230],
            ["arcTo", 0, 230, 0, 50, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#ffffff"
        });
        this.bgcolor.graphics.drawPath(50, 420, [
            ["moveTo", 20, 0],
            ["lineTo", 300, 0],
            ["arcTo", 310, 0, 310, 20, 20],
            ["lineTo", 310, 200],
            ["arcTo", 310, 230, 300, 230, 20],
            ["lineTo", 20, 230],
            ["arcTo", 0, 230, 0, 50, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#ffffff"
        });
        this.bgcolor.graphics.drawPath(400, 420, [
            ["moveTo", 20, 0],
            ["lineTo", 300, 0],
            ["arcTo", 310, 0, 310, 20, 20],
            ["lineTo", 310, 200],
            ["arcTo", 310, 230, 300, 230, 20],
            ["lineTo", 20, 230],
            ["arcTo", 0, 230, 0, 50, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#ffffff"
        });
        this.bgcolor.graphics.drawPath(50, 690, [
            ["moveTo", 20, 0],
            ["lineTo", 300, 0],
            ["arcTo", 310, 0, 310, 20, 20],
            ["lineTo", 310, 200],
            ["arcTo", 310, 230, 300, 230, 20],
            ["lineTo", 20, 230],
            ["arcTo", 0, 230, 0, 50, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#ffffff"
        });
        this.bgcolor.graphics.drawPath(400, 690, [
            ["moveTo", 20, 0],
            ["lineTo", 300, 0],
            ["arcTo", 310, 0, 310, 20, 20],
            ["lineTo", 310, 200],
            ["arcTo", 310, 230, 300, 230, 20],
            ["lineTo", 20, 230],
            ["arcTo", 0, 230, 0, 50, 20],
            ["lineTo", 0, 20],
            ["arcTo", 0, 0, 20, 0, 20],
            ["closePath"]
        ],
        {
            fillStyle: "#ffffff"
        });
    }

    function gameRecharge(data){
        rechargeThis(data);
    }
    Laya.class(gameRecharge,"gameRecharge",_super);

    // 充值操作
    function rechargeThis(amount) {
        $.ajax({
            url:"/toiletCat/api/user/userRecharge.action",
            type:"POST",
            async:false,
            data:{
                amount:amount,
                rechargeType:"wxpay",
                userNo:uId
            },
            success:function(data) {
                // 转换数据
                if(typeof(data) == "string") {
                    data = eval("("+data+")");
                }

                // 判断是否成功
                if(data["is_success"] != "success") {
                    toiletCatMsg(data["result"], null);
                    return;
                }

                // 将当前url放入前端缓存
                sessionStorage["toiletCatLastPage"] = window.location.href;

                // 跳转到充值页面
                window.location.href = data["result"];

            }
        });
    }

    function gameRechargeBack(){
        Laya.timer.frameLoop(1,this,gameRechargehide);
    }

    function gameRechargehide(){
        if(gameRechargeObj.y >1092){
            gameRechargeObj.visible = false;
            Laya.timer.clear(this,gameRechargehide);
        }else{
            gameRechargeObj.y +=20;
        }
    }
    Laya.class(rechargeThis,"rechargeThis",_super);


    Laya.class(Recharge,"Recharge", _super);
    return Recharge;
})(Laya.Sprite);
