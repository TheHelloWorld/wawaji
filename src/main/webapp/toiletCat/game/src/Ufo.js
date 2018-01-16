//飞碟和爪子
var selectToy = -1;
var catchResult = 0;
var catch_status = 0;
var centerX=0,centerY=0,diff=0,downY=0;
var sin,sin_right;
var upToyX = 0,upToyY = 0,upToyDiff = 0;
var resetx = resety = 0;
var resetarmleft = resetarmright = false;
var current_status = 0;
var ufo,armleft,armright,x = 1,ufoBounds,shadowBounds,canMoveStatus=1;

var Ufo = (function(_super){
    function Ufo(){
        Ufo.super(this);
        this.ufo = new Laya.Animation();
        ufo = this.ufo;
        //创建图集动画
        Laya.Animation.createFrames(["comp/ufo.png"],"flyStop");
        Laya.Animation.createFrames(["comp/ufo.png","comp/ufoleft1.png","comp/ufoleft2.png","comp/ufoleft3.png","comp/ufoleft4.png"],"flyLeft");
        Laya.Animation.createFrames(["comp/ufo.png","comp/uforight1.png","comp/uforight2.png","comp/uforight3.png","comp/uforight4.png"],"flyRight");
        Laya.Animation.createFrames(["comp/ufo.png","comp/up1.png","comp/up2.png","comp/up3.png"],"flySmall");
        Laya.Animation.createFrames(["comp/ufo.png","comp/down1.png","comp/down2.png","comp/down3.png"],"flybig");
        this.ufo.zOrder = 90;
        this.addChild(this.ufo);
        ufo.play(0,false,"flyStop");
        ufo.interval = 100;

        this.armleft = new Laya.Sprite();
        armleft = this.armleft;
        this.armleft.loadImage("comp/armleft.png");
        this.armleft.zOrder = 89;
        this.addChild(this.armleft);

        this.armright = new Laya.Sprite();
        armright = this.armright;
        this.armright.loadImage("comp/armright.png");

        this.armright.zOrder = 89;
        this.addChild(this.armright);

        //this.bound = this.getBounds();console.log(this.bound);
        this.ufo.pos(10,0);
        this.armleft.pivot(57,0);
        this.armleft.pos(57,70);
        this.armright.pos(155,70);
        ufoBounds = this.getBounds();


    }

    //注册
    Laya.class(Ufo, "Ufo", _super);
    Laya.class(UfoShadow, "UfoShadow", _super);

    //向左移动
    Laya.class(moveLeft, "moveLeft", _super);
    Laya.class(stopMoveLeft, "stopMoveLeft", _super);

    //向右移动
    Laya.class(moveRight, "moveRight", _super);
    Laya.class(stopMoveRight, "stopMoveRight", _super);

    Laya.class(moveBig, "moveBig", _super);
    Laya.class(stopMoveBig, "stopMoveBig", _super);

    Laya.class(moveSmall, "moveSmall", _super);
    Laya.class(stopMoveSmall, "stopMoveSmall", _super);
    Laya.class(catchToy, "catchToy", _super);

    var _proto = Ufo.prototype;

    function UfoShadow(){
        UfoShadow.super(this);
        this.di = new Laya.Sprite();
        shadow = this.di;
        this.di.loadImage("comp/di.png");
        this.addChild(this.di);
        shadowBounds = shadow.getBounds();
    }

    function moveLeft(){
        if(canMoveStatus == 1){
            ufo.play(0,false,"flyLeft");
            Laya.timer.frameLoop(1,this,onLoopLeft);
        }
    }

    function stopMoveLeft(){
        if(canMoveStatus == 1){
            ufo.play(0,false,"flyStop");
            Laya.timer.clear(this,onLoopLeft);
        }
    }

    function moveRight(){
        if(canMoveStatus == 1){
            ufo.play(0,false,"flyRight");
            Laya.timer.frameLoop(1,this,onLoopRight);
        }
    }

    function stopMoveRight(){
        if(canMoveStatus == 1){
            ufo.play(0,false,"flyStop");
            Laya.timer.clear(this,onLoopRight);
        }
    }

    function moveBig(){
        if(canMoveStatus == 1){
            ufo.play(0,false,"flybig");
            Laya.timer.frameLoop(1,this,onLoopBig);
        }
    }

    function stopMoveBig(){
        if(canMoveStatus == 1){
            ufo.play(0,false,"flyStop");
            Laya.timer.clear(this,onLoopBig);
        }
    }

    function moveSmall(){
        if(canMoveStatus == 1){
            ufo.play(0,false,"flySmall");
            Laya.timer.frameLoop(1,this,onLoopSmall);
        }
    }

    function stopMoveSmall(){
        if(canMoveStatus == 1){
            ufo.play(0,false,"flyStop");
            Laya.timer.clear(this,onLoopSmall);
        }
    }


    function onLoopLeft(){
        if(shadowBounds.x == 0){
            shadowBounds = shadow.getBounds();
        }
        if(ufoAll.x > (1.66*((1-x)/0.005))){
            ufoAll.x -=1.5;
            shadow.x -= 1.5;
            shadowBounds.x = shadow.x+100;
            checkHit();
        }
    }

    function onLoopRight(){
        if(shadowBounds.x == 0){
            shadowBounds = shadow.getBounds();
        }
        if((ufoAll.x + ufoBounds.width) < (595-(0.78*((1-x)/0.005)))){
            ufoAll.x +=1.5;
            shadow.x += 1.5;
            shadowBounds.x = shadow.x+100;
            checkHit();
        }
    }

    function onLoopSmall(){
        if(x >= 0.7){
            ufoBounds = ufoAll.getBounds();
            shadowBounds = shadow.getBounds();
            x -=0.005;
            if((ufoAll.x + ufoBounds.width) < (595-(0.78*((1-x)/0.005)))){
                ufoAll.x +=2*x;
                shadow.x +=2.1*x;
            }
            ufoAll.y +=0.5*x;
            ufoAll.scale(x, x);

            shadow.y +=1.7*x;
            shadow.scale(x, x);
            checkHit();
        }
    }
    function onLoopBig(){
        if(x < 1){
            ufoBounds = ufoAll.getBounds();
            shadowBounds = shadow.getBounds();
            x +=0.005;
            if(ufoAll.x > (1.66*((1-x)/0.005))){
                ufoAll.x -=2*x;
                shadow.x -=2.1*x;
            }
            ufoAll.y -=0.5*x;
            ufoAll.scale(x, x);

            shadow.y -=1.7*x;
            shadow.scale(x, x);
            checkHit();
        }
    }

    //开始抓取娃娃
    function catchToy(){
        if(centerY == 0 ){
            shadowBounds.y = shadowBounds.y == 0 ? 720:shadowBounds.y;
            centerY = shadowBounds.y+shadowBounds.height/2-10;
            ufoAll.zOrder = 91;
        }
        if(ufoBounds.y == 0){
            ufoBounds = ufoAll.getBounds();
        }
        if(selectToy == -1){
            diff = centerY-ufoBounds.y-ufoBounds.height;
            Laya.timer.frameLoop(1,this,downUfo);
        }else{
            diff = toyImgCreate[selectToy].y-ufoBounds.y-90;
            Laya.timer.frameLoop(1,this,downUfo);
        }

    }

    //ufo下降
    function downUfo(){
        if(selectToy == -1){
            if(downY <=diff){
                downY+=2;
                ufoAll.y+=2;
            }else{
                Laya.timer.clear(this,downUfo);
                Laya.timer.frameLoop(1,this,onLoopCatch);
            }
        }else{
            if(downY <=diff){
                downY+=2;
                ufoAll.y+=2;
            }else{
                Laya.timer.clear(this,downUfo);
                if(selectToy > -1){
                    var c =  Math.sqrt((Math.pow(parseFloat(57*ufoAll.scaleX),2) + Math.pow(parseFloat(120*ufoAll.scaleY),2)));
                    sin = 360 * (1 - Math.cos(((toyImgCreate[selectToy].x-ufoAll.x-80)/c)))/2 +5;
                    if(ufoAll.x+80 - toyImgCreate[selectToy].x > 0){
                        sin = 2;
                    }
                    var gift_right =parseFloat(toyImgCreate[selectToy].x) + parseFloat(toySize[0]);
                    sin_right = 360 * (1 - Math.cos(((gift_right-ufoAll.x-80-ufoBounds.width)/c)))/2+5;
                    if(gift_right-ufoAll.x-80-ufoBounds.width > 0 ){
                        sin_right = 2;
                    }
                }
                Laya.timer.frameLoop(1,this,onLoopCatch);
            }
        }
    }

    //爪子合并
    function onLoopCatch(){
        if(selectToy == -1){
            if(armright.rotation < 40){
                armleft.rotation -=1;
                armright.rotation +=1;
            }else{
                Laya.timer.clear(this,onLoopCatch);
                Laya.timer.frameLoop(1,this,upUfo);
            }
        }else{
            if(armleft.rotation > sin*-1){
                armleft.rotation -=1;
            }
            if(armright.rotation < sin_right){
                armright.rotation +=1;
            }
            if(armleft.rotation <= sin && armright.rotation >= sin_right){
                Laya.timer.clear(this,onLoopCatch);
                upToyY = parseInt((Math.random()*1000));
                if(upToyY<100){
                    upToyY += 100;
                }
                if(upToyY > downY){
                    upToyY = upToyY % downY;
                }

                Laya.timer.frameLoop(1,this,upUfo);
                toyImgCreate[selectToy].zOrder = ufoAll.zOrder+2;
                toyShadowCurPos[selectToy]['obj'].visible = false;
                if(catch_status == 0){
                    Laya.timer.frameLoop(1,this,upToy);
                }

            }
        }
    }

    //ufo上升
    function upUfo(){
        if(downY > 0){
            downY-=2;
            ufoAll.y-=2;
        }else{
            Laya.timer.clear(this,upUfo);
            Laya.timer.frameLoop(1,this,initPosition);
        }
        if(selectToy > -1){
            if(catch_status == 1){
                if(selectToy <= 1){
                    var need_j = 30;
                }else{
                    var need_j = 0;
                }
                toyImgCreate[selectToy].y -=2;
                var ufox = (ufoAll.x + ufoBounds.width/2-need_j);
                if(armleft.rotation > -40){
                    armleft.rotation -=2;
                }
                if(armright.rotation < 40){
                    armright.rotation +=2;
                }
                if(toyImgCreate[selectToy].x < ufox+2){
                    upToyX +=2;
                    toyImgCreate[selectToy].x += 2;
                }else if(toyImgCreate[selectToy].x > ufox){
                    upToyX -=2;
                    toyImgCreate[selectToy].x -= 2;
                }
            }
        }
    }

    //抓取失败 娃娃上升
    function upToy(){
        if(selectToy <= 1){
            var need_j = 30;
        }else{
            var need_j = 0;
        }
        var ufox = ufoAll.x + ufoBounds.width/2-need_j;
        if(armleft.rotation > -40){
            armleft.rotation -=2;
        }
        if(armright.rotation < 40){
            armright.rotation +=2;
        }
        if(toyImgCreate[selectToy].x < ufox+2){
            upToyX +=2;
            toyImgCreate[selectToy].x += 2;
        }else if(toyImgCreate[selectToy].x > ufox){
            upToyX -=2;
            toyImgCreate[selectToy].x -= 2;
        }
        if(upToyDiff < upToyY ){
            upToyDiff += 2;
            toyImgCreate[selectToy].y -= 2;
        }else{
            Laya.timer.clear(this,upToy);
            toyImgCreate[selectToy].zOrder -=2;
            Laya.timer.frameLoop(1,this,downToy);
            toyShadowCurPos[selectToy]['obj'].visible = true;
            toyShadowCurPos[selectToy]['obj'].play(0,false,'shadowStop');
        }
    }

    //抓取失败娃娃掉落
    function downToy(){
        if(upToyDiff > 0){
            upToyDiff -=8;
            toyImgCreate[selectToy].y += 8;
        }

        if(upToyX > 0 ){
            upToyX -=2;
            toyImgCreate[selectToy].x -= 2;
        }else if(upToyX < 0){
            upToyX +=2;
            toyImgCreate[selectToy].x += 2;
        }
        if(upToyX == 0 && upToyDiff <= 0){
            Laya.timer.clear(this,downToy);
        }
    }

    //回到起始位置
    function initPosition(){
        if(x < 1){
            x +=0.005;
            if(ufoAll.x > (1.66*((1-x)/0.005))){
                ufoAll.x -=2*x;
                shadow.x -=2.1*x;
                if(catch_status == 1){
                    toyImgCreate[selectToy].x -=0.6;
                    toyImgCreate[selectToy].y +=1;
                }
            }
            ufoAll.y -=0.5*x;
            ufoAll.scale(x, x);

            shadow.y -=1.7*x;
            shadow.scale(x, x);
        }else{
            resety = 1;
        }

        if(ufoAll.x>0 && resety == 1 ){
            ufoAll.x-=1.5;
            shadow.x -= 1.5;
            if(catch_status == 1 && selectToy > -1){
                toyImgCreate[selectToy].x -= 1.5;
            }
        }else if(ufoAll.x <= 0 && resety == 1){
            resetx = 1;
        }

        if(resetx == 1  && resety == 1){
            Laya.timer.clear(this,initPosition);
            Laya.timer.frameLoop(1,this,initArm);
            if(catch_status == 1 && selectToy > -1){
                toyImgCreate[selectToy].zOrder = 91;
                Laya.timer.frameLoop(1,this,successCatchToy);
            }
        }
    }

    //爪子恢复初始状态
    function initArm(){
        if(armleft.rotation < 0){
            armleft.rotation +=1;
        }else{
            armleft.rotation = 0;
            resetarmleft = true;
        }

        if(armright.rotation > 0){
            armright.rotation -=1;
        }else{
            armright.rotation = 0;
            resetarmright = true;
        }

        if(resetarmright && resetarmleft){
            resetGameButton();
            luckyNumChange();
            Laya.timer.clear(this,initArm);
        }
    }
    var addinity = 0;
    var djs_time = 10;
    function luckyNumChange(){
        if(catch_status == 1){
            luckyNumPNG.x = 30;
            luckyNumPNG.xscale = (0,1);
            zzcObj.visible = true;
            successInfoObj.visible = true;
            timepng.visible = false;
            curnum.visible = true;
            curNumObj.visible = true;
            singlePlayFonttxt.visible = true;
            timePngTxtObj.visible = false;
            canPlayMyBroadcast = 1;
            successList.push(selectToy);

        }else{
            var luckynumxlen = luckyNum/100 > 1?1:luckyNum/100;
            luckyNumPNG.x = (30-(luckynumxlen)*20);
            luckyNumPNG.scale(luckynumxlen,1);
            addLuckyNumFontObj.text = "+"+addLuckyNum;
            addLuckyNumFont.visible = true;
            addinity = 0;
            zzcObj.visible = true;
            failInfoObj.visible = true;
            timepng.visible = false;
            curnum.visible = true;
            curNumObj.visible = true;
            singlePlayFonttxt.visible = true;
            timePngTxtObj.visible = false;
            djs_time = 10;
            Laya.timer.loop(1000,this,createdjs);
            Laya.timer.frameLoop(1,this,onLoopLuckyNumChange);
            Laya.timer.frameLoop(1,myBroadcast,myBroadcastLoop,[],false);
            canPlayMyBroadcast = 1;
        }
    }

    function createdjs(){
        if(djs_time == 0){
            Laya.timer.clear(this,createdjs);
            closeFailInfo();
        }else{
            djs_time--;
            againtxt.text = "再来一次（"+djs_time+"）";
        }
    }

    function onLoopLuckyNumChange(){
        if(addinity < 50){
            addinity += 1;
            addLuckyNumFont.y -=2
        }else{
            addLuckyNumFont.y += 100;
            addLuckyNumFont.visible = false;
            Laya.timer.clear(this,onLoopLuckyNumChange);
        }
    }

    //成功抓取 娃娃掉落
    function successCatchToy(){
        if(armright.rotation < 30){
            if(toyImgCreate[selectToy].y<800){
                toyImgCreate[selectToy].y +=8;
            }else{
                Laya.timer.clear(this,successCatchToy);
            }
        }
    }
    var successList = [];
    //碰撞检查
    function checkHit(){
        current_status = 0;
        shadowBounds.y = shadowBounds.y == 0 ? 720:shadowBounds.y;
        centerX = shadowBounds.x+shadowBounds.width/2;
        centerY = shadowBounds.y+shadowBounds.height/2-10;
        if(centerY < toyShadowCurPos[0]['y'] ){
            ufoAll.zOrder=toyinfo_position[0][2]-1;
        }else if(centerY > toyShadowCurPos[0]['y']+toyShadowCurPos[0]['height'] && centerY < toyShadowCurPos[2]['y']){
            ufoAll.zOrder=toyinfo_position[2][2]-1;
        }
        current_status = 0;
        for(var i = 0;i<4;i++){
            if(toyShadowCurPos[i]['x'] <= centerX && (toyShadowCurPos[i]['x']+toyShadowCurPos[i]['width']) >= centerX){
                if(toyShadowCurPos[i]['y'] <= centerY && (toyShadowCurPos[i]['y']+toyShadowCurPos[i]['height']) >= centerY){
                    if($.inArray(i,successList) == -1){
                        selectToy = i;
                        toyShadowCurPos[i]['obj'].play(0,false,'shadowSelect');
                    }else{
                        current_status++;
                        toyShadowCurPos[i]['obj'].play(0,false,'shadowStop');
                    }
                }else{
                    current_status++;
                    toyShadowCurPos[i]['obj'].play(0,false,'shadowStop');
                }
            }else{
                current_status++;
                toyShadowCurPos[i]['obj'].play(0,false,'shadowStop');
            }
        }
        if(current_status == 4){selectToy = -1;}
    }

    return Ufo;
})(Laya.Sprite);