// 用户编号
var userNo = "";

// 用户名
var userName = "";

// 用户游戏币数
var userCoin = "";

// 用户头像
var userImg = "";

// 用户邀请码
var invitationCode = "";

$(function(){

    // 用户编号
    userNo = sessionStorage["toiletCatUserNo"];
    // 用户名
    userName = sessionStorage["toiletCatUserName"];
    // 用户游戏币数
    userCoin = sessionStorage["toiletCatUserCoin"];
    // 用户头像
    userImg = sessionStorage["toiletCatUserImg"];
    // 用户邀请码
    invitationCode = sessionStorage["toiletCatInvitationCode"];

    setUserInfo();

    var hc = $(window).height();

    $(".background-div").css(
        'height',hc
    );

    $(".center-content").css(
        'height',hc*0.29
    );

    $(".center-other").css(
        'height',hc*0.07
    );

    $(".center-img img").css(
        'height',hc*0.1
    );
});

function returnMethod() {
    window.location.href="/toiletCat/gameRoom/gameRoom.html?nowType=login";
}

function toUserToy() {
    window.location.href="/toiletCat/userToy/userToy.html?type=gameRoom";
}

function toUserAddress() {
    window.location.href="/toiletCat/userAddress/userAddress.html?type=gameRoom";
}

function toUserCatch() {
    window.location.href="/toiletCat/userCatch/userCatch.html?type=gameRoom";
}

// 设置用户信息
function setUserInfo() {
    $("#userCoin").html(userCoin);
    $("#userImg").append("<img src='"+userImg+"' />");
    $("#userName").html(userName);
    $("#invitationCode").html(invitationCode);
}