var id = 0;

var toyForCoin = 0;

var handleStatus = 0;

var deliverId = 0;

var deliverCoin = 0;

var freeDeliverNum = 0;

var userCoin = 0;

var flag = true;

var userNo = "";

var unHandleNum = 0;

var deliverNum = 0;

$(function() {

    // 用户编号
    checkSession();

    // 用户游戏币数
    userCoin = sessionStorage["toiletCatUserCoin"];

    id = getQueryString("id");

    userNo = getQueryString("userNo");

    // 根据用户编号和id获得记录信息
    getUserToyByUserNoAndId();

});

// 根据用户编号和id获得记录信息
function getUserToyByUserNoAndId() {

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

            toyForCoin = result["toyForCoin"];

            handleStatus = result["handleStatus"];

            deliverId = result["deliverId"];

            deliverCoin = result["deliverCoin"];

            freeDeliverNum = result["freeDeliverNum"];

            unHandleNum = result["unHandleNum"];

            deliverNum = result["deliverNum"];

            toyForCoin = result["toyForCoin"];

            var str = " <div class='row'>";
            str += "        <div class='user-toy-div'>";
            str += "            <img class='user-toy-img index-img' src='" + result["toyImg"] + "'>";
            str += "        </div>";
            str += "        <div class='user-toy-div' style='color:white;font-size:1.5rem;margin-left:0%;'>";
            str += "            <div>" + result["toyName"] + "</div>";
            str += "<br/>";
            str += "            <div class='user-toy-success index-center'>抓取成功</div>";
            str += "<br/>";
            str += "        </div>";
            str += "    </div>";
            str += "    <div  class='row'>";
            str += "        <div class='col-xs-5 user-toy-left user-toy-text-status' style='width:30%'>";
            str += "            <span style='color:white'>状态:</span>";
            str += "        </div>";
            str += "        <div class='col-xs-5 user-toy-right user-toy-text-left' style='font-size: 1.5rem;width:50%;color:white;'>";

            str += "    </div>";

            $("#userToyHandleInfo").append(str);
        }
    });
}

// 选择兑换游戏币数量
function choiceExchangeCoin() {
    var str = " <div>";
    str +=    "     将";
    str +=    "     <select id='unHandleNum' onchange='changeNum()' style='color: #000;'>";
    for(var i = unHandleNum; i>= 1; i--) {
        str += "<option value='" + i + "'>" + i + "</option>";
    }
    str += "</select>";
    str += "个战利品兑换成";
    str += "<span id='exchangeCoinNum'></span>";
    str += "个马桶币";
    str += "</div>";
    $("#userToyShowMsg").append(str);
    $("#userToyShowMsg").show();
    changeNum()
}

// 返回用户战利品也
function returnMethod() {
    window.location.href = "/toiletCat/userToy/userToyHandle.html?userNo=" + userNo;
}
