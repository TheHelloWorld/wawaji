var angle = 0;
var zhuaqu;
var zhua_up_top = 0;
var beforeGetPosition = {"left":0};
	beforeGetPosition['right'] = 0;
	beforeGetPosition['up'] = 0;
var status = 1;
var liwu_move = {'position':{'top':0}};
var can_up = {'left':false};
	can_up['right'] = false;
var angle
function getprize(){
	if(flag_status['get'] == true){
		$(".button-top").attr({"ontouchstart":"", "ontouchend":""});
		$(".button-left").attr({"ontouchstart":"", "ontouchend":""});
		$(".button-bottom").attr({"ontouchstart":"", "ontouchend":""});
		$(".button-right").attr({"ontouchstart":"", "ontouchend":""});
		$(".button-get").attr({"ontouchstart":""});
		flag_status['move'] = false;
		flag_status['get'] = false;
		if(current_id > -1){$(".grasp").css("z-index",wawa_init_position[wawa_num][2][current_id]);
			var top = parseFloat($("#liwu"+current_id).css('top'));
			var zhua_up = current_size['up']['top'];
			var zhua_left = current_size['left']['top'];
			var zhua_right = current_size['right']['top'];
			var height = parseFloat(current_size['up']['height']);
			var zhua_j = zhua_up+height;
			var down_height = top - zhua_j + 20;
			var y = 0.00;
			zhua_up_top = current_size['up']['top'] + parseFloat(current_size['up']['height']);
			var g_p = setInterval(function(){
				if(y >= down_height){
					clearInterval(g_p);
					beforeGetPosition['y'] = y
					beforeGetPosition['up'] = up;
					beforeGetPosition['left'] = left;
					beforeGetPosition['right'] = right;
					mergePaw();
				}else{
					y++;
					up = zhua_up + y;
					left = zhua_left + y;
					right = zhua_right + y;
					obj['up'].css('top',up+"px");
					obj['left'].css('top',left+"px");
					obj['right'].css('top',right+"px");
				}
			},1);
		}else{
			$(".grasp").css("z-index",91);
			var down_height = current_size['di']['top']-parseFloat(current_size['left']['height'])/2;
			var y = 0;
			var zhua_up = current_size['up']['top'];
			var zhua_left = current_size['left']['top'];
			var zhua_right = current_size['right']['top'];
			var g_p = setInterval(function(){
				if(zhua_left >= down_height){
					clearInterval(g_p);
					beforeGetPosition['y'] = y
					beforeGetPosition['up'] = zhua_up;
					beforeGetPosition['left'] = zhua_left;
					beforeGetPosition['right'] = zhua_right;
					mergePaw();
				}else{
					y++;
					zhua_up++;
					zhua_left++;
					zhua_right++;
					obj['up'].css('top',zhua_up+"px");
					obj['left'].css('top',zhua_left+"px");
					obj['right'].css('top',zhua_right+"px");
				}
			},1);
		}
	}
	
}

