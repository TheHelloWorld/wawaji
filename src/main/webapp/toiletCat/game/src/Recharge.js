var backObj = [];
var chongzhiText;
var Recharge = (function(_super){
    function Recharge(){
        Recharge.super(this);
        this.bgcolor = new Laya.Sprite();
        this.bgcolor.graphics.drawRect(0, 0, 540, 1060, "#83BCC7");
        this.addChild(this.bgcolor);

        this.topbg = new Laya.Sprite();
        this.topbg.graphics.drawRect(0, 0, 540, 80, "#48776D");
        this.addChild(this.topbg);

        this.back = new Laya.Sprite();
        this.back.loadImage("comp/back.png");
        this.back.pos(20,20);

        this.back.on(Laya.Event.CLICK,this,gameRechargeBack);
        this.addChild(this.back);

        this.coinImg = new Laya.Sprite();
        this.coinImg.loadImage("comp/rechargeCoin.png");
        this.coinImg.pos(180,20);
        this.coinImg.zOrder=11;
        this.addChild(this.coinImg);

        //顶部白色背景
        this.whitebg = new Laya.Sprite();
        this.whitebg.zOrder=1;
        this.whitebg.graphics.drawPath(180, 20, [
                ["moveTo", 20, 0],
                ["lineTo", 200, 0],
                ["arcTo", 210, 0, 210, 10, 10],
                ["lineTo", 210, 40],
                ["arcTo", 210, 50, 200, 50, 10],
                ["lineTo", 20, 50],
                ["arcTo", 0, 50, 0, 40, 10],
                ["lineTo", 0, 10],
                ["arcTo", 0, 0, 10, 0, 10],
                ["closePath"]
            ],
            {
                fillStyle: "#ffffff"
            });
        this.whitebg.zOrder = 10;
        this.addChild(this.whitebg);
        //当前游戏币个数
        this.curCoin = new Laya.Text();
        this.curCoin.color = "#000000";
        this.curCoin.font = "Impact";
        this.curCoin.fontSize = 30;
        this.curCoin.width = 300;
        this.curCoin.wordWrap = true;
        this.curCoin.text = sessionStorage["toiletCatUserCoin"];
        this.curCoin.leading = 5;
        this.curCoin.zOrder=11;
        this.curCoin.pos(260,25);
        this.addChild(this.curCoin);

        var j=0;
        //充值背景
        for(var i=0;i<3;i++){
            y = 110 + (i*260);
            this.rechargebg1 = new Laya.Sprite();
            this.rechargebg1.loadImage("comp/rechargeback.png");
            backObj[j] = this.rechargebg1;
            this.rechargebg1.pos(15,y);
            this.addChild(this.rechargebg1);
            this.rechargebg1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][j]["money"],j]);
            j++;
            this.rechargebg1 = new Laya.Sprite();
            this.rechargebg1.loadImage("comp/rechargeback.png");
            this.rechargebg1.pos(285,y);
            backObj[j] = this.rechargebg1;
            this.addChild(this.rechargebg1);
            this.rechargebg1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][j]["money"],j]);
            j++;
        }

        //充值1
        for(var i=0;i<6;i+=2){
            y = i * 130;
            this.curCoin1 = new Laya.Text();//文字
            this.curCoin1.color = "#D2B160";
            this.curCoin1.font = "Impact";
            this.curCoin1.fontSize = 30;
            this.curCoin1.width = 300;
            this.curCoin1.wordWrap = true;
            this.curCoin1.text = setInitData['result']['rechargeData'][i]["coinText"];
            this.curCoin1.leading = 5;
            this.curCoin1.pos(70,120+y);
            this.addChild(this.curCoin1);

            this.curCoin2 = new Laya.Text();//文字
            this.curCoin2.color = "#D2B160";
            this.curCoin2.font = "Impact";
            this.curCoin2.fontSize = 29;
            this.curCoin2.width = 300;
            this.curCoin2.wordWrap = true;
            if(i == 0){
                this.curCoin2.text =  setInitData['result']['rechargeData'][i]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][i]['coin'])+"("+setInitData['result']['rechargeData'][i]["userLimitFlag"]+"/"+setInitData['result']['rechargeData'][i]["rechargeLimit"]+")";
            }else{
                this.curCoin2.text =  setInitData['result']['rechargeData'][i]["showText"].replace("#{coin}",setInitData['result']['rechargeData'][i]['coin']);
            }
            this.curCoin2.leading = 5;
            this.curCoin2.pos(40,230+y);
            this.addChild(this.curCoin2);

            this.rechargeFont1 = new Laya.Text();//文字
            this.rechargeFont1.font = "Impact";
            this.rechargeFont1.fontSize = 40;
            this.rechargeFont1.width = 300;
            this.rechargeFont1.wordWrap = true;

            this.limit = new Laya.Sprite();
            this.limit.loadImage("comp/limit.png");
            this.limit.pos(170,90);
            this.limit.scale(2,2);
            this.addChild(this.limit);

            if(i > 0){
                this.coinImg1 = new Laya.Sprite();//图
                this.coinImg1.loadImage("comp/money.png");
                this.coinImg1.pos(50,285+y);
                this.addChild(this.coinImg1);
                this.rechargeFont1.fontSize = 40;
                this.rechargeFont1.color = "#FE613C";
                this.rechargeFont1.text = setInitData['result']['rechargeData'][i]["money"];
                this.rechargeFont1.pos(130,290+y);
            }else if(setInitData['result']['rechargeData'][i]["userLimitFlag"] < setInitData['result']['rechargeData'][i]["rechargeLimit"] ){
                this.coinImg1 = new Laya.Sprite();//图
                this.coinImg1.loadImage("comp/money.png");
                this.coinImg1.pos(50,285+y);
                this.addChild(this.coinImg1);
                this.rechargeFont1.fontSize = 40;
                this.rechargeFont1.color = "#FE613C";
                this.rechargeFont1.text = setInitData['result']['rechargeData'][i]["money"];
                this.rechargeFont1.pos(130,290+y);
            }else{
                this.rechargeFont1.fontSize = 30;
                this.rechargeFont1.color = "#FE613C";
                this.rechargeFont1.text = "今日已达上限";
                this.rechargeFont1.pos(50,290+y);
            }
            this.rechargeFont1.leading = 5;
            this.rechargeFont1.zOrder = 2;

            if(i == 0){
                if(setInitData['result']['rechargeData'][0]["userLimitFlag"] < setInitData['result']['rechargeData'][0]["rechargeLimit"]){
                    //判断限冲次数
                    this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][0]["money"],i]);
                }
            }else{
                this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][i]["money"],i]);
            }
            this.addChild(this.rechargeFont1);

