    var Recharge = (function(_super){
    function Recharge(){
        Recharge.super(this);
        this.bgcolor = new Laya.Sprite();
        this.bgcolor.graphics.drawRect(0, 0, 753, 1112, "#FEFE9E");
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
        this.curCoin1.fontSize = 30;
        this.curCoin1.width = 300;
		this.curCoin1.wordWrap = true;
		this.curCoin1.text = setInitData['result']['rechargeData'][0]["coinText"];
		this.curCoin1.leading = 5;
        this.curCoin1.pos(170,180);
        this.addChild(this.curCoin1);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = setInitData['result']['rechargeData'][0]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][0]['coin'])+"("+setInitData['result']['rechargeData'][0]["userLimitFlag"]+"/"+setInitData['result']['rechargeData'][0]["rechargeLimit"]+")";
		this.curCoin2.leading = 5;
        this.curCoin2.pos(120,250);
        this.addChild(this.curCoin2);

        this.limit = new Laya.Sprite();
        this.limit.loadImage("comp/limit.png");
        this.limit.pos(290,150);
        this.limit.scale(2,2);
        this.addChild(this.limit);
        //充值按钮背景图
        this.bgcolor1 = new Laya.Sprite();
        this.bgcolor1.zOrder=1;
        if(setInitData['result']['rechargeData'][0]["userLimitFlag"] < setInitData['result']['rechargeData'][0]["rechargeLimit"] ){
            var color = "#FEFE9E";
        }else{
            var color = "#DCDCDC";
        }
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
            fillStyle: color
        });
        this.addChild(this.bgcolor1);
        this.rechargeFont1 = new Laya.Text();//文字
		this.rechargeFont1.font = "Impact";
        this.rechargeFont1.fontSize = 30;
        this.rechargeFont1.width = 300;
		this.rechargeFont1.wordWrap = true;
        if(setInitData['result']['rechargeData'][0]["userLimitFlag"] < setInitData['result']['rechargeData'][0]["rechargeLimit"] ){
			this.rechargeFont1.color = "#FF0000";
		    this.rechargeFont1.text = "￥"+setInitData['result']['rechargeData'][0]["money"];
			this.rechargeFont1.pos(150,320);
        }else{
			this.rechargeFont1.color = "#808080";
            this.rechargeFont1.text = "今日已达上限";
			this.rechargeFont1.pos(110,320);
        }
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2;
        
        if(setInitData['result']['rechargeData'][0]["userLimitFlag"] < setInitData['result']['rechargeData'][0]["rechargeLimit"]){
            //判断限冲次数
            this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][0]["money"]]);
        }
        this.addChild(this.rechargeFont1);

        this.coinImg2 = new Laya.Sprite();//图
        this.coinImg2.loadImage("comp/rechargeCoin.png");
        this.coinImg2.pos(420,170);
        this.addChild(this.coinImg2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
        if(setInitData['result']['rechargeData'][1]["userFirstFlag"] == 'is_first'){
		    this.curCoin2.text = "充"+setInitData['result']['rechargeData'][1]["coinText"]+"送"+setInitData['result']['rechargeData'][1]["giveCoin"];
        }else{
            this.curCoin2.text = "充"+setInitData['result']['rechargeData'][1]["coinText"];
        }
		this.curCoin2.leading = 5;
        this.curCoin2.pos(520,180);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
        if(setInitData['result']['rechargeData'][1]["userFirstFlag"] == 'is_first'){
		    this.curCoin2.text = setInitData['result']['rechargeData'][1]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][1]["giveCoin"]);
        }else{
            this.curCoin2.text = setInitData['result']['rechargeData'][1]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][1]['coin']);
        }
		this.curCoin2.leading = 5;
        this.curCoin2.pos(470,250);
        this.addChild(this.curCoin2);

        //判断是否首充
        if(setInitData['result']['rechargeData'][1]["userFirstFlag"] == 'is_first'){
            this.first = new Laya.Sprite();
            this.first.loadImage("comp/first.png");
            this.first.pos(640,150);
            this.first.scale(2,2);
            this.addChild(this.first);
        }

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
		this.rechargeFont1.text = "￥"+setInitData['result']['rechargeData'][1]["money"];
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2;
        this.rechargeFont1.pos(500,320);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][1]["money"]]);
        this.addChild(this.rechargeFont1);

        this.coinImg3 = new Laya.Sprite();//图
        this.coinImg3.loadImage("comp/rechargeCoin.png");
        this.coinImg3.pos(70,440);
        this.addChild(this.coinImg3);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = setInitData['result']['rechargeData'][2]["coinText"];
		this.curCoin2.leading = 5;
        this.curCoin2.pos(170,450);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = this.curCoin2.text = setInitData['result']['rechargeData'][2]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][2]['coin']);
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
		this.rechargeFont1.text = "￥"+setInitData['result']['rechargeData'][2]["money"];
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2;
        this.rechargeFont1.pos(150,590);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][2]["money"]]);
        this.addChild(this.rechargeFont1);

        this.coinImg4 = new Laya.Sprite();//图
        this.coinImg4.loadImage("comp/rechargeCoin.png");
        this.coinImg4.pos(420,440);
        this.addChild(this.coinImg4);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = setInitData['result']['rechargeData'][3]["coinText"];
		this.curCoin2.leading = 5;
        this.curCoin2.pos(520,450);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = this.curCoin2.text = setInitData['result']['rechargeData'][3]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][3]['coin']);
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
		this.rechargeFont1.text = "￥"+setInitData['result']['rechargeData'][3]["money"];
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2;
        this.rechargeFont1.pos(500,590);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][3]["money"]]);
        this.addChild(this.rechargeFont1);

        this.coinImg5 = new Laya.Sprite();//图
        this.coinImg5.loadImage("comp/rechargeCoin.png");
        this.coinImg5.pos(70,710);
        this.addChild(this.coinImg5);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = setInitData['result']['rechargeData'][4]["coinText"];
		this.curCoin2.leading = 5;
        this.curCoin2.pos(170,720);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = this.curCoin2.text = setInitData['result']['rechargeData'][4]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][4]['coin']);
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
		this.rechargeFont1.text = "￥"+setInitData['result']['rechargeData'][4]["money"];
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2;
        this.rechargeFont1.pos(150,860);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][4]["money"]]);
        this.addChild(this.rechargeFont1);

        this.coinImg6 = new Laya.Sprite();//图
        this.coinImg6.loadImage("comp/rechargeCoin.png");
        this.coinImg6.pos(420,710);
        this.addChild(this.coinImg6);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#000000";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = setInitData['result']['rechargeData'][5]["coinText"];
		this.curCoin2.leading = 5;
        this.curCoin2.pos(520,720);
        this.addChild(this.curCoin2);
        this.curCoin2 = new Laya.Text();//文字
        this.curCoin2.color = "#D0D0D0";
		this.curCoin2.font = "Impact";
        this.curCoin2.fontSize = 30;
        this.curCoin2.width = 300;
		this.curCoin2.wordWrap = true;
		this.curCoin2.text = this.curCoin2.text = setInitData['result']['rechargeData'][5]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][5]['coin']);
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
		this.rechargeFont1.text = "￥"+setInitData['result']['rechargeData'][5]["money"];
		this.rechargeFont1.leading = 5;
        this.rechargeFont1.zOrder = 2;
        this.rechargeFont1.pos(500,860);
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][5]["money"]]);
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
        this.curCoin.fontSize = 30;
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
        sessionStorage["toiletCatLastPage"] = window.location.href;
        $.ajax({
            url:"/toiletCat/api/recharge/userRecharge.action",
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

                var result = data["result"];

            if (typeof(result) == "string") {
                result = eval("(" + result + ")");
            }

            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId": result["appId"],     //公众号名称，由商户传入
                    "timeStamp": result["timeStamp"],         //时间戳，自1970年以来的秒数
                    "nonceStr": result["nonceStr"], //随机串
                    "package": result["package"],
                    "signType": result["signType"],         //微信签名方式：
                    "paySign": result["paySign"] //微信签名
                },
                function (res) {

                    // 微信前端返回支付成功/失败(终态)
                    if (res.err_msg == "get_brand_wcpay_request:ok" || res.err_msg == "get_brand_wcpay_request:fail") {

                        clickFlag = true;

                        $("#recharge_button").html("充值");

                        // 我方订单号
                        sessionStorage["toiletCatUserOrderNo"] = result["orderNo"];

                         window.location.href="/toiletCat/recharge/rechargeResult.html";

                        // 微信前端返回支付取消
                    } else if(res.err_msg == "get_brand_wcpay_request:cancel") {

                        clickFlag = true;

                        $("#recharge_button").html("充值");

                        cancelRecharge(result["orderNo"]);
                    }
                }
            );

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
