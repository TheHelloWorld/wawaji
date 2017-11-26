// 每页数据数
var pageSize = 0;

var nowPage = 1;

var showUrl = "/wawaji/machine/getAllMachineByPage.action";

var width = $(window).width() / 2 - 20;

var userNo = "";

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
            userNo = result["userNo"];

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
                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img'>"
                str += "                <img height='100px' width=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
                str += "            </div>";
                str += "            <div style='margin-bottom: 2px'><span>" + list[i]["toyName"] + "</span></div>";
                str += "            <div><span>围观:" + list[i]["viewer"] + "</span></div>"
                str += "            <div><span class='my-inline-right' ><img src='/image/background/coin.ico' />:" + list[i]["toyNowCoin"] + "</span>";
                if(list[i]["available"] == "true") {
                    str += "        空闲</div>";
                } else {
                    str += "        <span class='my-inline-left'><img src='/image/background/busy.ico' />使用中</span></div>";
                }
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

function toUserToy() {
    window.location.href="/wawaji/userToy/userToy.html?userNo="+userNo;
}

function toRecharge() {
    window.location.href="/wawaji/userToy/userToy.html?userNo="+userNo;
}

function toUserIndex() {
    window.location.href="/wawaji/userToy/userToy.html?userNo="+userNo;
}