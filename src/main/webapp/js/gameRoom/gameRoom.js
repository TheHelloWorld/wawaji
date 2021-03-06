// 每页数据数
var pageSize = 10;

var nowPage = 1;

var width = $(window).width() / 2 - 20;

var bannerType = 1;

var loginFlag = "";

var loginUrl = "/toiletCat/user/login.html?from=gameIndex&type=gameRoom&checkType=checkCode";

$(function() {

    if(sessionStorage["toiletCatType"] == null || sessionStorage["toiletCatType"] == undefined) {

        // 从url获取类型参数
        var type = getQueryString("type");

        if(type == "app") {

            sessionStorage["toiletCatType"] = "app";

        } else {
            sessionStorage["toiletCatType"] = "wx_web";
        }
    }

    var url = "/toiletCat/api/gameRoom/getUserSeeGameRoomTotalCountAndPageSize.action";

    // 获取banner图
    getBannerByType(bannerType);

    // 获得所有用户可见游戏房间数量及分页
    getTotalCountAndPageSize(url);

    // 分页获得所有用户可见游戏房间并展示
    getUserSeeGameRoomListByPage(nowPage);

    $("#loading").hide();

    var indexBodyDivHeight = $(".index-body-div").height() + $("#banner-box").height() + $(".default-header").height();

    var addHeight = $("#main").height() +  ($(".default-height").height() * 2);

    $(".index-body-div").scroll(function(){

        if ($(this).scrollTop() + indexBodyDivHeight >= addHeight) {
            nextPage();
            addHeight = $("#main").height() + ($(".default-height").height() * 2);
        }
    });

});

// 用户自动登陆
function userAutoLogin() {

    $.ajax({
        url:"/toiletCat/api/user/autoLogin.action",
        type:"POST",
        async:false,
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

            var user = data["result"];

            // 判断是否成功获取用户信息
            if(user == "fail") {
                loginFlag = user;
                return;
            }

            if(typeof(user) == "string") {
                user = eval("("+user+")");
            }

            // 赋值操作
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

            // 用户编号
            userNo = user["userNo"];
            // 用户名
            userName = user["userName"];
            // 用户游戏币数
            userCoin = user["userCoin"];
            // 用户头像
            userImg = user["userImg"];
            // 用户邀请码
            invitationCode = user["invitationCode"];

            setUserInfo();
        }
    });
}

// 分页获得游戏房间数据
function getUserSeeGameRoomListByPage(nowPage) {

    var startPage = (nowPage - 1) * pageSize;
    var loading = " <div id='loading-div' style='text-align: center;height: 10%'>";
    loading += "        <img src='/image/loading/loading.gif' height='100%'>";
    loading += "    </div>";
    $("#main").append(loading);
    $.ajax({
        url:"/toiletCat/api/gameRoom/getUserSeeGameRoomListByPage.action",
        type:"POST",
        async:false,
        data:{
            startPage:startPage
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

            var list = data["result"];

            var str = "";

            for(var i = 0; i<list.length; i++) {

                if(i % 2 == 0) {
                    str += "<div class='row' style='margin-bottom: 5px'>";
                }

                str += "<div class='toiletCat-col-xs-6' onclick='toGamePage("+list[i]["gameRoomNo"]+")'>";
                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='index-img' style='text-align:center;'>";
                str += "                <img height='100px' maxwidth=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
                str += "            </div>";
                str += "            <div style='margin-bottom: 1%; margin-left: 5%'><span>" + list[i]["toyName"] + "</span></div>";
                str += "            <div class='my-inline-right'><span><img width='20%' src='/image/background/coin_img.png' align='absmiddle' />:" + list[i]["toyNowCoin"] + "</span></div>";
                str += "            <div style='margin-left: 5%'><span>在线人数:<span id='viewer"+list[i]["gameRoomNo"]+"'>" + list[i]["viewer"] + "</span></span></div>";
                str += "        </div>";
                str += "    </div>";
                str += "</div>";

                if(i % 2 != 0 || i == list.length - 1) {
                    str += "</div>";
                }
            }
            $("#main").append(str);
            $("#loading-div").remove();
        }
    });
}

function toGamePage(gameRoomNo) {

    var gameRoomUrl = "/toiletCat/game/bin/index.html?userNo=" + userNo + "&gameRoomNo=" + gameRoomNo;

    if(loginFlag == "fail") {
        gameRoomUrl = loginUrl;
    }
    window.location.href = gameRoomUrl;

}

// 设置用户信息
function setUserInfo() {

    $("#userInfo").html("");
    var str = " <div class='user-info-div-img'>";
    str += "        <img src='"+userImg+"' class='user-img'>";
    str += "    </div>";
    str += "    <div class='user-info-div-user-coin'>";
    str += "        <img src='/image/background/coin_img.png' align='absmiddle' /><span id='gameRoomIndexUserCoin' >" + userCoin + "</span>";
    str += "    </div>";

    $("#userInfo").append(str);
}

// 跳转到登录页
function toLoginPage() {
    window.location.href = loginUrl;
}

function nextPage() {
    nowPage ++;
    if(nowPage <= totalPage) {
        getUserSeeGameRoomListByPage(nowPage);
    } else {
        nowPage = totalPage;
    }
}

// 跳转到战利品页
function toUserToy() {

    var userToyUrl = "/toiletCat/userToy/userToy.html?userNo="+userNo + "&type=gameRoom";

    if(loginFlag == "fail") {
        userToyUrl = loginUrl;
    }
    window.location.href = userToyUrl;
}

// 跳转到充值页面
function toRecharge() {

    if(loginFlag == "fail") {
        window.location.href = loginUrl;
    } else {
        recharge();
    }


}

// 跳转到用户主页
function toUserIndex() {

    var userIndexUrl = "/toiletCat/user/userIndex.html?type=gameRoom&userNo=" + userNo;

    if(loginFlag == "fail") {
        userIndexUrl = loginUrl;
    }

    window.location.href = userIndexUrl;
}