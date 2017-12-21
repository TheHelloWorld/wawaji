var recharge_scroll = $(window).scrollTop();

var recharge_height = $(window).height();

function recharge() {

    var recharge_hc = recharge_height + recharge_scroll;
    var str = "	<div style='top:"+recharge_hc+"px;' class='recharge'>";
    str += "		<div class='recharge-first-line' >";
    str += "			<div style='float:left;'>";
    str += "				<img style='-webkit-transform:rotate(-90deg);' src='/image/background/returnButton.ico' width=100% onclick='closeRecharge()'/>";
    str += "			</div>";
    str += "		<div class='recharge-user-coin' >";
    str += "			<div class='recharge-coin' >";
    str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
    str += "			</div>";
    str += "			<div class='recharge-coin' id='user-recharge-coin' >";
    str += "				"+sessionStorage["toiletCatUserCoin"];
    str += "			</div>";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "	<div style='float: left;' >";
    str += "	<div class='recharge-block' onclick='rechargeThis(10,100)' >";
    str += "		<div class='recharge-coin' >";
    str += "			<img src='/image/background/coin.ico' width=100% height=100% />";
    str += "		</div>";
    str += "		<div class='recharge-coin' >";
    str += "			100";
    str += "		</div>";
    str += "		<div class='recharge-coin-text' >";
    str += "			充100游戏币";
    str += "		</div>";
    str += "		<div class='recharge-money' >";
    str += "			¥10.00";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "	<div class='recharge-block' onclick='rechargeThis(20,210)' >";
    str += "		<div class='recharge-coin' >";
    str += "			<img src='/image/background/coin.ico' width=100% height=100% />";
    str += "		</div>";
    str += "		<div class='recharge-coin' >";
    str += "			200+10";
    str += "		</div>";
    str += "		<div class='recharge-coin-text' >";
    str += "			充210游戏币";
    str += "		</div>";
    str += "		<div class='recharge-money' >";
    str += "			¥20.00";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "	<div class='recharge-block' onclick='rechargeThis(30,330)' >";
    str += "		<div class='recharge-coin' >";
    str += "			<img src='/image/background/coin.ico' width=100% height=100% />";
    str += "		</div>";
    str += "		<div class='recharge-coin' >";
    str += "			300+30";
    str += "		</div>";
    str += "		<div class='recharge-coin-text' >";
    str += "			充330游戏币";
    str += "		</div>";
    str += "		<div class='recharge-money' >";
    str += "			¥30.00";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "	<div class='recharge-block' onclick='rechargeThis(30,330)' >";
    str += "		<div class='recharge-coin' >";
    str += "			<img src='/image/background/coin.ico' width=100% height=100% />";
    str += "		</div>";
    str += "		<div class='recharge-coin' >";
    str += "			500+50";
    str += "		</div>";
    str += "		<div class='recharge-coin-text' >";
    str += "			充550游戏币";
    str += "		</div>";
    str += "		<div class='recharge-money' >";
    str += "			¥50.00";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "	<div class='recharge-block' onclick='rechargeThis(100,1180)' >";
    str += "		<div class='recharge-coin' >";
    str += "			<img src='/image/background/coin.ico' width=100% height=100% />";
    str += "		</div>";
    str += "		<div class='recharge-coin' >";
    str += "			1000+180";
    str += "		</div>";
    str += "		<div class='recharge-coin-text' >";
    str += "			充1180游戏币";
    str += "		</div>";
    str += "		<div class='recharge-money' >";
    str += "			¥100.00";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "	<div class='recharge-block' onclick='rechargeThis(200,2400)' >";
    str += "		<div class='recharge-coin' >";
    str += "			<img src='/image/background/coin.ico' width=100% height=100% />";
    str += "		</div>";
    str += "		<div class='recharge-coin' >";
    str += "			2000+400";
    str += "		</div>";
    str += "		<div class='recharge-coin-text' >";
    str += "			充2400游戏币";
    str += "		</div>";
    str += "		<div class='recharge-money' >";
    str += "			¥200.00";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "</div>";
    $("body").append(str);
    $(".recharge").animate({
        top:recharge_scroll+"px"
    },500)
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
function rechargeThis(amount, coin) {
    $.ajax({
        url:"/toiletCat/user/userRecharge.action",
        type:"POST",
        async:false,
        data:{
            amount:amount,
            coin:coin,
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

            var nowCoin = parseInt(sessionStorage["toiletCatUserCoin"]) + coin;

            updateUserCoin(nowCoin);

            sessionStorage["toiletCatUserCoin"] = nowCoin;

            toiletCatMsg("充值成功", "closeRecharge()");
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

