var auto, i=0, current_id = -1, can_big=false, can_small = true, current_status=0;
var change_width=0,change_height=0;
var finish_arr = [];

//停止移动操作
function stopMove(){
    clearInterval(auto);
    if(gift_position[0]['top'] > current_size['di']["top"]+current_size['di']["height"]/2){
        $(".grasp").css("z-index",90);
    }else if(gift_position[0]['top'] < current_size['di']["top"]+current_size['di']["height"]/2 && gift_position[3]['top'] > current_size['di']["top"]+current_size['di']["height"]/2){
        $(".grasp").css("z-index",91);
    }else{
        $(".grasp").css("z-index",93);
    }
}
//改变娃娃底部颜色 并记录当前娃娃选择娃娃id
function changeColor(){
    var position_x_min = current_size['di']['left'];
    var position_x_max = position_x_min + parseFloat(current_size['di']['width']);

    var position_y_min = current_size['di']['top'];
    var position_y_max = position_y_min + parseFloat(current_size['di']['height']);
    current_status = 0;
    for(var key in gift_position){
        if(
            position_x_min <= gift_position[key]['left'] &&
            position_x_max >= gift_position[key]['right'] &&
            position_y_min <= gift_position[key]['top'] &&
            position_y_max >= gift_position[key]['down'] &&
            $.inArray(key,finish_arr) == -1
        ){
            current_id = key;
            $("#wawaback"+key+" img").attr('src',"/image/game/di-ready.png");
        }else{
            current_status++;
            $("#wawaback"+key+" img").attr('src',"/image/game/wawadi.png");
        }
    }
    if(current_status == wawa_num){current_id = -1;}
}

function moveRight(){
    if(flag_status['move'] == true){
        stopMove();
        if(current_size['up']['left']+0.5 < max['up']['left']){
            auto = window.setInterval(function(){
                if(current_size['up']['left']>=max['up']['left']){
                    stopMove();
                }else{
                    for(var p = 0;p<2;p++){
                        changeColor();
                        current_size['up']['left'] = current_size['up']['left']+0.5;
                        current_size['left']['left'] = current_size['left']['left']+0.5;
                        current_size['right']['left'] = current_size['right']['left']+0.5;
                        current_size['di']['left'] = current_size['di']['left'] + 0.5;

                        obj['up'].css('left',current_size['up']['left']+"px");
                        obj['left'].css('left',current_size['left']['left']+"px");
                        obj['right'].css('left',current_size['right']['left']+"px");
                        obj['di'].css('left',current_size['di']['left']+"px");
                    }
                }
            },1)
        }
    }
}

function moveLeft(){
    if(flag_status['move'] == true){
        stopMove();
        if(current_size['up']['left']+0.5 >zhuazi_size['up']['left']){
            auto = window.setInterval(function(){
                if(current_size['up']['left']<=zhuazi_size['up']['left']){
                    stopMove();
                }else{
                    for(var p = 0;p<2;p++){
                        changeColor();
                        current_size['up']['left'] = current_size['up']['left'] - 0.5;
                        current_size['left']['left'] = current_size['left']['left'] - 0.5;
                        current_size['right']['left'] = current_size['right']['left'] - 0.5;
                        current_size['di']['left'] = current_size['di']['left'] - 0.5;

                        obj['up'].css('left',current_size['up']['left']+"px");
                        obj['left'].css('left',current_size['left']['left']+"px");
                        obj['right'].css('left',current_size['right']['left']+"px");
                        obj['di'].css('left',current_size['di']['left']+"px");
                    }
                }
            },1)
        }
    }
}
function moveBig(){
    if(flag_status['move'] == true){
        stopMove();
        var j = 0;
        can_small = true;
        if(can_big){
            auto = window.setInterval(function(){
                changeColor();
                if(i == 0){
                    can_big = false;
                    stopMove();
                }else{
                    i--;
                    j++
                    change_width = parseFloat(zhuazi_size['up']['width']) * 0.002;
                    change_height = parseFloat(zhuazi_size['up']['height']) * 0.002;

                    current_size['up']['width'] = parseFloat(current_size['up']['width']) + change_width +"px";
                    current_size['up']['height'] = parseFloat(current_size['up']['height']) + change_height +"px";
                    obj['up'].css({'width':current_size['up']['width'], 'height':current_size['up']['height']});

                    //大小
                    current_size['left']['width'] = parseFloat(current_size['left']['width']) + parseFloat(zhuazi_size['left']['width']) * 0.002 + "px";
                    current_size['left']['height'] = parseFloat(current_size['left']['height']) + parseFloat(zhuazi_size['left']['height']) * 0.002 + "px";
                    //位置
                    current_size['left']['left'] = parseFloat(current_size['left']['left']) - (change_width/2);
                    current_size['left']['top'] = parseFloat(current_size['left']['top']) + change_height;

                    obj['left'].css({
                        'width':current_size['left']['width'],
                        'height':current_size['left']['height'],
                        'left':current_size['left']['left'],
                        'top':current_size['left']['top']
                    });
                    //大小
                    current_size['right']['width'] = parseFloat(current_size['right']['width']) + parseFloat(zhuazi_size['right']['width']) * 0.002 +"px";
                    current_size['right']['height'] = parseFloat(current_size['right']['height']) + parseFloat(zhuazi_size['right']['height']) * 0.002 +"px";
                    //位置
                    current_size['right']['left'] = parseFloat(current_size['right']['left']) + change_width;
                    current_size['right']['top'] = parseFloat(current_size['right']['top']) + change_height;

                    obj['right'].css({
                        'width':current_size['right']['width'],
                        'height':current_size['right']['height'],
                        'left':current_size['right']['left'],
                        'top':current_size['right']['top']
                    });

                    //大小
                    current_size['di']['width'] = parseFloat(current_size['di']['width']) + parseFloat(zhuazi_size['di']['width']) * 0.002;
                    current_size['di']['height'] = parseFloat(current_size['di']['height']) + parseFloat(zhuazi_size['di']['height']) * 0.002;
                    //位置
                    current_size['di']['left'] = parseFloat(current_size['di']['left']) - change_width;
                    current_size['di']['top'] = parseFloat(current_size['di']['top']) + (di_cha/100);

                    obj['di'].css({
                        'width':current_size['di']['width'],
                        'height':current_size['di']['height'],
                        'left':current_size['di']['left'],
                        'top':current_size['di']['top']
                    });
                }
            },20)
        }
    }
}

