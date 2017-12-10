// 每页数据数
var pageSize = 0;

var nowPage = 1;

var showUrl = "/toiletCat/gameRoom/getAllGameRoomByPage.action";

var width = $(window).width() / 2 - 20;

var userNo = "";

var bannerType = 1;

$(function() {
    var url = "/toiletCat/gameRoom/getUserSeeGameRoomTotalCountAndPageSize.action";

    // 获取banner图
    getBannerByType(bannerType);

    // 获得所有用户可见游戏房间数量及分页
    getTotalCountAndPageSize(url);

    // 分页获得所有用户可见游戏房间并展示
    getUserSeeGameRoomListByPage(nowPage);

    // 用户自动登陆
    userAutoLogin();

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

            var result = data["result"];

            // 判断是否成功获取用户信息
            if(result == "fail") {
                console.info(result);
                return;
            }

            if(typeof(result) == "string") {
                result = eval("("+result+")");
            }
            console.info(result);
            userNo = result["userNo"];

        }
    });
}


// 用户登陆或注册
function userLoginOrRegister() {

    $.ajax({
        url:"/toiletCat/user/registerOrLoginUser.action",
        type:"POST",
        async:false,
        data:{
            mobileNO:mobileNo,
            ticket:ticket
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

            var result = data["result"];

            if(result == "验证码错误,请重试") {
                alert(result);
                return;
            }

            if(typeof(result) == "string") {
                result = eval("("+result+")");
            }

            console.info(result);
            userNo = result["userNo"];

        }
    });
}

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
                str += "            <div><span>围观:" + list[i]["viewer"] + "</span></div>";
                str += "            <div><span class='my-inline-right' ><img src='/image/background/coin.ico' />:" + list[i]["toyNowCoin"] + "</span></div>";
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

// 跳转到登录页
function toLoginPage() {
    window.location.href="/toiletCat/user/login.html";
}

function toIndex() {
    window.location.href="/toiletCat/index/machineRoom.html";
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

function toUserToy() {
    window.location.href="/toiletCat/userToy/userToy.html?userNo="+userNo + "&type=gameRoom";
}

function toRecharge() {
    window.location.href="/toiletCat/userToy/userToy.html?userNo="+userNo + "&type=gameRoom";
}

function toUserIndex() {
    window.location.href="/toiletCat/userToy/userToy.html?userNo="+userNo + "&type=gameRoom";
}