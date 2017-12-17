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
    str += "			<div class='recharge-coin' >";
    str += "				"+sessionStorage["toiletCatUserCoin"];
    str += "			</div>";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "	<div style='float: left;' >";
    str += "	<div class='recharge-block' >";
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
    str += "	<div class='recharge-block' >";
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
    str += "	<div class='recharge-block' >";
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
    str += "	<div class='recharge-block' >";
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
    str += "	<div class='recharge-block' >";
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
    str += "	<div class='recharge-block' >";
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

