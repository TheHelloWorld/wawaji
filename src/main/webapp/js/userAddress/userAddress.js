// 每页数据数
var userNo = "";

$(function() {
    // 获得用户编号
    userNo = getQueryString("userNo");
    // 判断当前用户是否看继续添加
    judeUserAddress(userNo);

    // 获得当前用户所有地址记录
    getAllUserAddressByUserNo(userNo);

});



// 判断当前是否可以继续添加地址
function judeUserAddress(userNo) {
    $.ajax({
        url:"/wawaji/userAddress/judgeUserAddressIsMaxNum.action",
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
                alert(data["result"]);
                return;
            }

            // 若后台操作成功则删除当前行
            if(data["result"] == "fail") {
               $("#addUserAddress").attr("disable", true);
            }
        }
    });
}

// 获得当前用户所有地址
function getAllUserAddressByUserNo(userNo) {

    $.ajax({
        url:"/wawaji/userAddress/getUserAddressListByUserNo.action",
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
                alert(data["result"]);
                return;
            }

            var list = data["result"];
            var str = "";

            for(var i = 0; i<list.length; i++) {

                str += "<div class='row' style='margin-bottom: 5px'>";
                str += "<div class='panel-body' style='background: #ffff99'>";
                str += "<p>姓名:" + list[i]["userName"] + "</p>";
                str += "<p>手机号:" + list[i]["mobileNo"] + "</p>";
                str += "<p>地址:" + list[i]["address"] + "</p>";
                str += "<p>操作:" + list[i]["address"] + "</p>";
                str += "</div>";
                str += "</div>"
            }

            $("#userAddress").append(str);
        }

    });
}

// 添加元素
function toAddUserAddressPage() {

    window.location.href = "/wawaji/userAddress/userAddressDetailPage.jsp?type=add&userNo="+userNo+"";
}

// 修改元素
function toEditUserAddressPage() {

    window.location.href = "/wawaji/userAddress/userAddressDetailPage.jsp?type=update&userNo="+userNo+"";
}