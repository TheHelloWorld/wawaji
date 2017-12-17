function recharge(){
    var recharge_scroll = $(window).scrollTop();
    var recharge_height = $(window).height();
    var recharge_hc = recharge_height + recharge_scroll;
    var str = "	<div style='top:"+recharge_hc+"px;' class='recharge'>";
    str += "		<div class='recharge-coin' >";
    str += "			<div style='float:left;'>";
    str += "				<img style='-webkit-transform:rotate(-90deg);' src='/image/cat_track.net.ico' width=10% onclick='closeRecharge()'/>";
    str += "			</div>";
    str += "		<div style='float:left;background:white;width:50%;border-radius:1.25rem;'>";
    str += "			<div class='recharge-coin' >";
    str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
    str += "			</div>";
    str += "			<div class='recharge-coin' >";
    str += "				200";
    str += "			</div>";
    str += "		</div>";
    str += "		<div class='clear:both'>";
    str += "		</div>";
    str += "	</div>";
    str += "	<div>";
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

function closeRecharge(){
    $(".recharge").remove()
}

