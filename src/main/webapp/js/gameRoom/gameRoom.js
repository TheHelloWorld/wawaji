// 每页数据数
var pageSize = 0;

var nowPage = 1;

var showUrl = "/toiletCat/gameRoom/getAllGameRoomByPage.action";

var width = $(window).width() / 2 - 20;

var bannerType = 1;

var nowType = "";

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

$(function() {

    nowType = getQueryString("nowType");

    var url = "/toiletCat/gameRoom/getUserSeeGameRoomTotalCountAndPageSize.action";

    // 获取banner图
    getBannerByType(bannerType);

    // 获得所有用户可见游戏房间数量及分页
    getTotalCountAndPageSize(url);

    // 分页获得所有用户可见游戏房间并展示
    getUserSeeGameRoomListByPage(nowPage);

    if(nowType == "login") {
        // 用户编号
        userNo = getQueryString("userNo");
        // 用户名
        userName = getQueryString("userName");
        // 用户游戏币数
        userCoin = getQueryString("userCoin");
        // 用户头像
        userImg = getQueryString("userImg");
        // 用户邀请码
        invitationCode = getQueryString("invitationCode");

        setUserInfo();

    } else {
        // 用户自动登陆
        userAutoLogin();
    }
});

// 用户自动登陆
function userAutoLogin() {

    $.ajax({
        url:"/toiletCat/user/autoLogin.action",
        type:"POST",
        async:false,
        success:function(data) {
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

            // 判断是否成功获取用户信息
            if(user == "fail") {
                console.info(user);
                return;
            }

            if(typeof(user) == "string") {
                user = eval("("+user+")");
            }

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

    $.ajax({
        url:"/toiletCat/gameRoom/getUserSeeGameRoomListByPage.action",
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
                alert(data["result"]);
                return;
            }

            var list = data["result"];

            var str = "";

            for(var i = 0; i<list.length; i++) {
                if(i % 2 == 0) {
                    str += "<div class='row' style='margin-bottom: 5px'>";
                }

                if(i % 2 == 0) {
                    str += "<div class='machine-col-xs-6-left' >";
                } else if(i % 2 != 0) {
                    str += "<div class='machine-col-xs-6-right' >";
                }

                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img'>"
                str += "                <img height='100px' width=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
                str += "            </div>";
                str += "            <div style='margin-bottom: 2px'><span>" + list[i]["toyName"] + "</span></div>";
                str += "            <div><span class='my-inline-right' ><img src='/image/background/coin.ico' />:" + list[i]["toyNowCoin"] + "</span></div>";
                str += "            <div><span>在线人数:" + list[i]["viewer"] + "</span></div>";
                str += "        </div>";
                str += "    </div>";
                str += "</div>";

                if(i % 2 != 0) {
                    str += "</div>";
                }
            }
            $("#main").append(str);
        }
    });
}

// 设置用户信息
function setUserInfo() {
    $("#userInfo").html("");
    var str = "<div class='user-info-div-img'><img src='"+userImg+"' class='user-img'></div>";
    str += "<div class='user-info-div-user-coin'><img src='/image/background/coin.ico' />"+userCoin+"</div>";

    $("#userInfo").append(str);
}

// 跳转到登录页
function toLoginPage() {
    window.location.href="/toiletCat/user/login.html?from=gameIndex&type=gameRoom&checkType=checkCode";
}

function toIndex() {
    window.location.href="/toiletCat/gameRoom/gameRoom.html";
}

function getPage(page) {
    nowPage = getPageByNum(nowPage, page, totalPage, step);
    getAllByPage(showUrl, nowPage);
}

function nextPage() {
    nowPage = nextPageNum(nowPage, totalPage, step);
    getAllByPage(showUrl, nowPage);
}

//上一页
function lastPage() {
    nowPage = lastPageNum(nowPage, totalPage, step);
    getAllByPage(showUrl, nowPage);
}