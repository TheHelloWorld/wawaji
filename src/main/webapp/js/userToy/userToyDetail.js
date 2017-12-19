var id = 0;

var toyForCoin = 0;

var handleStatus = 0;

var choiceTypeFlag = false;

var userToyJson = {};

var userAddressJson = {};

var userAddressId = 0;

var flag = true;

$(function() {
    $("#zzc").hide();

    // 用户编号
    checkSession();

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
                toiletCatMsg(data["result"]);
                return;
            }

            var result = data["result"];

            if(typeof(result) == "string") {
                result = eval("("+result+")");
            }

            toyForCoin = result["toyForCoin"];

            handleStatus = result["handleStatus"];

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
            str += "        <div class='col-xs-5 user-toy-right user-toy-text-left'>";
            if (result["choiceType"] == 0) {
                choiceTypeFlag = true;
                str += "<select id='choiceType' class='user-toy-select' onchange='choiceDeliver()'>";
                str += "    <option value='0'>未选择</option>";
                str += "    <option value='1'>兑换成"+result["toyForCoin"]+"个游戏币</option>";
                str += "    <option value='2'>寄送</option>";
                str += "</select>";
            } else if (result["choiceType"] == 1) {

                str += "<span>已兑换"+result["toyForCoin"]+"个游戏币</span>";

            } else if (result["choiceType"] == 2) {

                if (result["handleStatus"] == 20) {

                    str += "<span>待发货</span>";
                } else if (result["handleStatus"] == 30) {

                    str += "<span>已发货</span>";
                    str += getDeliverByIdAndUserNo(str, result["deliverId"]);

                }
            }
            str += "        </div>";
            str += "    </div>";

            if(choiceTypeFlag) {
                str += "<div class='default-height'></div>";
                str += "<nav class ='index-footer default-height' >";
                str += "<button onclick='updateChoiceType("+result["id"]+")' class='btn btn-outline btn-primary btn-lg btn-block'>确认</button>";
                str += "</nav>";
            }

            $("#userToyInfo").append(str);
        }
    });
}

function getDeliverByIdAndUserNo(str,id) {
    $.ajax({
        url:"/toiletCat/deliver/getUserToyByUserNoAndId.action",
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
                toiletCatMsg(data["result"]);
                return;
            }

            var result = data["result"];

            str += "<div>";
            str += "    <div class='col-xs-5 user-toy-left user-toy-text-status'>发货单号:"+result["deliverNo"]+"</div>";
            str += "    <div class='col-xs-5 user-toy-right user-toy-text-status'>快递公司:"+result["company"]+"</div>";
            str += "</div>";

            return str;
        }
    });
}

function choiceDeliver() {
    // 如果是寄送
    if($("#choiceType").val() == "2") {
        getAllUserAddressByUserNo(userNo);
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
                toiletCatMsg(data["result"]);
                return;
            }

            var list = data["result"];
            var str = "";

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
                toiletCatMsg(data["result"]);
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
                str += "    <input type='checkbox' name='unHandleToy' value='"+list[i]["id"]+"' >";
                str += "    <input type='hidden' id='unHandleToy"+list[i]["id"]+"' value='"+list[i]["toyName"]+"' >";
                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img'>"
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

    var userToyStr = JSON.stringify(userToyJson);

    if($("#choiceType").val() == 2) {

        var userToyIdJson = [];
        var userToyNameJson = [];

        userAddressJson["userName"] = $("#userName"+userAddressId).val();
        userAddressJson["mobileNo"] = $("#mobileNo"+userAddressId).val();
        userAddressJson["address"] = $("#province"+userAddressId).val() + $("#address"+userAddressId).val();

        var obj = document.getElementsByName('unHandleToy');
        for(var i = 0;i<obj.length; i++) {
            if(obj[i].checked) {

                userToyIdJson.push(obj[i].value);

                userToyNameJson.push($("#unHandleToy"+obj[i].value).val());

            }

        }

        console.info("userToyIdJson" + userToyIdJson);
        console.info("userToyNameJson" + userToyNameJson);

    }

    var userAddressStr = JSON.stringify(userAddressJson);

    // $.ajax({
    //     url:"/toiletCat/userToy/updateChoiceTypeByIdAndUserNo.action",
    //     type:"POST",
    //     async:false,
    //     data:{
    //         userToyStr:userToyStr,
    //         userAddressStr:userAddressStr
    //     },
    //     success:function (data) {
    //         // 转换数据
    //         if(typeof(data) == "string") {
    //             data = eval("("+data+")");
    //         }
    //
    //         // 判断是否成功
    //         if(data["is_success"] != "success") {
    //             toiletCatMsg(data["result"]);
    //             return;
    //         }
    //
    //         window.location.href = "/toiletCat/userToy/userToyDetailPage.html?type=update&userNo="+userNo+"&id="+id;
    //     }
    // });
}

// 返回用户战利品也
function returnMethod() {
    window.location.href = "/toiletCat/userToy/userToy.html?userNo="+userNo;
}