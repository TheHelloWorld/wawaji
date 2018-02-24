var toyNo = "";

var toyForCoin = 0;

var handleStatus = 0;

var userToyJson = {};

var userAddressJson = {};

var userAddressId = 0;

var deliverId = 0;

var deliverCoin = 0;

var freeDeliverNum = 0;

var userCoin = 0;

var flag = true;

var onlyOneUserAddressId = 0;

var userNo = "";

var unHandleNum = 0;

var deliverNum = 0;

var forCoinNum = 0;

$(function() {

    $("#zzc").hide();

    $("#submitButton").hide();

    // 用户编号
    checkSession();

    // 用户游戏币数
    userCoin = sessionStorage["toiletCatUserCoin"];

    toyNo = getQueryString("toyNo");

    userNo = getQueryString("userNo");

    // 根据用户编号和id获得记录信息
    getUserToyByUserNoAndId();

    if(sessionStorage["toiletCatUserToyAddUserAddress"] == "2") {

        sessionStorage["toiletCatUserToyAddUserAddress"] = "null";
        getAllUserAddressByUserNo(userNo);
        clickAddress(onlyOneUserAddressId);
        $("#choiceType").val("2");
        $("#submitButton").show();
    }

});

// 根据用户编号和id获得记录信息
function getUserToyByUserNoAndId() {

    $.ajax({
        url:"/toiletCat/api/userToy/getUserToyByUserNoAndToyNo.action",
        type:"POST",
        async:false,
        data:{
            toyNo:toyNo,
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

            var str =   "<div>";
            str +=      "   <div class='user-toy-div'>";
            str +=      "       <img class='user-toy-img index-img' src='" + result["toyImg"] + "'>";
            str +=      "   </div>";
            str +=      "   <div class='user-toy-div' style='margin-left: 0;'>";
            str +=      "       <div>" + result["toyName"] + "</div>";
            str +=      "       <br/>";
            str +=      "       <div class='user-toy-success index-center'>抓取成功</div>";
            str += "            <br/>";
            str += "       </div>";
            str += "     </div>";

            $("#userToyInfo").append(str);

            var selectStr = "<div style='margin-top: 5%'>";
            selectStr += "      <div class='user-toy-left user-toy-text-status' style='width:30%;'>";
            selectStr += "            <span style='color:white'>状态:</span>";
            selectStr += "      </div>";
            selectStr += "      <div class='user-toy-right user-toy-text-left' style='font-size: 1.5rem;width:50%;color:white;'>";
            selectStr += "          <select id='choiceType' class='user-toy-select' onchange='choiceDeliver()'>";
            selectStr += "              <option value='0'>未选择</option>";
            selectStr += "              <option value='1'>兑换成游戏币</option>";
            selectStr += "              <option value='2'>寄送</option>";
            selectStr += "          </select>";
            selectStr += "    </div>";
            $("#userToySelect").append(selectStr);


        }
    });
}

// 选择方式修改触发方法
function choiceDeliver() {
    // 如果是寄送
    if($("#choiceType").val() == "2") {
        $("#userToyShowMsg").hide();
        $("#userToyShowMsg").html("");

        // 判断当前数量是否可以寄送
        if(unHandleNum < deliverNum) {
            var str = " <div class='custom-font'>";
            str += "还差" + (deliverNum - unHandleNum) + "个才能寄送哦";
            str += "</div>";

            $("#userToyShowMsg").append(str);
            $("#userToyShowMsg").show();
            return;
        }

        getAllUserAddressByUserNo(userNo);
        $("#submitButton").show();
    } else if($("#choiceType").val() == "1") {
        $("#userToyShowMsg").html("");
        $("#userToyShowMsg").hide();
        $("#userAddress").html("");
        choiceExchangeCoin();
        $("#submitButton").show();
    } else {
        $("#userToyShowMsg").hide();
        $("#userToyShowMsg").html("");
        $("#userAddress").html("");
    }
}

