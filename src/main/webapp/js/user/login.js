// 在全局定义验证码
var code;

// 当前方式
var checkType = "";

var wait=60;

var type = "";

var from = "";

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

    if(checkType == "checkCode") {
        // 产生验证码
        createCode();
        $("#checkTextDiv").hide();
    } else {
        $("#checkCodeDiv").hide();
        $("#checkTextDiv").show();
    }
});

// 产生验证码
function createCode() {
    code = "";
    // 验证码的长度
    var codeLength = 4;
    // 随机数
    var random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    // 循环操作
    for(var i = 0; i < codeLength; i++) {
        // 取得随机数的索引（0~35）
        var index = Math.floor(Math.random() * 36);
        // 根据索引取得随机数加到code上
        code += random[index];
    }
    $("#checkCode").val(code);
}

// 点击登陆按钮方法
function login() {

    if(!isNotNull("mobile")) {
        alert("手机号不能为空");
        return;
    }

    if(!checkMobileNo()) {
        alert("请填入正确的手机号");
        return;
    }

    if(checkType == "checkCode") {
        validate();
    } else {
        loginOrRegister();
    }
}

// 校验验证码
function validate() {
    // 获取输入框内验证码并转化为大写
    var inputCode = $("#checkCodeText").val().toUpperCase();

    // 若输入的验证码为空
    if(!isNotNull("checkCodeText")) {
        alert("请输入验证码！");
        return;
    }

    // 若输入的验证码与产生的验证码不一致时
    if(inputCode != code) {
        // 则弹出验证码输入错误
        alert("验证码输入错误!");
        // 刷新验证码
        createCode();
        // 清空文本框
        $("#checkCodeText").val("");
        return;
    } else {
        checkType = "checkText";

        var url = window.location.href;

        url = url.split("checkType=")[0] + "checkType=" + checkType;

        window.history.pushState({},0,url);

        $("#checkCodeDiv").hide();
        $("#checkTextDiv").show();
    }
}

// 法送短信验证码
function sendTextCode(o) {

    if(!isNotNull("mobile")) {
        alert("手机号不能为空");
        return;
    }

    if(!checkMobileNo()) {
        alert("请填入正确的手机号");
        return;
    }

    sendTextWaitTime(o);

    sendMobileText();
}

// 检查手机号
function checkMobileNo() {

    var numbers = /^1\d{10}$/;
    //获取输入手机号码
    var val = $('#mobile').val().replace(/\s+/g,"");

    if(!numbers.test(val) || val.length ==0) {
        return false;
    }

    return true;
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
        url:"/toiletCat/user/sendMobileVerificationCode.action",
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
                alert(data["result"]);
            }
        }
    });
}

// 登陆或注册
function loginOrRegister() {

    $.ajax({
        url:"/toiletCat/user/registerOrLoginUser.action",
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
                alert(data["result"]);
                return;
            }

            var user = data["result"];

            // 转换数据
            if(typeof(user) == "string") {
                user = eval("("+user+")");
            }

            var url = "";

            // 根据来源跳转不同页面
            // 游戏主页
            if(from == "gameIndex") {

                url = "/toiletCat/gameRoom/gameRoom.html?nowType=login&userNo="+user["userNo"]+"&userName="+user["userName"]+"&userImg="+user["userImg"]+"&userCoin="+user["userCoin"]+"&invitationCode="+user["invitationCode"];
            // 用户主页
            } else if(from == "userIndex") {

                url = "/toiletCat/user/userIndex.html?type="+type+"&userNo="+user["userNo"]+"&userName="+user["userName"]+"&userImg="+user["userImg"]+"&userCoin="+user["userCoin"]+"&invitationCode="+user["invitationCode"];
            // 充值页面
            } else if(from == "recharge") {
                url = "/toiletCat/gameRoom/gameRoom.html?type="+type+"&userNo="+user["userNo"];
            // 战利品页
            } else if(from == "userToy") {

                url = "/toiletCat/userToy/userToy.html?type="+type+"&userNo="+user["userNo"];
            // 机器主页
            } else {

                url = "/toiletCat/machineRoom/machineRoom.html?nowType=login&userNo="+user["userNo"]+"&userName="+user["userName"]+"&userImg="+user["userImg"]+"&userCoin="+user["userCoin"]+"&invitationCode="+user["invitationCode"];

            }

            window.location.href = url;
        }

    });
}