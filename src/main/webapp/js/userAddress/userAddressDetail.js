// 用户编号
var userNo = "";

// 用户名
var userName = "";

// 用户游戏币数
var userCoin = "";

// 用户头像
var userImg = "";

// 用户邀请码
var invitationCode = "";

var returnUrl = "";

$(function() {
    // 用户编号
    userNo = getQueryString("userNo");
    // 用户名
    userName = getQueryString("userName");
    // 用户游戏币数
    userCoin = getQueryString("userCoin");
    // 用户头像
    userImg = getQueryString("userImg");
    // 用户邀请码
    invitationCode = getQueryString("invitationCode");

    returnUrl = "/toiletCat/userAddress/userAddress.html?type=gameRoom&userNo="+userNo+"&userName="+userName+"&userImg="+userImg+"&userCoin="+userCoin+"&invitationCode="+invitationCode;

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
                alert(data["result"]);
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
       alert("请填写姓名");
       return false;

   } else if(!checkMobileNo("mobileNo")) {

       alert("请填写正确的手机号");
       return false;

   } else if(!checkLocation()) {

       return false;

   } else if(!isNotNull("address")) {

       alert("请填写详细地址");
       return false;

   }

   return true;

}

// 检查地址
function checkLocation() {

    if($("#province").val() == "") {
        alert("请选择省份");
        return false;

    } else if($("#city").val() == "") {
        alert("请选择城市");
        return false;

    } else if($("#district").val() == "") {
        alert("请选择地区");
        return false;

    }

    return true;

}

