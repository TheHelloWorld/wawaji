var flag = true;

$(function () {

    $("#recharge-result").append("<img style='margin-top: 30%;' width='100%' src='/image/recharge-result-loading.gif'>");

    setTimeout(
        function() {
            getRechargeResult()
        }, 1000);
});

// 获得充值结果
function getRechargeResult() {

    if(!flag) {
        return;
    }

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

            $("#recharge-result").html("");

            if(result["result"] == "fail") {
                flag = false;
                toiletCatMsg("充值失败 QAQ", "returnLastPage()");


            } else if(result["result"] == "success"){
                flag = false;

                // 用户游戏币数
                sessionStorage["toiletCatUserCoin"] = result["userCoin"];
                toiletCatMsg("充值成功", "returnLastPage()");
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