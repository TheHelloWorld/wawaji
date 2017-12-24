var id = 0;

var toyForCoin = 0;

var handleStatus = 0;

var userToyJson = {};

var userAddressJson = {};

var userAddressId = 0;

var deliverId = 0;

var deliverCoin = 0;

var userCoin = 0;

var flag = true;

$(function() {

    $("#zzc").hide();

    $("#submitButton").hide();

    // 用户编号
    checkSession();



    // 用户游戏币数
    userCoin = sessionStorage["toiletCatUserCoin"];

    id = getQueryString("id");
    
    // 根据用户编号和id获得记录信息
    getUserToyByUserNoAndId();

});

// 根据用户编号和id获得记录信息
function getUserToyByUserNoAndId() {

    $.ajax({
        url:"/toiletCat/userToy/getUserToyByUserNoAndId.action",
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

            var str = " <div class='row'>";
            str += "        <div class='user-toy-div'>";
            str += "            <img class='user-toy-img toy-img index-img' src='" + result["toyImg"] + "'>";
            str += "        </div>";
            str += "        <div class='user-toy-div'>";
            str += "            <div>" + result["toyName"] + "</div>";
            str += "<br/>";
            var newDate = new Date();
            newDate.setTime(result["createTime"]);
            str += "            <div>"+newDate.format('yyyy-MM-dd h:m:s')+"</div>";
            str += "<br/>";
            str += "            <div class='user-toy-success index-center'>抓取成功</div>";
            str += "<br/>";
            str += "        </div>";
            str += "    </div>";
            str += "    <div  class='row'>";
            str += "        <div class='col-xs-5 user-toy-left user-toy-text-status'>";
            str += "            <span>状态:</span>";
            str += "        </div>";
            str += "        <div class='col-xs-5 user-toy-right user-toy-text-left' style='font-size: 1.5rem;color: #666615;'>";

            if (result["choiceType"] == 0) {
                str += "<select id='choiceType' class='user-toy-select' onchange='choiceDeliver()'>";
                str += "    <option value='0'>未选择</option>";
                str += "    <option value='1'>兑换成"+result["toyForCoin"]+"个游戏币</option>";
                str += "    <option value='2'>寄送</option>";
                str += "</select>";

            } else if (result["choiceType"] == 1) {

                str += "<span>已兑换"+result["toyForCoin"]+"个游戏币</span>";

            } else if (result["choiceType"] == 2) {
                str += getDeliverByIdAndUserNo(result["deliverId"]);
            }

            str += "    </div>";

            $("#userToyInfo").append(str);
        }
    });
}

function getDeliverByIdAndUserNo(id) {

    var deliverStr = "";

    $.ajax({
        url:"/toiletCat/deliver/getDeliverByIdAndUserNo.action",
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

            if(result["deliverStatus"] == 0) {
                deliverStr += "<span>待发货</span>";
                deliverStr += "</div>";
            } else {
                deliverStr += "<span>已发货</span>";
                deliverStr += "</div>";
                deliverStr += "<div style='margin-top: 10%;font-size: 1.5rem;color: #666615;'>";
                deliverStr += "    <div class='col-xs-5 user-toy-left user-toy-text-status'>发货单号:</div>";
                deliverStr += "    <div class='col-xs-5 user-toy-right user-toy-text-status'>快递公司:</div>";
                deliverStr += "    <div class='col-xs-5 user-toy-left user-toy-text-status'>"+result["deliverNo"]+"</div>";

                deliverStr += "    <div class='col-xs-5 user-toy-right user-toy-text-status'>"+result["company"]+"</div>";
                deliverStr += "</div>";
            }

        }
    });
    return deliverStr;
}

function choiceDeliver() {
    // 如果是寄送
    if($("#choiceType").val() == "2") {
        getAllUserAddressByUserNo(userNo);
        $("#submitButton").show();
    } else if($("#choiceType").val() == "1"){
        $("#userAddress").html("");
        $("#submitButton").show();
    } else {
        $("#userAddress").html("");
    }
}

