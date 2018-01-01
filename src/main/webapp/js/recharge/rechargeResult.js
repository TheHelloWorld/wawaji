var flag = true;

$(function () {

    $("#recharge-result").append("<img src='/image/loading.gif'>");

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

            var str = "";

            if(result["result"] == "fail") {
                flag = false;
                str += "<img src='/image/background/recharge_fail.ico' />";
                str += "<span>充值失败 QAQ</span>";


            } else {
                flag = false;
                str += "<img src='/image/background/recharge_success.ico' />";
                str += "<span>充值成功</span>";
                // 用户游戏币数
                sessionStorage["toiletCatUserCoin"] = result["userCoin"];
            }

            $("#recharge-result").html("");

            $("#recharge-result").append(str);

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