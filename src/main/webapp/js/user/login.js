// 在全局定义验证码
var code;

// 当前方式
var checkType = "";

var wait=60;

var type = "";

var from = "";

// 图片验证码
var verifyCode;

!(function(window, document) {
    var size = 5;//设置验证码长度
    function GVerify(options) { //创建一个图形验证码对象，接收options对象为参数
        this.options = { //默认options参数值
            id: "", //容器Id
            canvasId: "verifyCanvas", //canvas的ID
            width: "100", //默认canvas宽度
            height: "30", //默认canvas高度
            type: "blend", //图形验证码默认类型blend:数字字母混合类型、number:纯数字、letter:纯字母
            code: "",
        }
        if(Object.prototype.toString.call(options) == "[object Object]"){//判断传入参数类型
            for(var i in options) { //根据传入的参数，修改默认参数值
                this.options[i] = options[i];
            }
        }else{
            this.options.id = options;
        }

        this.options.numArr = "0,1,2,3,4,5,6,7,8,9".split(",");
        this.options.letterArr = getAllLetter();

        this._init();
        this.refresh();
    }

    GVerify.prototype = {
        /**版本号**/
        version: '1.0.0',

        /**初始化方法**/
        _init: function() {
            var con = document.getElementById(this.options.id);
            var canvas = document.createElement("canvas");
            this.options.width = con.offsetWidth > 0 ? con.offsetWidth : "100";
            this.options.height = con.offsetHeight > 0 ? con.offsetHeight : "30";
            canvas.id = this.options.canvasId;
            canvas.width = this.options.width;
            canvas.height = this.options.height;
            canvas.style.cursor = "pointer";
            canvas.innerHTML = "您的浏览器版本不支持canvas";
            con.appendChild(canvas);
            var parent = this;
            canvas.onclick = function(){
                parent.refresh();
            }
        },

        /**生成验证码**/
        refresh: function() {
            this.options.code = "";
            var canvas = document.getElementById(this.options.canvasId);
            if(canvas.getContext) {
                var ctx = canvas.getContext('2d');
            }else{
                return;
            }

            ctx.textBaseline = "middle";

            ctx.fillStyle = randomColor(180, 240);
            ctx.fillRect(0, 0, this.options.width, this.options.height);

            if(this.options.type == "blend") { //判断验证码类型
                var txtArr = this.options.numArr.concat(this.options.letterArr);
            } else if(this.options.type == "number") {
                var txtArr = this.options.numArr;
            } else {
                var txtArr = this.options.letterArr;
            }

            for(var i = 1; i <=size; i++) {
                var txt = txtArr[randomNum(0, txtArr.length)];
                this.options.code += txt;
                ctx.font = randomNum(this.options.height/2, this.options.height) + 'px SimHei'; //随机生成字体大小
                ctx.fillStyle = randomColor(50, 160); //随机生成字体颜色
                ctx.shadowOffsetX = randomNum(-3, 3);
                ctx.shadowOffsetY = randomNum(-3, 3);
                ctx.shadowBlur = randomNum(-3, 3);
                ctx.shadowColor = "rgba(0, 0, 0, 0.3)";
                var x = this.options.width / (size+1) * i;
                var y = this.options.height / 2;
                var deg = randomNum(-30, 30);
                /**设置旋转角度和坐标原点**/
                ctx.translate(x, y);
                ctx.rotate(deg * Math.PI / 180);
                ctx.fillText(txt, 0, 0);
                /**恢复旋转角度和坐标原点**/
                ctx.rotate(-deg * Math.PI / 180);
                ctx.translate(-x, -y);
            }
            /**绘制干扰线**/
            for(var i = 0; i < 4; i++) {
                ctx.strokeStyle = randomColor(40, 180);
                ctx.beginPath();
                ctx.moveTo(randomNum(0, this.options.width), randomNum(0, this.options.height));
                ctx.lineTo(randomNum(0, this.options.width), randomNum(0, this.options.height));
                ctx.stroke();
            }
            /**绘制干扰点**/
            for(var i = 0; i < this.options.width/4; i++) {
                ctx.fillStyle = randomColor(0, 255);
                ctx.beginPath();
                ctx.arc(randomNum(0, this.options.width), randomNum(0, this.options.height), 1, 0, 2 * Math.PI);
                ctx.fill();
            }
        },

        /**验证验证码**/
        validate: function(code){
            var code = code.toLowerCase();
            var v_code = this.options.code.toLowerCase();
            if(code == v_code){
                return true;
            }else{
                this.refresh();
                return false;
            }
        }
    };
    /**生成字母数组**/
    function getAllLetter() {
        var letterStr = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
        return letterStr.split(",");
    }
    /**生成一个随机数**/
    function randomNum(min, max) {
        return Math.floor(Math.random() * (max - min) + min);
    }
    /**生成一个随机色**/
    function randomColor(min, max) {
        var r = randomNum(min, max);
        var g = randomNum(min, max);
        var b = randomNum(min, max);
        return "rgb(" + r + "," + g + "," + b + ")";
    }
    window.GVerify = GVerify;
})(window, document);

