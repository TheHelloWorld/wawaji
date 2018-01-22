var flag = true;

var runFlag = true;

var s;

$(function () {

    s = setInterval(
        function() {
            getRechargeResultByTime()
        }, 1000);
});

function getRechargeResultByTime() {

    if(!runFlag) {
        return;
    }

    if(!flag) {
        return;
    }

    getRechargeResult();
}

// 获得充值结果
function getRechargeResult() {

    runFlag = false;

    // 订单号
    var orderNo = getQueryString("out_trade_no");

    $.ajax({
        url:"/toiletCat/api/recharge/getRechargeResultByOrderNo.action",
        type:"POST",
        data:{
            orderNo:orderNo
        },
        async:false,
        success:function(data) {

            runFlag = true;

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

            if(typeof(result) == "string") {
                result = eval("("+result+")");
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