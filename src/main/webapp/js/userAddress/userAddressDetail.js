// 每页数据数
var userNo = "";
// 返回url
var returnUrl = "";

$(function() {
    // 获得用户编号
    userNo = getQueryString("userNo");
    $("#userNo").val(userNo);
    returnUrl = "/toiletCat/userAddress/userAddress.html?userNo="+userNo;
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

function saveOrUpdate() {

    if(getQueryString("type") == "add") {
        var saveUrl = "/toiletCat/userAddress/addUserAddress.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/toiletCat/userAddress/updateUserAddressByIdAndUserNo.action";
        updateThis(updateUrl, returnUrl);
    }

}