//右侧
            this.curCoin1 = new Laya.Text();//文字
            this.curCoin1.color = "#D2B160";
            this.curCoin1.font = "Impact";
            this.curCoin1.fontSize = 30;
            this.curCoin1.width = 300;
            this.curCoin1.wordWrap = true;
            if(i == 0){
                this.curCoin1.text = "充"+setInitData['result']['rechargeData'][i+1]["coinText"];
            }else{
                this.curCoin1.text = setInitData['result']['rechargeData'][i+1]["coinText"];
            }
            this.curCoin1.leading = 5;
            this.curCoin1.pos(340,120+y);
            this.addChild(this.curCoin1);

            this.curCoin2 = new Laya.Text();//文字
            this.curCoin2.color = "#D2B160";
            this.curCoin2.font = "Impact";
            this.curCoin2.fontSize = 29;
            this.curCoin2.width = 300;
            this.curCoin2.wordWrap = true;
            if(i == 0){
                if(setInitData['result']['rechargeData'][1]["userFirstFlag"] == 'is_first'){
                    this.curCoin2.text = "充"+setInitData['result']['rechargeData'][1]["coinText"]+"送"+setInitData['result']['rechargeData'][1]["giveCoin"];
                }else{
                    this.curCoin2.text = "充"+setInitData['result']['rechargeData'][1]["coinText"]+"马桶币";
                }
            }else{
                this.curCoin2.text = setInitData['result']['rechargeData'][i+1]["coinText"];
            }

            this.curCoin2.leading = 5;
            this.curCoin2.pos(310,230+y);
            this.addChild(this.curCoin2);

            this.rechargeFont1 = new Laya.Text();//文字
            this.rechargeFont1.font = "Impact";
            this.rechargeFont1.fontSize = 40;
            this.rechargeFont1.width = 300;
            this.rechargeFont1.wordWrap = true;

            if(i == 0 && setInitData['result']['rechargeData'][1]["userFirstFlag"] == 'is_first'){
                this.limit = new Laya.Sprite();
                this.limit.loadImage("comp/first.png");
                this.limit.pos(420,90);
                this.limit.scale(2,2);
                this.addChild(this.limit);
            }

            this.coinImg1 = new Laya.Sprite();//图
            this.coinImg1.loadImage("comp/money.png");
            this.coinImg1.pos(320,285+y);
            this.addChild(this.coinImg1);
            this.rechargeFont1.fontSize = 40;
            this.rechargeFont1.color = "#FE613C";
            this.rechargeFont1.text = setInitData['result']['rechargeData'][i+1]["money"];
            this.rechargeFont1.pos(400,290+y);

            this.rechargeFont1.leading = 5;
            this.rechargeFont1.zOrder = 2;

            this.addChild(this.rechargeFont1);
            this.rechargeFont1.on(Laya.Event.CLICK,this,gameRecharge,[setInitData['result']['rechargeData'][i+1]["money"],i+1]);
        }

        for(var i=0;i<6;i++){
            var y = i * 130;
            var x = i*10;
            this.coinImg1 = new Laya.Sprite();//图
            this.coinImg1.loadImage("comp/coin"+i+".png");
            this.coinImg1.pos(100-x,170+y);
            this.addChild(this.coinImg1);
            i++;
            this.coinImg1 = new Laya.Sprite();//图
            this.coinImg1.loadImage("comp/coin"+i+".png");
            this.coinImg1.pos(370-x,170+y);
            this.addChild(this.coinImg1);
        }

        //确认充值按钮
        this.whitebg = new Laya.Sprite();
        this.whitebg.zOrder=1;
        this.whitebg.graphics.drawPath(180, 890, [
                ["moveTo", 20, 0],
                ["lineTo", 200, 0],
                ["arcTo", 210, 0, 210, 10, 10],
                ["lineTo", 210, 40],
                ["arcTo", 210, 50, 200, 50, 10],
                ["lineTo", 20, 50],
                ["arcTo", 0, 50, 0, 40, 10],
                ["lineTo", 0, 10],
                ["arcTo", 0, 0, 10, 0, 10],
                ["closePath"]
            ],
            {
                fillStyle: "#9ACD32"
            });
        this.addChild(this.whitebg);
        this.whitebg.on(Laya.Event.CLICK,this,gameRechargeSure);


        this.rechargeFont1 = new Laya.Text();//文字
        this.rechargeFont1.font = "Impact";
        this.rechargeFont1.fontSize = 40;
        this.rechargeFont1.width = 300;
        this.rechargeFont1.wordWrap = true;
        this.rechargeFont1.fontSize = 30;
        this.rechargeFont1.color = "black";
        this.rechargeFont1.text = '充值';
        this.rechargeFont1.zOrder = 11;
        this.rechargeFont1.pos(250,895);
        chongzhiText = this.rechargeFont1;
        this.rechargeFont1.on(Laya.Event.CLICK,this,gameRechargeSure);
        this.addChild(this.rechargeFont1);
    }

    var rechargeDataNum = 0;
