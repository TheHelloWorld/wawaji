// 在全局定义验证码
var code;

// 当前方式
var checkType = "";

var wait=60;

var type = "";

$(function() {

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

    if(checkType = "checkCode") {
        validate();
    } else {

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

        url = url.split("=")[0] + "=" + checkType;

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

    $.ajax({
        url:"/toiletCat/user/sendMobileVerificationCode.action",
        type:"POST",
        async:false,
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
                return;
            }

            sendTextWaitTime(o);
        }

    });
}

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

// 登陆或注册
function loginOrRegister() {

    $.ajax({
        url:"/toiletCat/user/registerOrLoginUser.action",
        type:"POST",
        async:false,
        data:{
            mobileNo:$("#mobile").val(),

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

            var url = "";
            if(type="gameRoom") {
                url = "/toiletCat/gameRoom/gameRoom.html?nowType=login&userNo="+data["result"];

            } else {
                url = "/toiletCat/machineRoom/machineRoom.html?nowType=login&userNo="+data["result"];
            }

            window.location.href = url;
        }

    });
}