function moveSmall(){
    if(flag_status['move'] == true){
        stopMove();
        can_big = true;
        var j=0;
        if(can_small){
            auto = window.setInterval(function(){
                changeColor();
                if(i == 100){
                    can_small = false;
                    clearInterval(auto);
                }else{
                    i++;
                    j++;
                    change_width = parseFloat(zhuazi_size['up']['width']) * 0.002;
                    change_height = parseFloat(zhuazi_size['up']['height']) * 0.002;

                    current_size['up']['width'] = parseFloat(current_size['up']['width']) - change_width +"px";
                    current_size['up']['height'] = parseFloat(current_size['up']['height']) - change_height +"px";
                    obj['up'].css({
                        'width':current_size['up']['width'],
                        'height':current_size['up']['height']
                    });
                    //大小
                    current_size['left']['width'] = parseFloat(current_size['left']['width']) - parseFloat(zhuazi_size['left']['width']) * 0.002;
                    current_size['left']['height'] = parseFloat(current_size['left']['height']) - parseFloat(zhuazi_size['left']['height']) * 0.002;
                    //位置
                    current_size['left']['left'] = parseFloat(current_size['left']['left']) + (change_width/2);
                    current_size['left']['top'] = parseFloat(current_size['left']['top']) - change_height;
                    obj['left'].css({
                        'width':current_size['left']['width'],
                        'height':current_size['left']['height'],
                        'left':current_size['left']['left'],
                        'top':current_size['left']['top']
                    });

                    //大小
                    current_size['right']['width'] = parseFloat(current_size['right']['width']) - parseFloat(zhuazi_size['right']['width']) * 0.002;
                    current_size['right']['height'] = parseFloat(current_size['right']['height']) - parseFloat(zhuazi_size['right']['height']) * 0.002;
                    //位置
                    current_size['right']['left'] = parseFloat(current_size['right']['left']) - change_width;
                    current_size['right']['top'] = parseFloat(current_size['right']['top']) - change_height;
                    obj['right'].css({
                        'width':current_size['right']['width'],
                        'height':current_size['right']['height'],
                        'left':current_size['right']['left'],
                        'top':current_size['right']['top']
                    });

                    //大小
                    current_size['di']['width'] = parseFloat(current_size['di']['width']) - parseFloat(zhuazi_size['di']['width']) * 0.002;
                    current_size['di']['height'] = parseFloat(current_size['di']['height']) - parseFloat(zhuazi_size['di']['height']) * 0.002;
                    //位置
                    current_size['di']['left'] = parseFloat(current_size['di']['left']) + change_width;
                    current_size['di']['top'] = parseFloat(current_size['di']['top']) -  (di_cha/100);
                    obj['di'].css({
                        'width':current_size['di']['width'],
                        'height':current_size['di']['height'],
                        'left':current_size['di']['left'],
                        'top':current_size['di']['top']
                    });
                }
            },20)
        }
    }
}