// 获得当前用户所有地址
function getAllUserAddressByUserNo(userNo) {

    $.ajax({
        url:"/toiletCat/userAddress/getUserAddressListByUserNo.action",
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
                toiletCatMsg("请添加寄送地址喵", toUserAddress())
            }

            for(var i = 0; i<list.length; i++) {

                var userAddressClass = "user-address-line-default";

                if(i == 0) {
                    userAddressClass = "user-address-line";
                }

                str += "<div class='" + userAddressClass + "' id='userAddress" + list[i]["id"] + "' onclick='clickAddress(" + list[i]["id"] + ")' class='row'>";
                str += "    <div class='panel-body' >";
                str += "        <div class='my-margin-bottom'>";
                str += "            <input id = 'userName"+list[i]["id"]+"' type='hidden' value='" + list[i]["userName"] + "' >";
                str += "            <span  class='my-inline-right' >收货人:" + list[i]["userName"] + "</span>";
                str += "            <input id = 'mobileNo"+list[i]["id"]+"' type='hidden' value='" + list[i]["mobileNo"] + "' >";
                str += "            <span class='my-inline-left' >手机号:" + list[i]["mobileNo"] + "</span>";
                str += "        </div>";
                str += "        <input id = 'province"+list[i]["id"]+"' type='hidden' value='" + list[i]["province"] + "&nbsp;"+ list[i]["city"] +"&nbsp;"+ list[i]["district"] +"' >";
                str += "        <div class='my-margin-bottom' >" + list[i]["province"] + "&nbsp;"+ list[i]["city"] +"&nbsp;"+ list[i]["district"] +"</div>";
                str += "        <input id = 'address"+list[i]["id"]+"' type='hidden' value='" + list[i]["address"] + "' >";
                str += "        <div class='my-margin-bottom' >地址:" + list[i]["address"] + "</div>";
                str += "    </div>";
                str += "</div>";
                str += "<div style='text-align: center'>";

                if(i%2 == 0) {
                    str += "<img width='100%' src='/image/line-left.png'>";
                } else {
                    str += "<img width='100%' src='/image/line-right.png'>";
                }

                str += "</div>";
            }

            $("#userAddress").append(str);

            $("#zzc").show();
        }
    });
}

function toUserAddress() {
    window.location.href="/toiletCat/userAddress/userAddress.html?type=gameRoom&userNo="+userNo;
}

// 点击地址
function clickAddress(id) {

    userAddressId = id;

    $("#userToyInfo").hide();
    $(".user-address-line").attr("class", "user-address-line-default");
    $("#userAddress"+id).attr("class", "user-address-line");
    $("#zzc").hide();
    $("#choiceAddress").html("");
    $("#choiceAddress").append($("#userAddress"+id).clone());
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
        url:"/toiletCat/userToy/getAllUnHandleUserToyByUserNo.action",
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

            flag = false;
            for(var i = 0; i<list.length; i++) {

                if(i % 2 == 0) {
                    str += "<div class='row' style='margin-bottom: 5px'>";
                }

                if(i % 2 == 0) {
                    str += "<div class='machine-col-xs-6-left' >";
                } else if(i % 2 != 0) {
                    str += "<div class='machine-col-xs-6-right' >";
                }

                str += "<label>";
                if(list[i]["id"] == id) {
                    str += "    <input type='checkbox' name='unHandleToy' value='"+list[i]["id"]+"' checked>";
                } else {
                    str += "    <input type='checkbox' name='unHandleToy' value='"+list[i]["id"]+"' >";
                }

                str += "    <input type='hidden' id='unHandleToy"+list[i]["id"]+"' value='"+list[i]["toyName"]+"' >";
                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img'>";
                str += "                <img height='100px' width=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
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

    userToyJson["id"] = id;
    userToyJson["userNo"] = userNo;
    userToyJson["choiceType"] = $("#choiceType").val();
    userToyJson["toyForCoin"] = toyForCoin;
    userToyJson["handleStatus"] = handleStatus;
    userToyJson["deliverId"] = deliverId;


    if($("#choiceType").val() == 2) {

        var userToyIdJson = [];
        var userToyNameJson = [];

        userAddressJson["userName"] = $("#userName"+userAddressId).val();
        userAddressJson["mobileNo"] = $("#mobileNo"+userAddressId).val();
        userAddressJson["address"] = $("#province"+userAddressId).val() + $("#address"+userAddressId).val();

        var count = 0;

        var obj = document.getElementsByName('unHandleToy');
        for(var i = 0;i<obj.length; i++) {
            if(obj[i].checked) {

                count ++;

                var userToyIdSingle = {};

                userToyIdSingle["userToyId"] = obj[i].value;

                userToyIdJson.push(userToyIdSingle);

                var userToyNameSingle = {};

                userToyNameSingle["toyName"] = $("#unHandleToy"+obj[i].value).val();

                userToyNameJson.push(userToyNameSingle);

            }

        }

        if(count < 3) {

            if(userCoin < deliverCoin) {
                toiletCatMsg("游戏币不足QAQ 请充值喵", "recharge()");
            }
        }

        userToyJson["userToyIds"] = userToyIdJson;

        userToyJson["userToyNames"] = userToyNameJson;

    }

    var userToyStr = JSON.stringify(userToyJson);

    var userAddressStr = JSON.stringify(userAddressJson);

    $.ajax({
        url:"/toiletCat/userToy/updateChoiceTypeByIdAndUserNo.action",
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
                toiletCatMsg(data["result"], null);
                return;
            }

            window.location.href = "/toiletCat/userToy/userToyDetailPage.html?type=update&userNo="+userNo+"&id="+id;
        }
    });
}

// 返回用户战利品也
function returnMethod() {
    window.location.href = "/toiletCat/userToy/userToy.html?userNo="+userNo;
}