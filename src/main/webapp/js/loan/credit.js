/**
 * Created by lx on 17/9/11.
 */

// var source = window.location.search;
var source = 'or001';
var sourceUrl = '';
var userId = '';
var mobile = '';
var userInfo;
var tagArray = [];

window.onload = function () {
    if (isWeiXin()) {
        document.getElementById("home-header").style.display = "none";
    }
    //弹框动态密码
    /* var txts = wrap.getElementsByTagName("input");
     for(var i = 0; i<txts.length;i++){
         var t = txts[i];
         t.index = i;
         t.setAttribute("readonly", true);
         t.onkeyup=function(){
             this.value=this.value.replace(/\D/g,'');
             if(this.value!=""){
                 var next = this.index + 1;
                 if(next > txts.length - 1) return;
                 txts[next].removeAttribute("readonly");
                 txts[next].focus();
             }
         }
     }
     txts[0].removeAttribute("readonly");*/
};

$(document).ready(function () {
    sourceUrl = location.href;
    getSource();
    queryBanners();
    autoLogin();
    queryTag();

    //给tag标签加上点击事件
    $(".mark-group span").click(function () {
        var thisId = this.id;
        var index = $.inArray(thisId, tagArray);
        //index == -1 时说明当前选择的不在数组里
        if (index == -1) {
            if (tagArray.length >= 3) {
                tip('预期平台标签最多选择3个');
                return;
            }
            tagArray.push(thisId);
        } else {
            tagArray.splice(index, 1);
            var span = document.getElementById(thisId);
            $(span).removeClass("active");
            $(span).find('i').text('');
        }

        $.each(tagArray, function (i, v) {
            var span = document.getElementById(v);
            $(span).addClass("active");
            $(span).find('i').text(i + 1);
        });
    });

    if (source != "register") {
        $.ajax({
            url: "/sudadai/pv/pv.aspx",
            type: "POST",
            data: {
                source: source
            },
            success: function (data) {
                //自己后台测试用
                console.info("data:" + data);
            }
        })
    }
});

/*截取url字符串参数*/
function getSource() {
    var reg = new RegExp("(^|&)" + "source" + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        source = unescape(r[2])
    }
    //url中包含beta或者beta2的时候，上游渠道source变为beta
    if (sourceUrl.indexOf("beta") != -1 || sourceUrl.indexOf("beta2") != -1) {
        source = "beta";
    }
}

function autoLogin() {
    $.ajax({
        url: "/sudadai/user/autoLogin.aspx",
        async: false,
        type: "POST",
        data: {source: source, sourceUrl: sourceUrl},
        success: function (data) {
            console.info("rerer:"+data);
            var result = data;
            if (result.success) {
                mobile = result.mobile;
                userId = result.userId;
                userInfo = result.userInfo;
                if (result.userInfo) {
                    loadHtml(result.targetList);
                } else {
                    queryChannel();
                }
            } else {
                queryChannel();
            }
        }
    });
}

function isWeiXin() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
        return true;
    } else {
        return false;
    }
}

//动态提示，两秒关闭
function tip(msg) {
    var bh = $(document).height();
    var bw = $("body").width();
    $("#errorMassage-fullbg").css({
        height: bh,
        width: bw,
        display: "block"
    });
    $(".errorMassage-dialog").text(msg);
    $(".errorMassage-dialog").show();
    if (window.innerWidth >= 690) {
        var l = (window.innerWidth - 500) / 2;
        $(".errorMassage-dialog").css({width: "500px"});
        $(".errorMassage-dialog").css({left: l + "px"});
    } else {
        $(".errorMassage-dialog").css({width: "70%"});
        $(".errorMassage-dialog").css({left: "15%"});
    }
    //2秒后自动关闭
    setTimeout(function () {
        $("#errorMassage-fullbg").css({
            display: "none"
        });
        $(".errorMassage-dialog").text("");
        $(".errorMassage-dialog").hide();
    }, 2000)
}

var countdown = 60;

//动态获取密码操作
function getActivePassword() {

    var mobile_login = $("#mobile").val();

    if (!(/^1[3|4|5|7|8]\d{9}$/.test(mobile_login))) {
        tip("请填写正确的手机号");
        return;
    } else if ($("#rcode").val() == "") {
        tip("请填写图片验证码");
        return;
    } else {
        $.ajax({
            url: "/sudadai/user/checkPicVcode.aspx",
            async: false,
            type: "POST",
            data: {
                mobile: mobile_login,
                rcode: $("#rcode").val()
            },
            success: function (data) {
                if (data.success) {
                    countdown = 60;
                    $(".panter0").css("display", "none");
                    $(".panter1").css("display", "block");
                    sendValidateMsgCode();
                } else {
                    tip(data.reason);
                }
            }
        });
    }
}