$(function() {

    from = getQueryString("from");

    type = getQueryString("type");

    checkType = getQueryString("checkType");

    var hc = $(window).height();

    var wc = $(window).width();

    $(".background-div").css(
        'height',hc
    );

    $(".login-mobile").css(
        'width',wc*0.5
    );

    $(".login-check-code").css(
        'width',wc*0.3
    );

    $(".login-code").css(
        'width',wc*0.3
    );

    $(".login-get-code").css(
        'width',wc*0.3
    );

    $(".login").css(
        'width',wc*0.4
    );

    $(".safe-img").css(
        'width',wc*0.2
    );

    $(".logo").css(
        'width',wc*0.6
    );

    if(checkType == "checkText") {
        $("#checkCodeDiv").hide();
        $("#checkTextDiv").show();
    } else {

        // 产生验证码
        verifyCode = new GVerify("v_container");
        $("#checkTextDiv").hide();

    }
});

// 点击登陆按钮方法
function login() {

    if(!isNotNull("mobile")) {
        toiletCatMsg("手机号不能为空", null);
        return;
    }

    if(!checkMobileNo("mobile")) {
        toiletCatMsg("请填入正确的手机号", null);
        return;
    }

    if(checkType == "checkText") {
        loginOrRegister();
    } else {
        validate();
    }
}

// 校验验证码
function validate() {
    // 若输入的验证码为空
    if(!isNotNull("checkCodeText")) {
        toiletCatMsg("请输入验证码", null);
        return;
    }

    // 获取输入框内验证码
    var inputCode = $("#checkCodeText").val().replace(/\s+/g, "");

    var res = verifyCode.validate(inputCode);

    // 若输入的验证码与产生的验证码不一致时
    if(!res) {
        // 则弹出验证码输入错误
        toiletCatMsg("验证码输入错误", null);
        // 刷新验证码
        verifyCode.refresh();
        // 清空文本框
        $("#checkCodeText").val("");
        return;
    }

    checkType = "checkText";

    var url = window.location.href;

    url = url.split("checkType=")[0] + "checkType=" + checkType;

    window.history.pushState({},0,url);

    $("#checkCodeDiv").hide();
    $("#checkTextDiv").show();

}

// 发送短信验证码
function sendTextCode(o) {

    if(!isNotNull("mobile")) {
        toiletCatMsg("手机号不能为空", null);
        return;
    }

    if(!checkMobileNo("mobile")) {
        toiletCatMsg("请填入正确的手机号", null);
        return;
    }

    sendTextWaitTime(o);

    sendMobileText();
}

