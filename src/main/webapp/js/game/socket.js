var wsUri ="ws://45.76.51.107:20002/"; 
var output;  
var uId;
var rId;
var coin;
var time1;
var toyImg;
var toyNo;
var toyNowCoin;
var toyName;
var toyRoomImg;
var luckyNum;
var catchId;

function startWebSocket() { 
    websocket = new WebSocket(wsUri); 
    websocket.onopen = function(evt) { 
        onOpen(evt)
    }; 
    websocket.onclose = function(evt) { 
        onClose(evt) 
    }; 
    websocket.onmessage = function(evt) { 
        onMessage(evt) 
    }; 
    websocket.onerror = function(evt) { 
        onError(evt) 
    };
}  
startWebSocket()
function onOpen(evt) {
	getInfo();
}  

function onClose(evt) {
	
}  

function onMessage(evt) {
	data = eval('('+evt.data+')');
	if(data.type == 'init'){
		setInit(data);
	}else if(data.type == 'successList'){
		successList(data);
	}else if(data.type == 'start'){
		startGameStatus(data);
	}else if(data.type == 'catchStatus'){
		catchStatus(data);
	}else if(data.type == 'broadcast'){
		broadcast(data);
	}else if(data.type == 'newSuccess'){
		newSuccess();
	}else if(data.type == 'refreshCurNum'){
		refreshCurNum(data);
	}
}

function refreshCurNum(data){
	$(".curPlayer").html('在线人数:'+data.count);
}

function timer(intDiff){
	time1 = window.setInterval(function(){
		if((intDiff) == 0){
			window.clearInterval(time1);
		}else{
			var s = intDiff;
			if(s < 9){
				s_str = 0+s.toString();
			}else{
				s_str = s;
			}
			$(".djs").html(s_str);
			intDiff--;
		}
	},1000)

}

function onError(evt) { 
    console.log(evt);
}

function closeSocket() {
    websocket.close();
}

function doSend(message) {
    websocket.send(message); 
}

function setInit(data){
	current_id = -1;
	setBotton();
	toyImg = data.result.toyRoomImg;
	toyNo = data.result.toyNo;
	toyNowCoin = data.result.toyNowCoin;
	toyName = data.result.toyName;
	toyRoomImg = data.result.toyRoomImg;
	luckyNum = data.result.userGameRoomLuckyNumB;
	var str = '<div style="text-align:center;font-size:50px;margin-top:5%;font-weight:bold">'+toyName+'</div>';
	for(var key in data.result.toyDesc){
		str +='<div style="text-align:center;margin-top:5%;"><img src="'+data.result.toyDesc[key]+'" /></div>';
	}
	$(".success-detail-div").html(str);
	$(".showOnceCoinCur").html(toyNowCoin+"/局");
	var weight = (luckyNum/100 *34);
	$(".luckyNum").css('width',weight+'%');
	$(".curPlayer").html("在线人数："+data.result.curPlayer);
	var userCoin = sessionStorage["toiletCatUserCoin"];
	$(".showCurCoin").html(userCoin);
	load();
}

function catchToy(){
	clearInterval(startDjsTimer);
	uId = getQueryString('userNo');
	rId = getQueryString('gameRoomNo');
	userName = sessionStorage["toiletCatUserName"];
	userImg = sessionStorage["toiletCatUserImg"];
	removebroadcast();
	if(current_id > -1){
		status = 1; 
	}else{
		status = 0;
	}
	var sign = "catch&"+uId+"&"+rId;
	sign = hex_md5(sign);
	var json = '{"type":"catch","uId":'+uId+',"rId":"'+rId+'","sign":"'+sign+'","status":"'+status+'","uName":"'+userName+'","tName":"'+toyName+'","userImg":"'+userImg+'"}';
	doSend(json);
}

function getInfo(){
	uId = getQueryString('userNo');
	rId = getQueryString('gameRoomNo');
	var json = '{"type":"init","uId":'+uId+',"rId":"'+rId+'"}';
	doSend(json);
}

function catchStatus(data){
	if(data.result.catch_result == 'catch success'){
		status = 1;
	}else{
		status = 0;
		luckyNum = luckyNum+data.result.addNum;
	}
	getprize();
}

function startGame(){
	var sign = 'start&'+uId+"&"+toyNowCoin+"&"+rId;
	sign = hex_md5(sign);
	var json = '{"type":"start","uId":'+uId+',"rId":"'+rId+'","coin":"'+toyNowCoin+'","sign":"'+sign+'"}';
	doSend(json);
}

function successList(data){
	var str = "";
	for(var key in data.result){
		str +='<div class="success-list-div">';
		str +='  <div class="success-list-img">';
		str +='    <img src="'+data.result[key].userImg+'" />';
		str +='  </div>';
		str +='  <div class="success-list-name">';
		str +='  <div>'+data.result[key].userName+'</div>';
		str +='  <div>'+data.result[key].catchTime+'</div>';
		str +='  </div>';
		str +='  <div style="clear:both"></div>';
		str +='  </div>';
	}
	$(".success-list").html(str);
}

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
var myStr; 
var successArr = [];
function broadcast(data){
	if(data.status == 'fail'){
		var top = data.top;
		catchId = data.catchId;
		if(data.isMe != 1){
			var str = '<div class="'+data.catchId+'_1" style="position:absolute;top:'+top+'%;background:white;opacity:0.5;width:100%;height:40%;"></div><div class="'+data.catchId+'"  style="position:absolute;left:100%;top:'+top+'%;font-size:50px;width:150%;height:40%;">'+data.str+'</div>';
			$(".broadcastFail").append(str);
			$("."+data.catchId).animate({"left":"-200%"},8000);
			setTimeout(removebroadcast,5000);
		}else{
			myStr = '<div class="'+data.catchId+'_1" style="position:absolute;top:'+top+'%;background:white;opacity:0.5;width:100%;height:40%;"></div><div class="'+data.catchId+'"  style="position:absolute;left:100%;top:'+top+'%;font-size:50px;width:150%;height:40%;">'+data.str+'</div>';
		}
	}else{
		if(data.isMe != 1){
			$(".broadcastSuccessWords").html(data.str);
			$(".broadcastSuccess").show();
			setTimeout(hidebroadcast,2000);
		}else{
			$(".broadcastSuccessWords").html(data.str);
		}
		var str = "";
		str +='<div class="success-list-div">';
		str +='  <div class="success-list-img">';
		str +='    <img src="'+data.userImg+'" />';
		str +='  </div>';
		str +='  <div class="success-list-name">';
		str +='  <div>'+data.userName+'</div>';
		str +='  <div>'+data.catchTime+'</div>';
		str +='  </div>';
		str +='  <div style="clear:both"></div>';
		str +='  </div>';
		str +=$(".success-list").html();
		$(".success-list").html(str);
	}
	
}
function removebroadcast(){
	$("."+catchId).remove();
	$("."+catchId+"_1").remove();
}

function hidebroadcast(){
	$(".broadcastSuccess").hide();
}
var startDjsTimer;
function startGameStatus(data){
	if(data.result.result == "success"){
		var startdjs = 30;
		setBotton();
		$("#startButton").hide();
		$("#moveButton").show();
		var coin = parseInt($(".showCurCoin").html());
		$(".showCurCoin").html(coin-toyNowCoin);
		$(".successPerson").hide();
		
		startDjsTimer = window.setInterval(function(){
			if(startdjs == 0){
				clearInterval(startDjsTimer);
				catchToy();
			}else{
				startdjs--;
				$("#startdjs").html(startdjs);
			}
		},1000)
	}else{
		chongzhi();
	}
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}