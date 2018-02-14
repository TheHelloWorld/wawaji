var recharge_scroll = $(window).scrollTop();

var recharge_height = $(window).height();

var clickFlag = true;

var clickMoney = "";

var len = 0;

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

            str += "				<img src='/image/button/back_button.png' class='rechargeReturn' onclick='closeRecharge()'/>";

            str += "			</div>";

            str += "		<div class='recharge-user-coin' >";

            str += "			<img src='/image/background/coin_img.png' align='absmiddle' width=30% />"+  sessionStorage["toiletCatUserCoin"];

            str += "		</div>";

            str += "		<div class='clear:both'>";

            str += "		</div>";

            str += "	</div>";

            str += "	<div style='float: left;' >";

            len = list.length;

            var blackHeight = recharge_height / 5;

            for(var i = 0; i<list.length; i++) {

                var money_class = "recharge-money";

                var coin = list[i]["coin"];

                var coinText = list[i]["coinText"];

                var money = "<img src='/image/recharge/rmb.png' align='absbottom' width=15%>" + list[i]["money"];

                var recharge_class = "recharge-coin";

                // 判断是否限充
                if(list[i]["rechargeLimit"] != 0) {

                    recharge_class = "recharge-coin-limit";

                    if(list[i]["userLimitFlag"] < list[i]["rechargeLimit"]) {

                        str += "	<img class='recharge-limit' src='/image/recharge/limit_recharge.png'>";

                        str += "	<div id='recharge-block-" + i + "' class='recharge-block' style='height: " + blackHeight + "px' onclick=clickThis('" + i + "','" + list[i]["money"] + "') >";

                    } else {

                        str += "	<img class='recharge-limit' src='/image/recharge/limit_recharge.png'>";

                        str += "	<div id='recharge-block-" + i + "' class='recharge-block' style='height: " + blackHeight + "px' >";

                        money_class = "recharge-money-disabled";

                        money = "今日已达上限";
                    }

                    // 判断是否首充
                } else if(list[i]["firstFlag"] != 0) {

                    str += "	<div id='recharge-block-" + i + "' class='recharge-block' style='height: " + blackHeight + "px' onclick=clickThis('" + i + "','" + list[i]["money"] + "') >";

                    if(list[i]["userFirstFlag"] == "is_first") {

                        recharge_class = "recharge-coin-first";

                        str += "	<img class='recharge-first' src='/image/recharge/first_recharge.png'>";

                        coin += list[i]["giveCoin"];

                        coinText = "充" + coinText + "送" + list[i]["giveCoin"];
                    }

                } else {

                    str += "	<div id='recharge-block-" + i + "' class='recharge-block' style='height: " + blackHeight + "px' onclick=clickThis('" + i + "','" + list[i]["money"] + "') >";
                }

                str += "		<div class='" + recharge_class + "' >";

                str +=              coinText;

                str += "		</div>";

                str += "		<div class='recharge-coin-img' >";

                str += "			<img src='/image/recharge/coin_" + i + ".png' height=100% />";

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

            str += "<div id='recharge_button' class='recharge-button' onclick='rechargeThis()'>";

            str += "    充值";

            str += "</div>";

            str += "</div>";

            $("body").append(str);

            $(".recharge").animate({
                top:recharge_scroll + "px"
            },500);
        }
    });
}

function clickThis(id, amount) {

    for(var i = 0; i<len; i++) {

        $("#recharge-block-" + i).removeClass('recharge-selected');

        $("#recharge-block-" + i).addClass('recharge-block');
    }

    $("#recharge-block-" + id).removeClass('recharge-block');

    $("#recharge-block-" + id).addClass('recharge-selected');

    clickMoney = amount;

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
function rechargeThis() {

    if(!clickFlag) {
        return;
    }

    if(clickMoney == "") {

        toiletCatMsg("请选择金额", null);

        return;
    }

    clickFlag = false;

    $("#recharge_button").html("支付中...");

    $.ajax({
        url:"/toiletCat/api/recharge/userRecharge.action",
        type:"POST",
        async:false,
        data:{
            amount: clickMoney,
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

