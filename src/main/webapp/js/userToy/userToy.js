// 每页数据数
var userNo = "";

var nowPage = 1;

$(function() {

    // 获得用户编号
    userNo = getQueryString("userNo");

    // 获得总页数和总数量
    getTotalCountAndPageSizeByUserNo();
    
    // 获得当前用户所有战利品
    getAllUserToyByUserNo();

});

// 获得总页数和总数量
function getTotalCountAndPageSizeByUserNo() {
    $.ajax({
        url:"/wawaji/userToy/getMachineTotalCountAndPageSize.action",
        type:"POST",
        async:false,
        data:{
            userNo:userNo
        },
        success:function(data){

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            totalCount = data["totalCount"];

            pageSize = data["pageSize"];

            if(totalCount <= pageSize) {

                totalPage = 1;

            } else if(totalCount % pageSize == 0) {

                totalPage = totalCount / pageSize;

            } else {

                totalPage = parseInt(totalCount / pageSize);
            }
        }
    });
}

// 获得当前用户所有战利品
function getAllUserToyByUserNo() {

    var startPage = (nowPage -1 ) * pageSize;

    $.ajax({
        url:"/wawaji/userToy/getUserToyByUserNo.action",
        type:"POST",
        async:false,
        data:{
            startPage:startPage,
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

                str += "<div id='userToy"+list[i]["id"]+"' class='row'>";
                str += "<div class='panel-body' style='background: #ffff99'>";
                str += "<p><img src='"+list[i]["toyImg"]+"'></p>";
                str += "<p>选择方式:" + list[i]["choiceType"] + "</p>";
                str += "<p>操作:<a href='javascript:void(0);' onclick='toEditUserAddressPage(" + list[i]["id"] + ")'>修改</a> | ";
                str += "<a href='javascript:void(0);' onclick='deleteUserAddress(" + list[i]["id"] + ")'>删除</a></p>";
                str += "</div>";
                str += "</div>"
            }

            $("#userAddress").append(str);
        }

    });
}

// 添加元素
function toAddUserAddressPage() {
    if(canAdd) {
        window.location.href = "/wawaji/userAddress/userAddressDetailPage.html?type=add&userNo="+userNo+"";
    } else {
        alert("最多只能有5个地址")
    }


}

// 修改元素
function toEditUserAddressPage(id) {

    window.location.href = "/wawaji/userAddress/userAddressDetailPage.html?type=update&userNo="+userNo+"&id="+id;
}

// 修改元素
function deleteUserAddress(id) {

    if(!confirm("确定要删除吗?")) {
        return;
    }

    $.ajax({
        url:"/wawaji/userAddress/deleteUserAddressByIdAndUserNo.action",
        type:"POST",
        async:false,
        data:{
            userNo:userNo,
            id:id
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

            $("#userAddress"+id).remove();
            canAdd = true;
        }
    });

}