var recharge_scroll = $(window).scrollTop();

var recharge_height = $(window).height();

function recharge(){
    $(".recharge").remove();
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
    str += "				"+userCoin;
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
    str += "			220";
    str += "		</div>";
    str += "		<div class='recharge-coin-text' >";
    str += "			充200送20";
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
    str += "			340";
    str += "		</div>";
    str += "		<div class='recharge-coin-text' >";
    str += "			充300送20";
    str += "		</div>";
    str += "		<div class='recharge-money' >";
    str += "			¥20.00";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "</div>";
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

}

