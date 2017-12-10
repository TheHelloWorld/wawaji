// 在全局定义验证码
var code;

// 当前方式
var checkType = "";

$(function() {

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
        'width',wc*0.2
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

function login() {
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

function changeURL(){
    var url = document.getElementById('url').value;
    window.history.pushState({},0,'http://'+window.location.host+'/'+url);
}