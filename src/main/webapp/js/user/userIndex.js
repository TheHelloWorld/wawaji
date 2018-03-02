
$(function(){

    checkSession();

    getUserInfoByUserNo(userNo);

    // 若已经填过邀请码则邀请码不展示
    if(sessionStorage["toiletCatInvitationUserNo"] != "0") {
        $("#userInviteCode").remove();
        $("#inviteZzc").remove();
    }

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
        'height', hc * 0.2
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

// 填写邀请码页面
function showInvite() {
    // 判断用户邀请状态
    if(sessionStorage["toiletCatInvitationUserNo"] != "0") {
        toiletCatMsg("只能填写一次邀请码喵", null);
        return;
    }

    $('#inviteZzc').show();$(".index-footer").hide();
}

// 隐藏邀请码
function hideInvite(){
    $('#inviteZzc').hide();$(".index-footer").show();
}

// 用户填写邀请码
function userInvite() {

    if(!isNotNull("invite")) {

        toiletCatMsg("请填写邀请码", null);

        return;
    }

    if($("#invite").val().trim().length != 5) {

        toiletCatMsg("请填写正确邀请码", null);

        return;
    }

    $.ajax({
        url:"/toiletCat/api/user/userInvite.action",
        type:"POST",
        async:false,
        data:{
            inviteCode:$("#invitationCode").html(),
            userNo:sessionStorage["toiletCatUserNo"]
        },
        success:function(data) {
            // 转换数据
            if(typeof(data) == "string") {
                data = eval("("+data+")");
            }

            // 判断是否成功
            if(data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
                return;
            }

            var result = data["result"];
            // 转换数据
            if(typeof(result) == "string") {
                result = eval("("+result+")");
            }

            var userCoin = result["userCoin"];

            sessionStorage["toiletCatUserCoin"] = userCoin;

            $("#userIndexUserCoin").html(userCoin);

            $("#invite").val("");

            $("#userInviteCode").remove();

            toiletCatMsg("成功", "hideInvite()");

        }
    });
}

// 分享到朋友圈
function shareToWeChat() {

    // 若是APP端
    if(sessionStorage["toiletCatType"] == "app") {

        getShareImg();

    } else {

        var str = "	<div onclick='closeShare()' class='toiletCat-msg' >";
        str += "        <img style='float:right;' src='/image/share/guide.png'>";
        str += "	</div>";

        $("body").append(str);
        $(".toiletCat-msg").height($(window).height());
    }
}

// 取消分享
function closeShare() {
    $(".toiletCat-msg").remove();
}

// 获取用户分享图片
function getShareImg() {
    $.ajax({
        url:"/toiletCat/api/weChat/getUserShareImgByUserNo.action",
        type:"POST",
        async:false,
        data:{
            inviteCode:$("#invite").val(),
            userNo:sessionStorage["toiletCatUserNo"]
        },
        success:function(data) {
            // 转换数据
            if(typeof(data) == "string") {
                data = eval("("+data+")");
            }

            // 判断是否成功
            if(data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
                return;
            }

            var url = data["url"];

            // 将参数传给native端
            window.android.share(url);
        }
    });
}