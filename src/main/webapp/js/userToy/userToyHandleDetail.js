var id = 0;

var userNo = "";

$(function() {

    // 用户编号
    checkSession();

    id = getQueryString("id");

    userNo = getQueryString("userNo");

    // 根据用户编号和id获得记录信息
    getUserToyHandleByUserNoAndId();

});

// 根据用户编号和id获得记录信息
function getUserToyHandleByUserNoAndId() {

    $.ajax({
        url:"/toiletCat/api/userToyHandle/getUserToyHandleByUserNoAndId.action",
        type:"POST",
        async:false,
        data:{
            id:id,
            userNo:userNo
        },
        success:function(data){

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

            var choiceType = result["choiceType"];

            var deliverStatus = result["deliverStatus"];

            var forCoinNum = result["forCoinNum"];

            var toyForCoin = result["toyForCoin"];

            var toyName = result["toyName"];

            var str = " <div class='row'>";
            str += "        <div class='user-toy-div'>";
            str += "            <img class='user-toy-img index-img' src='" + result["toyImg"] + "'>";
            str += "        </div>";
            str += "        <div class='user-toy-div' style='color: white; font-size: 1.5rem; margin-left: 0;'>";
            str += "            <div class='custom-font'>" + result["toyName"] + "</div>";
            str += "<br/>";
            str += "            <div class='user-toy-success index-center custom-font'>抓取成功</div>";
            str += "<br/>";
            str += "        </div>";
            str += "    </div>";

            var msg = "";

            if(choiceType == 1) {
                msg = "<div class='custom-font'>";
                msg += "已将" + forCoinNum + "个" + toyName + "兑换成" + toyForCoin + "马桶币";
                msg += "</div>";

                $("#userToyHandleShowMsg").append(msg);

            } else if(choiceType == 2) {

                if(deliverStatus == 0) {

                    msg = "<div class='custom-font'>";
                    msg += "    <span>待发货</span>";
                    msg += "</div>";

                    $("#userToyHandleShowMsg").append(msg);

                } else if(deliverStatus == 1) {
                    msg = "<div class='custom-font'>";
                    msg += "    <span>已发货</span>";
                    msg += "</div>";
                    msg += "<div style='margin-top: 10%;font-size: 1.5rem;color: white;'>";
                    msg += "    <div class='col-xs-5 user-toy-left user-toy-text-status custom-font'>发货单号:</div>";
                    msg += "    <div class='col-xs-5 user-toy-right user-toy-text-status custom-font'>快递公司:</div>";
                    msg += "    <div class='col-xs-5 user-toy-left user-toy-text-status custom-font'>"+result["deliverNo"]+"</div>";

                    msg += "    <div class='col-xs-5 user-toy-right user-toy-text-status custom-font'>"+result["company"]+"</div>";
                    msg += "</div>";

                    $("#userToyHandleShowMsg").append(msg);
                }

            }

            $("#userToyHandleInfo").append(str);
        }
    });
}

// 返回用户战利品也
function returnMethod() {
    window.location.href = "/toiletCat/userToy/userToyHandle.html?userNo=" + userNo;
}
