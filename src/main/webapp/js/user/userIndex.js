
$(function(){

    checkSession();

    getUserInfoByUserNo(userNo);

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
        'height', hc
    );

    $(".center-content").css(
        'height', hc * 0.29
    );

    $(".center-other").css(
        'height', hc * 0.07
    );

    $(".center-img img").css(
        'height', hc * 0.1
    );
});

// 返回方法
function returnMethod() {
    window.location.href="/toiletCat/gameRoom/gameRoom.html?nowType=login&userNo=" + userNo;
}

// 跳转到用户地址页
function toUserAddress() {
    window.location.href="/toiletCat/userAddress/userAddress.html?type=gameRoom&userNo=" + userNo;
}

// 跳转到用户抓去记录页
function toUserCatch() {
    window.location.href="/toiletCat/userCatch/userCatch.html?type=gameRoom&userNo=" + userNo;
}

// 跳转到用户充值记录页
function toUserSpend() {
    window.location.href="/toiletCat/userSpendRecord/userSpendRecord.html?type=gameRoom&userNo=" + userNo;
}

// 设置用户信息
function setUserInfo() {
    $("#userIndexUserCoin").html(userCoin);
    $("#userImg").append("<img src='"+userImg+"' />");
    $("#userName").html(userName);
    $("#invitationCode").html(invitationCode);
}