function jump(ele) {
    var product_id = $(ele).attr("id");
    var id = product_id.substring(7);
    _hmt.push(['_trackEvent', '渠道跟踪', '点击渠道：' + id]);
    if (mobile == "") {
        showBg();
        return;
    } else {
        if (userInfo) {
            $.post("/sudadai/userTo/userLeave.aspx", {"targetId": id, "mobile": mobile}, function (data) {
                if (data.success) {
                    _hmt.push(['_trackEvent', '渠道跟踪', '跳转到渠道：' + id, '上游渠道：' + source]);
                    window.location.href = data.url;
                } else {
                    tip(data.reason);
                }
            })
        } else {
            fullDialog();
        }
    }
}

//显示灰色 jQuery 遮罩层
function showBg() {
    var bh = $("body").height();
    var bw = $("body").width();
    $("#fullbg").css({
        height: bh,
        width: bw,
        display: "block"
    });
    $("#dialog").css({
        height: bh,
        display: "block"
    });
    forgetPwd();
}

//关闭灰色 jQuery 遮罩
function closeBg() {
    $("#fullbg,#dialog").css("display", "none");
    $(".panter0").css("display", "none");
    $(".panter1").css("display", "none");
    $("#mobile").val("");
    $("#rcode").val("");
}

//忘记密码
function forgetPwd() {
    window.clearInterval(InterValObj);
    $(".panter0").css("display", "block");
    $(".panter1").css("display", "none");
    $("#ticket").val("");
}

//验证码时间特效
var InterValObj; // timer变量，控制时间
var count = 60; // 间隔函数，1秒执行
var curCount;// 当前剩余秒数
function SetRemainTime() {
    if (curCount == 0) {
        window.clearInterval(InterValObj);// 停止计时器
        document.getElementById('sendSmsCode').removeAttribute("disabled");
        $("#sendSmsCode").removeClass("btn-disabled");
        $("#sendSmsCode").val("重新发送动态密码");
    } else {
        curCount--;
        $("#sendSmsCode").val(curCount + "秒以后重新发送");
    }
}

function queryBanners() {
    $.ajax({
        url: "/sudadai/ad/queryAd.aspx",
        async: false,
        type: "POST",
        data: {},
        success: function (data) {
            var result = data;
            if (result.success) {
                var htmlPoint = '';
                var htmlStr = '';
                if (result.adList.length > 0) {
                    $.each(result.adList, function (index, item) {
                        if (index == 0) {
                            htmlPoint += '<li data-target="#myCarousel" data-slide-to="' + index + '" class="active"></li>';
                            htmlStr += '<div class="item active">\n';
                        } else {
                            htmlPoint += '<li data-target="#myCarousel" data-slide-to="' + index + '"></li>';
                            htmlStr += '<div class="item">\n';
                        }
                        htmlStr += '<img src="' + item.adImgUrl + '" alt="" onclick=\'bannerLink("' + item.adUrl + '")\'>\n' +
                            '</div>';
                    });
                    $('#myCarousel').show();
                    $('#myCarousel').attr("data-ride", "carousel");
                    $('#point').html(htmlPoint);
                    $('#adImage').html(htmlStr);
                    /*  //手动轮播图片
                    $('#myCarousel').bsTouchSlider();*/
                    /*   $("#myCarousel").on("swipeleft",function(){
                           $('#myCarousel').carousel('next')
                       });
                       $("#myCarousel").on("swiperight",function(){
                           $('#myCarousel').carousel('prev')
                       });*/
                    var myTouch = util.toucher(document.getElementById('myCarousel'));

                    myTouch.on('swipeLeft', function (e) {
                        $('#myCarousel').carousel('next');
                    }).on('swipeRight', function (e) {
                        $('#myCarousel').carousel('prev');
                    });
                } else {
                    $('#myCarousel').removeAttr("data-ride");
                    $('#myCarousel').hide();
                }
            } else {
                tip(result.reason);
            }
        }
    });
}

function bannerLink(url) {
    window.location.href = url;
}

function queryChannel() {
    $.ajax({
        url: "/sudadai/target/queryDefaultTarget.aspx",
        async: false,
        type: "POST",
        data: {
            source: source,
            sourceUrl: sourceUrl
        },
        success: function (result) {
            if (result.success) {
                loadHtml(result.targetList);
            } else {
                tip(result.reason);
            }
        }
    });
}

