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
                // 判断是否限充
                if(list[i]["rechargeLimit"] != 0) {

                    if(list[i]["userLimitFlag"] < list[i]["rechargeLimit"]) {
                        str += "	<div class='recharge-block' onclick=rechargeThis('" + list[i]["money"] + "') >";
                        str += "	<img class='recharge-limit' src='/image/recharge/limit_recharge.png'>";
                    } else {
                        str += "	<div class='recharge-disabled' >";
                        money_class = "recharge-money-disabled";
                    }
                    // 判断是否首充
                } else if(list[i]["firstFlag"] != 0) {
                    str += "	<div class='recharge-block' onclick=rechargeThis('" + list[i]["money"] + "') >";
                    if(list[i]["userFirstFlag"] == "is_first") {
                        str += "	<img class='recharge-first' src='/image/recharge/first_recharge.png'>";
                        coin += list[i]["giveCoin"];
                        coinText = "充" + coinText + "送" + list[i]["giveCoin"];
                    }
                } else {
                    str += "	<div class='recharge-block' onclick=rechargeThis('" + list[i]["money"] + "') >";
                }

                str += "		<div class='recharge-coin' >";
                str += "			<img src='/image/background/coin-img.png' width=100% height=100% />";
                str += "		</div>";
                str += "		<div class='recharge-coin' >";
                str +=              coinText;
                str += "		</div>";
                str += "		<div class='recharge-coin-text' >";
                str +=               list[i]["showText"].replace("#{coin}",coin);
                if(list[i]["rechargeLimit"] != 0) {
                    str += " (" + list[i]["userLimitFlag"] + "/" + list[i]["rechargeLimit"] + ")";
                }
                str += "		</div>";
                str += "		<div class='" + money_class + "' >";
                str += "			¥" + list[i]["money"];
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

function closeRecharge() {

    $(".recharge").animate({
        top:recharge_height+"px"
    },500);
    setTimeout(function rechargeRemove() {
        $(".recharge").remove()
    },500);
}

// 充值操作
function rechargeThis(amount) {
    $.ajax({
        url:"/toiletCat/api/user/userRecharge.action",
        type:"POST",
        async:false,
        data:{
            amount:amount,
            rechargeType:"wxpay",
            userNo:userNo
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

