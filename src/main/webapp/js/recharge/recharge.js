function recharge(){
	var recharge_scroll = $(window).scrollTop();
	var recharge_height = $(window).height();
	var recharge_hc = recharge_height + recharge_scroll
	var str = "	<div style='z-index:99;position:fixed;background:#6DDBFD;top:" + recharge_hc + "px;left:0px;width:100%;height:100%;' class='recharge'>";
	str += "		<div style='margin-left:5%;margin-top:5%;height:8%'>";
	str += "			<img style='-webkit-transform:rotate(-90deg);' src='/image1/cat_track.net.ico' width=10% ontouchstart='closeRecharge()'/>";
	str += "		</div>";
	str += "		<div>";
	str += "			<div style='border-bottom:3px solid #D7D7D7;width:90%;margin-left:5%;color:white'>";
	str += "				<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "					<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "				</div>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				100";
	str += "			</div>";
	str += "			<div style='color:red;float:right;margin-top:10%;margin-right:5%;background:url(/image1/start.png);background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;text-align:center;width:30%;'>";
	str += "				¥10.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div style='border-bottom:3px solid #D7D7D7;width:90%;margin-left:5%;color:white'>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				200 + 10";
	str += "			</div>";
	str += "			<div style='color:red;float:right;margin-top:10%;margin-right:5%;background:url(/image1/start.png);background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;text-align:center;width:30%;'>";
	str += "				¥20.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div style='border-bottom:3px solid #D7D7D7;width:90%;margin-left:5%;color:white'>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				300 + 20";
	str += "			</div>";
	str += "			<div style='color:red;float:right;margin-top:10%;margin-right:5%;background:url(/image1/start.png);background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;text-align:center;width:30%;'>";
	str += "				¥30.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div style='border-bottom:3px solid #D7D7D7;width:90%;margin-left:5%;color:white'>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				500 + 55";
	str += "			</div>";
	str += "			<div style='color:red;float:right;margin-top:10%;margin-right:5%;background:url(/image1/start.png);background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;text-align:center;width:30%;'>";
	str += "				¥50.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div style='border-bottom:3px solid #D7D7D7;width:90%;margin-left:5%;color:white'>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				1000 + 120";
	str += "			</div>";
	str += "			<div style='color:red;float:right;margin-top:10%;margin-right:5%;background:url(/image1/start.png);background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;text-align:center;width:30%;'>";
	str += "				¥100.00";
	str += "			</div>";
	str += "			<div style='clear:both'>";
	str += "			</div>";
	str += "		</div>";
	str += "		<div style='border-bottom:3px solid #D7D7D7;width:90%;margin-left:5%;color:white'>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				<img src='/image/background/coin.ico' width=100% height=100% />";
	str += "			</div>";
	str += "			<div style='float:left;margin-top:10%;margin-left:5%;'>";
	str += "				2000 + 300";
	str += "			</div>";
	str += "			<div style='color:red;float:right;margin-top:10%;margin-right:5%;background:url(/image1/start.png);background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;text-align:center;width:30%;'>";
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
