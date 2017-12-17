function recharge(){
	var recharge_scroll = $(window).scrollTop();
	var recharge_height = $(window).height();
	var recharge_hc = recharge_height + recharge_scroll;
	var str = "	<div style='top: " + recharge_hc + "px;' class='recharge'>";
	str += "		<div style='margin-left:5%;margin-top:5%;height:8%'>";
	str += "			<img style='-webkit-transform:rotate(-90deg);' src='/image1/cat_track.net.ico' width=10% onclick='closeRecharge()'/>";
	str += "		</div>";
	str += "		<div>";
	str += "			<div class='recharge-block' >";
	str += "				<div class='recharge-coin' >";
	str += "					<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "				</div>";
	str += "			<div class='recharge-coin' >";
	str += "				100";
	str += "			</div>";
	str += "			<div class='recharge-money' >";
	str += "				¥10.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div class='recharge-block' >";
	str += "			<div class='recharge-coin' >";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div class='recharge-coin' >";
	str += "				200 + 10";
	str += "			</div>";
	str += "			<div class='recharge-money'>";
	str += "				¥20.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div class='recharge-block' >";
	str += "			<div class='recharge-coin' >";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div class='recharge-coin' >";
	str += "				300 + 20";
	str += "			</div>";
	str += "			<div class='recharge-money' >";
	str += "				¥30.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div class='recharge-block' >";
	str += "			<div class='recharge-coin' >";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div class='recharge-coin' >";
	str += "				500 + 55";
	str += "			</div>";
	str += "			<div class='recharge-money' >";
	str += "				¥50.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div class='recharge-block' >";
	str += "			<div class='recharge-coin' >";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div class='recharge-coin' >";
	str += "				1000 + 120";
	str += "			</div>";
	str += "			<div class='recharge-money' >";
	str += "				¥100.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div class='recharge-block' >";
	str += "			<div class='recharge-coin' >";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div class='recharge-coin' >";
	str += "				2000 + 300";
	str += "			</div>";
	str += "			<div class='recharge-money' >";
	str += "				¥200.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "	</div>";
	$("body").append(str);
	$(".recharge").animate({
		top:recharge_scroll+"px"
	},500)
}

function closeRecharge(){
	$(".recharge").remove()
}