function mergePaw(){
	if(current_id > -1){
		var left = current_size['left']['left'];
		var gift_left = parseFloat($("#liwu"+current_id).css("left"));
		var c =  Math.sqrt((Math.pow(parseFloat(current_size['left']['width']),2) + Math.pow(parseFloat(current_size['left']['height']),2)));
		var sin = 360 * (1 - Math.cos(((gift_left-left)/c)))/2 +5;
		var angle = 0;
		var move_right = move_left = -1;
		if(left-gift_left > 0){
			sin = 5;
			move_left = 0;
		}
		zhuaqu = setInterval(function(){
			if(angle > sin){
				clearInterval(zhuaqu);
				can_up['left_angle'] = sin;
				can_up['left'] = true;
				gift_get_up();
				zhuazi_get_up();
				zhuazi_get_up_angle();
				
			}else{
				angle = angle+0.3;
				obj['left'].css("-webkit-transform",'rotate(-'+angle+'deg)');
			}
		},10)
		
		var right = current_size['right']['left'] + parseFloat(current_size['right']['width']);
		var gift_right =parseFloat($("#liwu"+current_id).css("left")) + parseFloat(wawa_init_size[wawa_num][0]);
		var c_right =  Math.sqrt((Math.pow(parseFloat(current_size['right']['width']),2) + Math.pow(parseFloat(current_size['right']['height']),2)));
		var sin_right = 360 * (1 - Math.cos(((gift_right-right)/c_right)))+5;
		var angle_right = 0;
		if(gift_right-right > 0){
			sin_right = 5;
			move_right = 0;
		}
		if( move_right ==  move_left ){
			liwu_move['move'] = {'up':0};
		}else if(move_right == -1){
			liwu_move['move'] = {'up':left-gift_left};
		}else{
			liwu_move['move'] = {'up':right-gift_right};
		}
		
		zhuaqu_right = setInterval(function(){
			if(angle_right > sin_right){
				clearInterval(zhuaqu_right);
				can_up['right_angle'] = sin_right;
				can_up['right'] = true;
				gift_get_up();
				zhuazi_get_up();
				zhuazi_get_up_angle();
			}else{
				
				angle_right = angle_right+0.3;
				obj['right'].css("-webkit-transform",'rotate('+angle_right+'deg)');
			}
		},1)
	}else{
		var angle = 0;
		zhuaqu = setInterval(function(){
			if(angle > 45){
				clearInterval(zhuaqu);
				can_up['right_angle'] = 45;
				can_up['left_angle'] = 45;
				can_up['right'] = true;
				can_up['left'] = true;
				zhuazi_get_up();
			}else{
				angle = angle+0.3;
				obj['right'].css("-webkit-transform",'rotate('+angle+'deg)');
				obj['left'].css("-webkit-transform",'rotate(-'+angle+'deg)');
			}
		},1)
	}
}

function gift_get_up(){
	if(can_up['left'] == true && can_up['right'] == true && current_id > -1){
		if(status == 0){
			liwu_move['position']['top'] = parseFloat($("#liwu"+current_id).css('top'));
			liwu_move['position']['left'] = parseFloat($("#liwu"+current_id).css('left'));
			var rand = Math.random();
			var base = parseInt(Math.random()*10);
			rand = ((100 * rand)*base).toFixed(2);
			if(rand > liwu_move['position']['top']){
				rand = liwu_move['position']['top'] - zhua_up_top;
			}
			if(rand < 50){rand = 20;}
			if(rand > 600){rand = 600;}
			var x = 0;
			var up = liwu_move['position']['top'];
			var up_left = 0;
			var li_up = setInterval(function(){
				if(x >= rand){
					clearInterval(li_up);
					down();
				}else{
					x = x+2;
					up = up - 2;
					$("#liwu"+current_id).css('top',up);
					if(liwu_move['move']['up'] > 0){
						up_left += 0.5;
						if(up_left < liwu_move['move']['up']){
							$("#liwu"+current_id).css('left',liwu_move['position']['left']+up_left);
						}
					}else{
						up_left-=0.5
						if(up_left > liwu_move['move']['up']){
							$("#liwu"+current_id).css('left',liwu_move['position']['left']+up_left);
						}
					}
				}
			},1);
		}else{
			liwu_move['position']['top'] = parseFloat($("#liwu"+current_id).css('top'));
			liwu_move['position']['left'] = parseFloat($("#liwu"+current_id).css('left'));
			var rand = zhua_up_top;
			var up = liwu_move['position']['top'];
			var up_left = 0;
			var li_up = setInterval(function(){
				if(up <= rand){
					clearInterval(li_up);
				}else{
					up = up - 2;
					$("#liwu"+current_id).css('top',up);
					if(liwu_move['move']['up'] > 0){
						up_left += 0.5;
						if(up_left < liwu_move['move']['up']){
							$("#liwu"+current_id).css('left',liwu_move['position']['left']+up_left);
						}
					}else{
						up_left-=0.5
						if(up_left > liwu_move['move']['up']){
							$("#liwu"+current_id).css('left',liwu_move['position']['left']+up_left);
						}
					}
				}
			},1);
		}
	}
}

