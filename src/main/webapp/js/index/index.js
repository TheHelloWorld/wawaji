// 每页数据数
var pageSize = 0;

var nowPage = 1;

var showUrl = "/wawaji/machine/getAllMachineByPage.action";

var width = $(window).width() / 2 - 20;

$(function(){
    var url = "/wawaji/machine/getMachineTotalCountAndPageSize.action";
    getTotalCountAndPageSize(url);
    getAllMachineByPage(nowPage);
    userAutoLogin();

});

// 用户自动登陆
function userAutoLogin() {
    $.ajax({
        url:"/wawaji/user/autoLogin.action",
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
                return false;
            }

            if(typeof(result) == "string") {
                result = eval("("+result+")");
            }
            console.info(result);

        }
    });
}


// 用户登陆或注册
function userLoginOrRegister() {
    $.ajax({
        url:"/wawaji/user/registerOrLoginUser.action",
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


        }
    });
}

function getAllMachineByPage(nowPage) {
    var startPage = (nowPage - 1) * pageSize;

    $.ajax({
        url:"/wawaji/machine/getUserAllMachineByPage.action",
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

                str += "<div class='machine-col-xs-6' >";
                str += "<div class='machine-panel panel-info'>";
                str += "<div class='panel-heading'></div>";
                str += "<div class='panel-body' style='background: #ffff99'>";
                str += "<img height='100px' width=100% src='" + list[i]["toyImg"] + "' />";
                str += "<p>" + list[i]["toyName"] + "</p>";
                str += "<p>围观:" + list[i]["viewer"] + "</p>";
                str += "<p>游戏币:" + list[i]["toyNowCoin"] + "</p>";

                if(list[i]["available"] == "true") {
                    str += "<p>空闲</p>";
                } else {
                    str += "<p>使用中</p>";
                }
                str += "</div>";
                str += "<div class='panel-footer'></div>";
                str += "</div>";
                str += "</div>";

                if(i % 2 != 0) {
                    str += "</div>";
                }
            }
            $("#main").append(str);

        }
    });
}

function toIndex() {
    window.location.href="/wawaji/index/index.html";
}

function clickBanner() {
    window.location.href="/wawaji/index/index.html";
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