//充值操作
    function gameRecharge(data, id){
        for(var i=0;i<6;i++){
            backObj[i].loadImage('comp/rechargeback.png');
        }
        backObj[id].loadImage('comp/rechargebacking.png');
        rechargeDataNum = data;
    }
    Laya.class(gameRecharge,"gameRecharge",_super);

    function gameRechargeSure(){
        if( rechargeDataNum != 0 ){
            chongzhiText.text = '充值中...';
            chongzhiText.x = 230;
            rechargeThis(rechargeDataNum);
        }
    }

    Laya.class(gameRechargeSure,"gameRechargeSure",_super);

    // 充值操作
    function rechargeThis(amount) {

        sessionStorage["toiletCatLastPage"] = window.location.href;

        var rechargeType = "web_game_wxpay";

        if(sessionStorage["toiletCatType"] == "app") {

            rechargeType = "app_game_wxpay";
        }

        $.ajax({
            url:"/toiletCat/api/recharge/userRecharge.action",
            type:"POST",
            async:false,
            data:{
                amount:amount,
                rechargeType:rechargeType,
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

                // 判断是web端还是app端
                if(sessionStorage["toiletCatType"] == "app") {

                    // 将参数传给native端
                    window.android.pay(result);

                    clickFlag = true;

                    chongzhiText.text = '充值';
                    chongzhiText.x = 250;
                } else {

                    gameWebRecharge(result);

                }
            }
        });
    }

    // web端充值
    function gameWebRecharge(data) {

        if (typeof(data) == "string") {
            data = eval("(" + data + ")");
        }

        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId": data["appId"],     //公众号名称，由商户传入
                "timeStamp": String(data["timeStamp"]),         //时间戳，自1970年以来的秒数
                "nonceStr": data["nonceStr"], //随机串
                "package": data["package"],
                "signType": data["signType"],         //微信签名方式：
                "paySign": data["paySign"] //微信签名
            },
            function (res) {

                // 微信前端返回支付成功/失败(终态)
                if (res.err_msg == "get_brand_wcpay_request:ok" || res.err_msg == "get_brand_wcpay_request:fail") {

                    clickFlag = true;

                    chongzhiText.text = '充值';
                    chongzhiText.x = 250;

                    // 我方订单号
                    sessionStorage["toiletCatUserOrderNo"] = data["orderNo"];

                    window.location.href="/toiletCat/recharge/rechargeResult.html";

                    // 微信前端返回支付取消
                } else if(res.err_msg == "get_brand_wcpay_request:cancel") {

                    clickFlag = true;

                    chongzhiText.text = '充值';
                    chongzhiText.x = 250;

                    cancelRecharge(data["orderNo"]);
                }
            }
        );
    }

    // 取消支付操作
    function cancelRecharge(orderNo) {

        $.ajax({
            url:"/toiletCat/api/recharge/cancelRechargeByOrderNo.action",
            type:"POST",
            async:false,
            data:{
                orderNo: orderNo,
                userNo: sessionStorage["toiletCatUserNo"]
            },
            success:function(data) {

                // 转换数据
                if (typeof(data) == "string") {

                    data = eval("(" + data + ")");
                }

                // 判断是否成功
                if (data["is_success"] != "success") {

                    toiletCatMsg(data["result"], null);

                }
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
            gameRechargeObj.y +=30;
        }
    }
    Laya.class(rechargeThis,"rechargeThis",_super);


    Laya.class(Recharge,"Recharge", _super);
    return Recharge;
})(Laya.Sprite);