function zhuazi_get_up(){
	if(can_up['left'] == true && can_up['right'] == true){
		var x = 0;
		var z_g_u = setInterval(function(){
			if(x < -beforeGetPosition['y']){
				clearInterval(z_g_u);
				obj['up'].animate({
					"left":zhuazi_size['up']['left']+"px",
					"top":zhuazi_size['up']['top']+"px",
					"width":zhuazi_size['up']['width'],
					"height":zhuazi_size['up']['height'],
				},1000);
				obj['left'].animate({
					"left":zhuazi_size['left']['left']+"px",
					"top":zhuazi_size['left']['top']+"px",
					"width":zhuazi_size['left']['width'],
					"height":zhuazi_size['left']['height']
				},1000);
				obj['right'].animate({
					"left":zhuazi_size['right']['left']+"px",
					"top":zhuazi_size['right']['top']+"px",
					"width":zhuazi_size['right']['width'],
					"height":zhuazi_size['right']['height']
				},1000);
				
				obj['di'].animate({
					"left":zhuazi_size['di']['left']+"px",
					"top":zhuazi_size['di']['top']+"px",
					"width":zhuazi_size['di']['width'],
					"height":zhuazi_size['di']['height']
				},1000);
				
				if(status == 1 && current_id > -1){
					$('#wawaback'+current_id).html("");
					var top = parseFloat($('#liwu'+current_id).css('top'))+change_height;
					$('#liwu'+current_id).animate({
						"left":(change_width+zhuazi_size['left']['left'])+"px",
						"top":top+"px"
					},1000);
				}
				setTimeout(put_down_angle,1400);
			}else{
				x = x - 2;
				obj['up'].css('top',(beforeGetPosition['up']+x)+"px");
				obj['left'].css('top',(beforeGetPosition['left']+x)+"px");
				obj['right'].css('top',(beforeGetPosition['right']+x)+"px");
			}
		},1)
	}
}

function zhuazi_get_up_angle(){
	if(can_up['left'] == true && can_up['right'] == true){
		var angle = (can_up['left_angle']+can_up['right_angle'])/2;
		var left_cha = Math.abs(can_up['left_angle']-angle);
		var right_cha = Math.abs(can_up['right_angle']-angle);
		angle = right_cha;
		if(left_cha > right_cha){angle = left_cha;}
		var x = 0;
		var turn_left;
		var turn_right;
		if( can_up['left_angle'] != can_up['right_angle']){
			var z_g_u_a = setInterval(function(){
				if(x > angle){
					clearInterval(z_g_u_a);
					can_up['left_angle'] = turn_left;
					can_up['right_angle'] = turn_right;
				}else{
					x++;
					if(can_up['left_angle'] > can_up['right_angle']){
						turn_left = can_up['left_angle']-x;
						turn_right = can_up['right_angle']+x;
						$('.grasp-left').css("-webkit-transform",'rotate(-'+turn_left+'deg)');
						$('.grasp-right').css("-webkit-transform",'rotate('+turn_right+'deg)');
					}else{
						turn_left = can_up['left_angle']+x;
						turn_right = can_up['right_angle']-x;
						$('.grasp-left').css("-webkit-transform",'rotate(-'+turn_left+'deg)');
						$('.grasp-right').css("-webkit-transform",'rotate('+turn_right+'deg)');
					}
				}
			},1)
		}
	}
}

function down(){
	var cur = parseFloat($("#liwu"+current_id).css('top'));
	$("#wawaback"+current_id+" img").attr('src',"image1/di.png");
	var gift_down_left = parseFloat($("#liwu"+current_id).css('left'));
	$("#liwu"+current_id).animate({
		'top':liwu_move['position']['top'],
		'left':liwu_move['position']['left'],
	},500);
	over_angle();
}

function over_angle(){
	var x = 0;
	var turn_left = 0;
	var turn_right = 0;
	var o_a = setInterval(function(){
		if(turn_left > 45){
			clearInterval(o_a);
		}else{
			x++;
			turn_left = can_up['left_angle']+x;
			turn_right = can_up['right_angle']+x;
			$('.grasp-left').css("-webkit-transform",'rotate(-'+turn_left+'deg)');
			$('.grasp-right').css("-webkit-transform",'rotate('+turn_right+'deg)');
		}
	},1)
}

