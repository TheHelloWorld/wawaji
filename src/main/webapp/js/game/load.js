var hc=0, wc=0, di_cha;//屏幕高 阴影最大最小值差

var zhuazi_size = {"up":[], "left":[], "right":[], "di":[] }; //爪子4个部件 长宽左高初始值
var wawa_init_position = {'2':[], '5':{0:[0.18,0.4,0.63,0.37,0.6],1:[90,92],2:[91,91,91,93,93]}, '8':[] };//娃娃位置初始值
var wawa_init_size = {'2':[], '5':[185,244], '8':[] };
var max = {"up":[], "left":[], "right":[], "di":[] }; //爪子4部分 极限位置
var obj = {"up":"","left":"","right":"","di":"","wawa":"","wawadi":""}; //对象
var gift_position = []; //所有礼物四边初始位置
var current_size = {"up":[], "left":[], "right":[], "di":[] };

var flag_status = {"move":true,"get":true};
var wawa_num = 5;

function load() {
	hc = $(window).height();
	wc = $(window).width();
	//初始化屏幕大小
	$(".game-middle").css({'height': hc});
	$(".game-top").css({'height': hc*0.05});
	$(".game-bottom").css({'height': hc*0.3});
	$(".zzc").css({'height': hc*0.5});
	$(".resultMsg").css('height',hc);
	$(".successPerson").css({'height': hc*0.9,'margin-top':hc*0.1});
	$(".broadcastSuccess").css('height',hc*0.1);
	$(".broadcastSuccessBack").css('height',hc*0.1);
	
	//初始化要用到的爪子对象
	obj['up'] = $(".grasp-up");
	obj['left'] = $(".grasp-left");
	obj['right'] = $(".grasp-right");
	obj['di'] = $(".grasp-down");
	
	//获取爪子 3个部件的宽高
	zhuazi_size['up']['width'] = $(".grasp-up").css('width');
	zhuazi_size['up']['height'] = $(".grasp-up").css('height');
	
	zhuazi_size['left']['width'] = $(".grasp-left").css('width');
	zhuazi_size['left']['height'] = $(".grasp-left").css('height');
	
	zhuazi_size['right']['width'] = $(".grasp-right").css('width');
	zhuazi_size['right']['height'] = $(".grasp-right").css('height');
	
	current_size['up']['width'] = $(".grasp-up").css('width');
	current_size['up']['height'] = $(".grasp-up").css('height');
	
	current_size['left']['width'] = $(".grasp-left").css('width');
	current_size['left']['height'] = $(".grasp-left").css('height');
	
	current_size['right']['width'] = $(".grasp-right").css('width');
	current_size['right']['height'] = $(".grasp-right").css('height');
	
	//获取爪子影子的宽高和初始位置
	zhuazi_size['di']['top'] = hc*0.61;//605
	zhuazi_size['di']['left'] = wc*0.17;//125
	
	current_size['di']['top'] = hc*0.61;//605
	current_size['di']['left'] = wc*0.17;//125
	
	obj['di'].css('top',zhuazi_size['di']['top']+"px");
	obj['di'].css('left',zhuazi_size['di']['left']+"px");
	zhuazi_size['di']['width'] = (wc * 0.2)+"px";
	current_size['di']['width'] = (wc * 0.2)+"px";
	obj['di'].css('width',zhuazi_size['di']['width']);
	zhuazi_size['di']['height'] = obj['di'].css('height');
	current_size['di']['height'] = obj['di'].css('height');
	di_cha = hc * 0.135;
	
	//定义爪子三个部件的初始位置
	zhuazi_size['up']['left'] = wc*0.17;
	zhuazi_size['left']['left'] = wc*0.16;
	zhuazi_size['right']['left'] = wc*0.33;
	//获取初始top值
	zhuazi_size['up']['top'] = parseFloat(obj['up'].css('top'));
	zhuazi_size['left']['top'] = parseFloat(obj['left'].css('top'));
	zhuazi_size['right']['top'] = parseFloat(obj['right'].css('top'));
	
	current_size['up']['left'] = wc*0.17;
	current_size['left']['left'] = wc*0.16;
	current_size['right']['left'] = wc*0.33;
	//获取初始top值
	current_size['up']['top'] = parseFloat(obj['up'].css('top'));
	current_size['left']['top'] = parseFloat(obj['left'].css('top'));
	current_size['right']['top'] = parseFloat(obj['right'].css('top'));
	//设置初始left值
	obj['up'].css({'left': zhuazi_size['up']['left']+"px"});
	obj['left'].css({'left': zhuazi_size['left']['left']+"px"});
	obj['right'].css({'left': zhuazi_size['right']['left']+"px"});
	
	max['up']['left'] = wc*0.765;
	max['left']['left'] = wc*0.71;
	max['right']['left'] = wc*0.82;
	max['right']['di'] = wc*0.685;

	var wawa_width = wawa_init_size[wawa_num][0];
	var wawa_height = wawa_init_size[wawa_num][1];
	//接下来布置娃娃位置大小
	var str = "";
	for(var key in wawa_init_position[wawa_num][0]){
		var top,left,top2,index;
		if(key < 3){ 
			top = hc*0.37+"px";
			top2 = hc*0.37+wawa_init_size[wawa_num][1]-10+"px";
			index = wawa_init_position[wawa_num][1][0];
		}else{ 
			top = hc*0.475+"px";
			top2 = hc*0.475+wawa_init_size[wawa_num][1]-10+"px";
			index = wawa_init_position[wawa_num][1][1]
		}
		left = wc * wawa_init_position[wawa_num][0][key]+"px";
		str += '<div id="liwu'+key+'" style="position:absolute;z-index:'+index+';top:'+top+';left:'+left+'"><img src="'+toyRoomImg+'" class="wawa" style="z-index:'+index+'"/></div><div id="wawaback'+key+'" style="top:'+top2+';left:'+left+';width:185px;height:20px;background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;position:absolute;z-index:'+index+';text-align:center" class="wawaback"><img src="image1/wawadi.png" class="wawaback" style="width:90%;height:30px;z-index:'+index+'"/></div>';
	}
	$(".loadgift").append(str);
	//获取每个娃娃的上下左右四个位置
	for(var key in wawa_init_position[wawa_num][0]){
		gift_position[key] = [];
		gift_position[key]['left'] = wc * wawa_init_position[wawa_num][0][key] + (wawa_width/3);
		gift_position[key]['right'] =  wc * wawa_init_position[wawa_num][0][key] + (wawa_width/3 *2);
		if(key < 3){ top = hc*0.37; }else{ top = hc*0.475; }
		gift_position[key]['top'] = top + wawa_height;
		gift_position[key]['down'] = top + wawa_height+20;
	}
	$(".loadinggif").remove();
	$(".loading").remove();
}

window.ontouchstart = function(e) { e.preventDefault(); };

function showDiv(type){
	if(type == 'list'){
		$(".successPersonTitle-left").css('border-bottom','3px solid rgb(109, 219, 246)');
		$(".successPersonTitle-right").css('border-bottom','0px solid rgb(109, 219, 246)');
		$(".success-list").show();
		$(".success-detail").hide();
	}else{
		$(".successPersonTitle-left").css('border-bottom','0px solid rgb(109, 219, 246)');
		$(".successPersonTitle-right").css('border-bottom','3px solid rgb(109, 219, 246)');
		$(".success-list").hide();
		$(".success-detail").show();
	}
}

function setButton(){
	$(".button-top").attr({"ontouchstart":"moveSmall()", "ontouchend":"stopMove()"});
	$(".button-left").attr({"ontouchstart":"moveLeft()", "ontouchend":"stopMove()"});
	$(".button-bottom").attr({"ontouchstart":"moveBig()", "ontouchend":"stopMove()"});
	$(".button-right").attr({"ontouchstart":"moveRight()", "ontouchend":"stopMove()"});
	$(".button-get").attr({"ontouchstart":"catchToy()"});
}