function loadHtml(targetList) {
    var htmlStr = '';
    $.each(targetList, function (index, item) {
        var html1 = '<li id="product' + item.id + '" onclick="jump(this)">\n' +
            '<div class="credit-main">\n' +
            '<div class="credit-main-f0 none-left-right">\n' +
            '<div class="credit-title">\n' +
            '<div class="logo-icon"><img src="' + item.imgUrl + '" alt=""></div><span>' + item.name + '</span>\n';

        var html2 = '';
        var tagArr = item.tags.split(';');
        for (var i = 0; i < tagArr.length; i++) {
            html2 += '<label>' + tagArr[i] + '</label>';
        }
        var html3 = '</div>\n' +
            '<div class="credit-amount-info">\n' +
            '<div class="credit-amount"><span>' + item.amountInterval + '</span>元<i></i></div><label>' + item.timeInterval + '</label>\n' +
            '<p class="credit-btn">申请借款</p>\n' +
            '</div>\n' +
            '<div class="credit-condition">\n' +
            '<p class="pfirst">申请条件：</p><p class="plast">' + item.applicationCondition + '</p>\n' +
            '</div>\n' +
            '</div>\n' +
            '</div>\n' +
            '</li>';
        htmlStr += (html1 + html2 + html3);
    });
    $('#credit').html(htmlStr);
}

function queryTag() {
    $.ajax({
        url: "/sudadai/tag/getAllTag.aspx",
        async: false,
        type: "POST",
        success: function (data) {
            var result = data;
            if (result.success) {
                var htmlStr = '';
                $.each(result.tagList, function (index, item) {
                    htmlStr += '<span id= ' + item.tagCode + ' > ' + item.content + ' <i></i></span>';
                });
                $('#tagList').html(htmlStr);
            }
        }
    });
}

//发送短信验证码
function sendValidateMsgCode() {
    curCount = count;
    $("#sendSmsCode").attr("disabled", "true");
    $("#sendSmsCode").addClass("btn-disabled");
    $("#sendSmsCode").val(curCount + "秒以后重新发送");
    InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
}

function sendMsgAgain() {
    getActivePassword();
}

function loginbyticket() {
    var mobile_login = $("#mobile").val();

    if (!(/^1[3|4|5|7|8]\d{9}$/.test(mobile_login))) {
        tip("请填写正确的手机号");
        return;
    }
    var ticket = $("#ticket").val();
    $.post("/sudadai/user/loginByTicket.aspx", {
        "mobile": mobile_login,
        "ticket": ticket,
        "source": source,
        "sourceUrl": sourceUrl
    }, function (data) {
        var result = data;
        if (result.success) {
            mobile = result.mobile;
            userId = result.userId;
            userInfo = result.userInfo;
            if (result.userInfo) {
                loadHtml(result.targetList);
                closeBg();
            } else {
                fullDialog();
            }
        } else {
            tip(result.reason);
        }
    });
}

//弹出注册完成的弹框
function fullDialog() {
    $("#credit").css("display", "none");
    $("#dialog").css("display", "none");
    $("#fullbg").css({
        display: "none"
    });
    var bh = $("body").height();
    var bh = $("windon").height();
    var bhh = $(window).height();
    var h;
    if (bh > bhh) {
        h = bh;
    } else
        h = bhh;
    $("#full-dialog").css({
        height: (h) + "px",
        display: "block"
    });
    scrollTo(0, 0);
}

//填完信息完成的点击事件
function userInfoSuccess() {
    var userName = $("#userName").val();
    var idCardNo = $("#idCardNo").val();

    if (userName == '') {
        tip('请输入真实姓名');
        return;
    }
    var userNameReg = /^[\u4e00-\u9fa5]{2,10}$/;
    if (!userNameReg.test(userName)) {
        tip('请输入中文姓名');
        return;
    }
    if (idCardNo == '') {
        tip('请输入身份证号码');
        return;
    }
    // var idCardNoReg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    var idCardNoReg = /^\d{15}(\d{2}[0-9xX])?$/;

    if (!idCardNoReg.test(idCardNo)) {
        tip('请输入正确的身份证号');
        return;
    }
    if (tagArray.length == 0) {
        tip('请选择预期平台标签');
        return;
    }

    var data = {
        userId: userId,
        realName: userName,
        idCardNo: idCardNo,
        source: source,
        sourceUrl: sourceUrl,
        userTags: JSON.stringify(tagArray)
    };

    $("#userInfobtn").attr("disabled", "disabled");
    $("#userInfobtn").css("background", "#e4e2e2");
    $("#userInfobtn").css("color", "#ccc");

    $.ajax({
        url: "/sudadai/userInfo/saveUserInfoAndTags.aspx",
        async: false,
        type: "POST",
        data: data,
        success: function (data) {
            var result = data;
            if (result.success) {
                userInfo = true;
                loadHtml(result.targetList);
                $("#credit").css({
                    display: "block"
                });
                $("#full-dialog").css({
                    display: "none"
                });
                $("#userInfobtn").removeAttr("disabled");
                $("#userInfobtn").css("background", "#3399ff");
                $("#userInfobtn").css("color", "#ffffff");
            } else {
                tip(result.reason);
                $("#userInfobtn").removeAttr("disabled");
                $("#userInfobtn").css("background", "#3399ff");
                $("#userInfobtn").css("color", "#ffffff");
            }
        }
    });
}
