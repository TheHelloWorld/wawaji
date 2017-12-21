
var returnUrl = "";

$(function() {

    // 用户编号
    checkSession();

    returnUrl = "/toiletCat/userAddress/userAddress.html?userNo"+userNo;

    $("#userNo").val(userNo);

    if(getQueryString("type") == "update") {
        var id = getQueryString("id");
        getUserAddress(id, userNo);
    }
});

// 根据id和用户编号获得用户地址
function getUserAddress(id, userNo) {
    $.ajax({
        url:"/toiletCat/userAddress/getUserAddressByIdAndUserNo.action",
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

            // 转换数据
            if(typeof(result) == "string") {
                result = eval("("+result+")");
            }

            // 赋值
            $("#id").val(result["id"]);
            $("#userName").val(result["userName"]);
            $("#mobileNo").val(result["mobileNo"]);

            $("#province").val(result["province"]).trigger("change");
            $("#city").val(result["city"]).trigger("change");
            $("#district").val(result["district"]).trigger("change");

            $("#address").val(result["address"]);

        }
    });
}

// 储存或更新
function saveOrUpdate() {
    // 校验参数
    if(!checkData()) {
        return;
    }

    if(getQueryString("type") == "add") {
        var saveUrl = "/toiletCat/userAddress/addUserAddress.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/toiletCat/userAddress/updateUserAddressByIdAndUserNo.action";
        updateThis(updateUrl, returnUrl);
    }

}

function returnMethod() {
    window.location.href = returnUrl;
}

// 检查参数
function checkData() {

   if(!isNotNull("userName")) {
       toiletCatMsg("请填写姓名", null);
       return false;

   } else if(!checkMobileNo("mobileNo")) {

       toiletCatMsg("请填写正确的手机号", null);
       return false;

   } else if(!checkLocation()) {

       return false;

   } else if(!isNotNull("address")) {

       toiletCatMsg("请填写详细地址", null);
       return false;

   }

   return true;

}

// 检查地址
function checkLocation() {

    if($("#province").val() == "") {
        toiletCatMsg("请选择省份", null);
        return false;

    } else if($("#city").val() == "") {
        toiletCatMsg("请选择城市", null);
        return false;

    } else if($("#district").val() == "") {
        toiletCatMsg("请选择地区", null);
        return false;
    }
    return true;

}