function put_down_angle(){
	var x = 45;
	var y = 0;
	i=0
	flag_status['move'] = true;
	can_up['right'] = false;
	can_up['left'] = false;
	flag_status['get'] = true;
	if(current_id > -1 && status == 1){
		finish_arr.push(current_id);
	}
	current_size['up']['left'] = wc*0.17;
	current_size['left']['left'] = wc*0.16;
	current_size['right']['left'] = wc*0.33;
	//获取初始top值
	current_size['up']['top'] = parseFloat(obj['up'].css('top'));
	current_size['left']['top'] = parseFloat(obj['left'].css('top'));
	current_size['right']['top'] = parseFloat(obj['right'].css('top'));
	current_size['di']['top'] = hc*0.61;//605
	current_size['di']['left'] = wc*0.17;//125
	
	current_size['up']['width'] = $(".grasp-up").css('width');
	current_size['up']['height'] = $(".grasp-up").css('height');
	
	current_size['left']['width'] = $(".grasp-left").css('width');
	current_size['left']['height'] = $(".grasp-left").css('height');
	
	current_size['right']['width'] = $(".grasp-right").css('width');
	current_size['right']['height'] = $(".grasp-right").css('height');
	current_size['di']['width'] = (wc * 0.2)+"px";
	current_size['di']['height'] = obj['di'].css('height');
	
	var p_d_a = setInterval(function(){
		if(can_up['left_angle'] <= 0 && can_up['right_angle'] <= 0){
			i = 0;
			clearInterval(p_d_a);
		}else{
			x--;
			if(can_up['left_angle'] > 0){
				can_up['left_angle']--;
			}else{can_up['left_angle'] = 0;}
			if(can_up['right_angle'] > 0){
				can_up['right_angle']--;
			}else{can_up['right_angle'] = 0;}
			obj['left'].css("-webkit-transform",'rotate(-'+can_up['left_angle']+'deg)');
			obj['right'].css("-webkit-transform",'rotate('+can_up['right_angle']+'deg)');
		}
	},1)
	if(status == 1 && current_id > -1){
		success_get_gift(current_id);
	}else{
		var weight = (luckyNum/100 *34);
		$(".luckyNum").css('width',weight+'%');
		$(".resultMsg").show();
		$("#faildiv").show();
		$(".broadcastFail").append(myStr);
		$("."+data.catchId).animate({"left":"-200%"},8000);
		djsclose();
		setTimeout(removebroadcast,5000);
	}
}
var djsTimer;
function djsclose(){
	var djsTime = 10;
	djsTimer = window.setInterval(function(){
		if(djsTime == 0){
			clearInterval(djsTimer);
			closeLastDiv('failClose');
		}else{
			djsTime--;
			$("#djs").html(djsTime);
		}
	},1000)
}

function success_get_gift(){
	$("#liwu"+current_id).css('z-index',51)
	$("#liwu"+current_id).animate({
		"top":current_size['di']['top']
	},600);
	setTimeout(afterSuccess,700);
}

function afterSuccess(){
	$(".broadcastSuccess").show();
	setTimeout(hidebroadcast,2000);
	$("#liwu"+current_id).html("");
	$(".luckyNum").css('width','0%');
	$("#successImg img").attr('src',toyRoomImg);
	$("#successName").html('恭喜您获得：'+toyName);
	$(".resultMsg").show();
	$("#successdiv").show();
	current_id = -1;
}

function closeLastDiv(type){
	clearInterval(djsTimer);
	if(type == 'failClose'){
		$(".resultMsg").hide();
		$("#faildiv").hide();
		$("#startButton").show();
		$("#moveButton").hide();
		$(".successPerson").show();
	}else if(type == 'again'){
		$(".resultMsg").hide();
		$("#faildiv").hide();
		$("#startButton").show();
		$("#moveButton").hide();
		startGame();
	}else if('successClose'){
		$(".resultMsg").hide();
		$("#successdiv").hide();
		$("#startButton").show();
		$("#moveButton").hide();
		$(".successPerson").show();
	}
}