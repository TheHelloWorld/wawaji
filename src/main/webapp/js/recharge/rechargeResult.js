var flag = true;

var s;

$(function () {

    toiletCatMsg("ok", null);
    // s = setInterval(
    //     function() {
    //         getRechargeResultByTime()
    //     }, 1000);
});

function returnMethod() {
    window.location.href = "/toiletCat/gameRoom/gameRoom.html?nowType=login&userNo=" + sessionStorage["toiletCatUserNo"];
}

function getRechargeResultByTime() {

    if(!flag) {
        return;
    }

    queryResultByOrderNo();
}

// 根据订单号查询支付结果
function queryResultByOrderNo() {

    flag = false;

    $.ajax({
        url:"/toiletCat/api/recharge/getRechargeResultByOrderNo.action",
        type:"POST",
        async:false,
        data:{
            orderNo: sessionStorage["toiletCatUserOrderNo"],
            userNo: sessionStorage["toiletCatUserNo"],
            weChatOrderNo: sessionStorage["toiletCatWeChatOrderNo"]
        },
        success:function(data) {

            // 转换数据
            if (typeof(data) == "string") {
                data = eval("(" + data + ")");
            }

            // 判断是否成功
            if (data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
                return;
            }

            var result = data["result"];

            if (typeof(result) == "string") {
                result = eval("(" + result + ")");
            }

            if(result["result"] == "fail") {

                flag = false;

                // 停止定时
                clearInterval(s);

                toiletCatMsg("充值失败 QAQ", "returnLastPage()");

                $("#loading-img").remove();

            } else if(result["result"] == "success") {

                flag = false;

                // 停止定时
                clearInterval(s);

                // 用户游戏币数
                sessionStorage["toiletCatUserCoin"] = result["userCoin"];

                toiletCatMsg("充值成功", "returnLastPage()");

                $("#loading-img").remove();
            }
        }
    });
}

function returnLastPage() {

    var lastPageUrl = sessionStorage["toiletCatLastPage"];

    if(lastPageUrl == undefined) {
        lastPageUrl = "/toiletCat/gameRoom/gameRoom.html";
    }

    window.location.href = lastPageUrl;
}