// 选择兑换游戏币数量
function choiceExchangeCoin() {
    var str = " <div class='custom-font'>";
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

// 修改兑换战力品数量
function changeNum() {

    forCoinNum = $("#unHandleNum").val();
    $("#exchangeCoinNum").html($("#unHandleNum").val() * toyForCoin);
}

// 获得当前用户所有地址
function getAllUserAddressByUserNo(userNo) {

    $.ajax({
        url:"/toiletCat/api/userAddress/getUserAddressListByUserNo.action",
        type:"POST",
        async:false,
        data:{
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

            var list = data["result"];
            var str = "";

            if(list.length == 0) {
                toiletCatMsg("请添加寄送地址喵", "toUserAddress()");
            }

            for(var i = 0; i<list.length; i++) {

                var userAddressClass = "user-address-line-default";

                if(i == 0) {
                    userAddressClass = "user-address-line";
                }

                onlyOneUserAddressId = list[i]["id"];

                str += "<div class='" + userAddressClass + "' id='userAddress" + list[i]["id"] + "' onclick='clickAddress(" + list[i]["id"] + ")' class='row'>";
                str += "        <div class='user-address-text-line user-address-text-line-first'>";
                str += "            <input id = 'userName"+list[i]["id"]+"' type='hidden' value='" + list[i]["userName"] + "' >";
                str += "            <span  class='user-address-inline-left' >收货人:" + list[i]["userName"] + "</span>";
                str += "            <input id = 'mobileNo"+list[i]["id"]+"' type='hidden' value='" + list[i]["mobileNo"] + "' >";
                str += "            <span class='user-address-inline-right' >手机号:" + list[i]["mobileNo"] + "</span>";
                str += "        </div>";
                str += "        <input id = 'province"+list[i]["id"]+"' type='hidden' value='" + list[i]["province"] + "&nbsp;"+ list[i]["city"] +"&nbsp;"+ list[i]["district"] +"' >";
                str += "        <div class='user-address-text-line' >" + list[i]["province"] + "&nbsp;"+ list[i]["city"] +"&nbsp;"+ list[i]["district"] +"</div>";
                str += "        <input id = 'address"+list[i]["id"]+"' type='hidden' value='" + list[i]["address"] + "' >";
                str += "        <div class='user-address-text-line user-address-text-line-end' >地址:" + list[i]["address"] + "</div>";
                str += "    </div>";
                str += "<div style='text-align: center'>";

                if(i%2 == 0) {
                    str += "<img width='100%' src='/image/line-left.png'>";
                } else {
                    str += "<img width='100%' src='/image/line-right.png'>";
                }

                str += "</div>";
            }

            $("#userAddress").append(str);

            if(sessionStorage["toiletCatUserToyAddUserAddress"] != "2") {
                $("#zzc").show();
            }

        }
    });
}

function toUserAddress() {
    sessionStorage["toiletCatUserToyAddUserAddress"] = "1";
    sessionStorage["toiletCatUserToyId"] = id;
    window.location.href="/toiletCat/userAddress/userAddressDetailPage.html?type=add&userNo="+userNo;
}

// 点击地址
function clickAddress(id) {

    userAddressId = id;

    $("#userToyInfo").hide();
    $("#userToySelect").hide();
    $(".user-address-line").attr("class", "user-address-line-default");
    $("#userAddress"+id).attr("class", "user-address-line");
    $("#zzc").hide();
    $("#choiceAddress").html("");
    $("#choiceAddress").append($("#userAddress"+id).clone());
    $("#userAddress"+id).attr("class", "user-address-line user-address-line-onclick");
    $("#choiceAddress").append("<a style='padding-top: 2%;padding-left: 2%' href='javascript:void(0);' onclick='reChoiceAddress()'>重新选择>></a>");

    if(flag) {
        getAllUnHandleUserToyByUserNo();
    }

}

// 重新选择地址
function reChoiceAddress() {
    $("#zzc").show();
}

// 获得用户所有未处理战利品
function getAllUnHandleUserToyByUserNo() {

    $.ajax({
        url:"/toiletCat/api/userToy/getAllUnHandleUserToyByUserNo.action",
        type:"POST",
        async:false,
        data:{
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

            var list = data["result"];

            var str = "<div style='text-align: center;color: whitesmoke;font-size: 1.25rem;'>" + freeDeliverNum + "个或以上才包邮哦(邮费<img src='/image/background/coin_img.png'>"+deliverCoin+")</div>";

            flag = false;
            for(var i = 0; i<list.length; i++) {

                if(i % 2 == 0) {
                    str += "<div class='row' style='margin-bottom: 5px'>";
                }

                str += "<div class='un-handle-select-col-xs-6' >";
                str += "<label style='width: 100%;'>";
                if(list[i]["toyNo"] == toyNo) {
                    str += "    <input type='checkbox' name='unHandleToy' value='"+list[i]["toyNo"]+"' checked>";
                } else {
                    str += "    <input type='checkbox' name='unHandleToy' value='"+list[i]["toyNo"]+"' >";
                }

                str += "    <input type='hidden' id='unHandleToy"+list[i]["toyNo"]+"' value='"+list[i]["toyName"]+"' >";
                str += "    <div class='user-deliver-toy-div panel-info custom-font'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img' style='text-align: center'>";
                str += "                <img height='100px' maxwidth=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
                str += "            </div>";
                str += "            <div style='margin-bottom: 2px'><span>" + list[i]["toyName"] + "</span></div>";
                str += "        </div>";
                str += "    </div>";
                str += "</label>";
                str += "</div>";

                if(i % 2 != 0 || i == list.length - 1) {
                    str += "</div>";
                }
            }

            $("#unHandleUserToy").append(str);
        }
    });
}


// 修改选择方式
function updateChoiceType() {

    $("#submitButton").html("提交中..");

    $("#submitButton").attr("disabled", true);

    userToyJson["userNo"] = userNo;

    userToyJson["choiceType"] = $("#choiceType").val();

    userToyJson["toyForCoin"] = toyForCoin;

    userToyJson["handleStatus"] = handleStatus;

    userToyJson["deliverId"] = deliverId;

    if($("#choiceType").val() == 2) {

        var userToyNoJson = [];

        var userToyNameJson = [];

        userAddressJson["userName"] = $("#userName"+userAddressId).val();

        userAddressJson["mobileNo"] = $("#mobileNo"+userAddressId).val();

        userAddressJson["address"] = $("#province"+userAddressId).val() + $("#address"+userAddressId).val();

        var count = 0;

        var obj = document.getElementsByName('unHandleToy');

        for(var i = 0;i<obj.length; i++) {

            if(obj[i].checked) {

                count ++;

                var userToyNoSingle = {};

                userToyNoSingle["toyNo"] = obj[i].value;

                userToyNoJson.push(userToyNoSingle);

                var userToyNameSingle = {};

                userToyNameSingle["toyName"] = $("#unHandleToy"+obj[i].value).val();

                userToyNameJson.push(userToyNameSingle);
            }
        }

        // 如果寄送数量小于免费数量
        if(count < freeDeliverNum) {

            if(userCoin < deliverCoin) {

                $("#submitButton").html("确定");

                $("#submitButton").attr("disabled",false);

                toiletCatMsg("游戏币不足QAQ 请充值喵", null);

                return;
            }
        }

        userToyJson["toyNos"] = userToyNoJson;

        userToyJson["userToyNames"] = userToyNameJson;

    }

    // 兑换成游戏币的战利品数量
    userToyJson["forCoinNum"] = forCoinNum;

    // 玩具编号
    userToyJson["toyNo"] = toyNo;

    var userToyStr = JSON.stringify(userToyJson);

    var userAddressStr = JSON.stringify(userAddressJson);

    console.info("userToyStr:"+userToyStr);

    console.info("userAddressStr:"+userAddressStr);

    $.ajax({
        url:"/toiletCat/api/userToy/updateChoiceTypeByIdAndUserNo.action",
        type:"POST",
        async:false,
        data:{
            userToyStr:userToyStr,
            userAddressStr:userAddressStr
        },
        success:function (data) {
            // 转换数据
            if(typeof(data) == "string") {
                data = eval("("+data+")");
            }

            // 判断是否成功
            if(data["is_success"] != "success") {

                $("#submitButton").html("确定");

                $("#submitButton").attr("disabled",false);

                toiletCatMsg(data["result"], null);

                return;
            }

            var result = data["result"];

            if(typeof(result) == "string") {
                result = eval("("+result+")");
            }

            // 更新用户游戏币数
            sessionStorage["toiletCatUserCoin"] = result["userCoin"];

            $("#submitButton").html("确定");

            $("#submitButton").attr("disabled",false);

            window.location.href = "/toiletCat/userToy/userToyHandle.html?userNo=" + userNo;
        }
    });
}

// 返回用户战利品页
function returnMethod() {
    window.location.href = "/toiletCat/userToy/userToy.html?userNo=" + userNo;
}
