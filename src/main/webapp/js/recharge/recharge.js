var recharge_scroll = $(window).scrollTop();

var recharge_height = $(window).height();

function recharge() {

    $.ajax({
        url:"/toiletCat/api/moneyForCoin/getAllCanSeeMoneyForCoin.action",
        type:"POST",
        async:false,
        data:{
            userNo:sessionStorage["toiletCatUserNo"]
        },
        success:function(data) {

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
                return;
            }

            var list = data["result"];

            var recharge_hc = recharge_height + recharge_scroll;
            var str = "	<div style='top:"+recharge_hc+"px;' class='recharge'>";
            str += "		<div class='recharge-first-line' >";
            str += "			<div style='float:left;height: 100%'>";
            str += "				<img style='-webkit-transform:rotate(-90deg);' src='/image/returnButton.png' class='rechargeReturn' onclick='closeRecharge()'/>";
            str += "			</div>";
            str += "		<div class='recharge-user-coin' >";
            str += "			<div class='recharge-coin' >";
            str += "				<img src='/image/background/coin-img.png' width=100% height=100% />";
            str += "			</div>";
            str += "			<div class='recharge-coin' id='user-recharge-coin' >";
            str += "				" + sessionStorage["toiletCatUserCoin"];
            str += "			</div>";
            str += "		</div>";
            str += "		<div class='clear:both'>";
            str += "		</div>";
            str += "	</div>";
            str += "	<div style='float: left;' >";

            for(var i = 0; i<list.length; i++) {
                var money_class = "recharge-money";

                var coin = list[i]["coin"];
                var coinText = list[i]["coinText"];

                var money = "¥" + list[i]["money"];

                var recharge_class = "recharge-coin";

                // 判断是否限充
                if(list[i]["rechargeLimit"] != 0) {
                    recharge_class = "recharge-coin-limit";
                    if(list[i]["userLimitFlag"] < list[i]["rechargeLimit"]) {
                        str += "	<div class='recharge-block' onclick=rechargeThis('" + list[i]["money"] + "') >";
                        str += "	<img class='recharge-limit' src='/image/recharge/limit_recharge.png'>";
                    } else {
                        str += "	<div class='recharge-block' >";
                        str += "	<img class='recharge-limit' src='/image/recharge/limit_recharge.png'>";
                        money_class = "recharge-money-disabled";
                        money = "今日已达上限";
                    }
                    // 判断是否首充
                } else if(list[i]["firstFlag"] != 0) {
                    str += "	<div class='recharge-block' onclick=rechargeThis('" + list[i]["money"] + "') >";
                    if(list[i]["userFirstFlag"] == "is_first") {
                        recharge_class = "recharge-coin-first";
                        str += "	<img class='recharge-first' src='/image/recharge/first_recharge.png'>";
                        coin += list[i]["giveCoin"];
                        coinText = "充" + coinText + "送" + list[i]["giveCoin"];
                    }
                } else {
                    str += "	<div class='recharge-block' onclick=rechargeThis('" + list[i]["money"] + "') >";
                }

                str += "		<div class='" + recharge_class + "' >";
                str += "			<img src='/image/background/coin-img.png' width=100% height=100% />";
                str += "		</div>";
                str += "		<div class='" + recharge_class + "' >";
                str +=              coinText;
                str += "		</div>";
                str += "		<div class='recharge-coin-text' >";
                str +=               list[i]["showText"].replace("#{coin}",coin);
                if(list[i]["rechargeLimit"] != 0) {
                    str += " (" + list[i]["userLimitFlag"] + "/" + list[i]["rechargeLimit"] + ")";
                }
                str += "		</div>";
                str += "		<div class='" + money_class + "' >";
                str +=              money;
                str += "		</div>";
                str += "		<div class='clear:both'>";
                str += "		</div>";
                str += "	</div>";
            }

            str += "</div>";

            $("body").append(str);
            $(".recharge").animate({
                top:recharge_scroll + "px"
            },500);
        }
    });
}

// 关闭充值页
function closeRecharge() {

    $(".recharge").animate({
        top: recharge_height+"px"
    },500);
    setTimeout(function rechargeRemove() {
        $(".recharge").remove()
    },500);
}

// 充值操作
function rechargeThis(amount) {
    $.ajax({
        url:"/toiletCat/api/recharge/userRecharge.action",
        type:"POST",
        async:false,
        data:{
            amount: amount,
            rechargeType: "wxpay",
            userNo: sessionStorage["toiletCatUserNo"]
        },
        success:function(data) {

            // 转换数据
            if (typeof(data) == "string") {
                data = eval("(" + data + ")");
            }

            // 判断是否成功
            if (data["is_success"] != "success") {
                alert(data["result"]);
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

                        queryResultByOrderNo(result["orderNo"]);

                        // 微信前端返回支付取消
                    } else if(res.err_msg == "get_brand_wcpay_request:cancel") {

                        cancelRecharge(result["orderNo"]);
                    }
                }
            );
        }
    });
}

// 根据订单号查询支付结果
function queryResultByOrderNo(orderNo) {

    $.ajax({
        url:"/toiletCat/api/recharge/getRechargeResultByOrderNo.action",
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
                alert(data["result"]);
                return;
            }

            var result = data["result"];

            if (typeof(result) == "string") {
                result = eval("(" + result + ")");
            }

            if(result["result"] == "fail") {

                toiletCatMsg("充值失败 QAQ", "closeRecharge()");

            } else if(result["result"] == "success") {

                // 用户游戏币数
                sessionStorage["toiletCatUserCoin"] = result["userCoin"];

                toiletCatMsg("充值成功", "closeRecharge()");

            }
        }
    });
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

// 变更充值后金额
function updateUserCoin(nowCoin) {
    // 游戏房间主页
    $("#gameRoomIndexUserCoin").html(nowCoin);
    // 用户充值页
    $("#user-recharge-coin").html(nowCoin);
    // 用户主页
    $("#userIndexUserCoin").html(nowCoin);
    // 游戏页
    $(".showCurCoin").html(nowCoin);
}