// 按钮倒计时
function sendTextWaitTime(o) {

    o.setAttribute("disabled", true);
    o.innerText="重新发送(" + wait + ")";

    if (wait == 0) {
        o.removeAttribute("disabled");
        o.innerText="获取短信验证码";
        wait = 60;
    } else {

        o.setAttribute("disabled", true);
        o.innerText="重新发送(" + wait + ")";
        wait--;
        setTimeout(
            function() {
                sendTextWaitTime(o)
            }, 1000);
    }
}

// 发送短信验证码
function sendMobileText() {

    $.ajax({
        url:"/toiletCat/api/user/sendMobileVerificationCode.action",
        type:"POST",
        data:{
            mobileNo:$("#mobile").val()
        },
        success:function(data){

            // 转换数据
            if(typeof(data) == "string") {
                data = eval("("+data+")");
            }

            // 判断是否成功
            if(data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
            }
        }
    });
}

// 登陆或注册
function loginOrRegister() {

    $.ajax({
        url:"/toiletCat/api/user/registerOrLoginUser.action",
        type:"POST",
        async:false,
        data:{
            mobile:$("#mobile").val(),
            ticket:$("#textCode").val()
        },
        success:function(data){

            // 转换数据
            if(typeof(data) == "string") {
                data = eval("("+data+")");
            }

            // 判断是否成功
            if(data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
                return;
            }

            var user = data["result"];

            // 转换数据
            if(typeof(user) == "string") {
                user = eval("("+user+")");
            }

            var url = "";

            // 用户编号
            sessionStorage["toiletCatUserNo"] = user["userNo"];
            // 用户名
            sessionStorage["toiletCatUserName"] = user["userName"];
            // 用户游戏币数
            sessionStorage["toiletCatUserCoin"] = user["userCoin"];
            // 用户头像
            sessionStorage["toiletCatUserImg"] = user["userImg"];
            // 用户邀请码
            sessionStorage["toiletCatInvitationCode"] = user["invitationCode"];

            // 根据来源跳转不同页面
            // 游戏主页
            if(from == "gameIndex") {

                url = "/toiletCat/gameRoom/gameRoom.html?nowType=login&userNo=" + user["userNo"];
            // 用户主页
            } else if(from == "userIndex") {

                url = "/toiletCat/user/userIndex.html?type=" + type + "&userNo=" + user["userNo"];
            // 充值页面
            } else if(from == "recharge") {
                url = "/toiletCat/gameRoom/gameRoom.html?type=" + type + "&userNo=" + user["userNo"];
            // 战利品页
            } else if(from == "userToy") {

                url = "/toiletCat/userToy/userToy.html?type=" + type + "&userNo=" + user["userNo"];
            // 机器主页
            } else {

                url = "/toiletCat/machineRoom/machineRoom.html?nowType=login&userNo=" + user["userNo"];

            }
            window.location.href = url;
        }
    });
}

// 展示用户协议
function showAgreement() {
    var agreementContent = $("#agreement").html();

    var height = $("body").height()/4*3;

    var str = "	<div class='toiletCat-msg' >";

    str += "	</div>";
    str += "		<div class='agreement-msg-div'>";
    str += "			<div class='toiletCat-msg-alert'>";
    str += "				用户协议";
    str += "			</div>";
    str += "			<div class='agreement-text' style='height: " + height + "px'>";
    str += 					agreementContent;
    str += "			</div>";
    str += "			<div class='toiletCat-msg-button' onclick='closeAgreement()'>";
    str += "				同意";
    str += "			</div>";
    str += "		</div>";
    $("body").append(str);
    $(".toiletCat-msg").height($(window).height());

}

function changeAgreement() {
   if(!$("#userAgreement").is(':checked')) {
       $("#login").attr("disabled", true);
   } else {
       $("#login").attr("disabled", false);
   }
}

// 关闭提示框
function closeAgreement() {
    $(".toiletCat-msg").remove();
    $(".agreement-msg-div").remove();
}