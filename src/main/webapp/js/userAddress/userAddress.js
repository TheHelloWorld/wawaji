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

// 是否可以添加标志位
var canAdd = true;

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

    // 判断当前用户是否看继续添加
    judeUserAddress(userNo);

    // 获得当前用户所有地址记录
    getAllUserAddressByUserNo(userNo);

});

// 判断当前是否可以继续添加地址
function judeUserAddress(userNo) {
    $.ajax({
        url:"/toiletCat/userAddress/judgeUserAddressIsMaxNum.action",
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
                canAdd = false;
            }
        }
    });
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
                alert(data["result"]);
                return;
            }

            var list = data["result"];
            var str = "";

            for(var i = 0; i<list.length; i++) {

                str += "<div id='userAddress"+list[i]["id"]+"' class='row'>";
                str += "    <div class='panel-body' >";
                str += "        <div class='my-margin-bottom'>";
                str += "            <span class='my-inline-right' >收货人:" + list[i]["userName"] + "</span>";
                str += "            <span class='my-inline-left' >手机号:" + list[i]["mobileNo"] + "</span>";
                str += "        </div>";
                str += "        <div class='my-margin-bottom' >" + list[i]["province"] + "&nbsp;"+ list[i]["city"] +"&nbsp;"+ list[i]["district"] +"</div>";
                str += "        <div class='my-margin-bottom' >地址:" + list[i]["address"] + "</div>";
                str += "        <div>";
                str += "            <a class='my-edit-right' href='javascript:void(0);' onclick='toEditUserAddressPage(" + list[i]["id"] + ")'><img src='/image/background/edit.png' />修改</a>";
                str += "            <a href='javascript:void(0);' onclick='deleteUserAddress(" + list[i]["id"] + ")'><img src='/image/background/remove.png' />删除</a>";
                str += "        </div>";
                str += "    </div>";
                str += "</div>"
                str += "<div style='text-align: center'>";

                if(i%2 == 0) {
                    str += "<img width='100%' src='/image/line-left.png'>";
                } else {
                    str += "<img width='100%' src='/image/line-right.png'>";
                }

                str += "</div>";
            }

            $("#userAddress").append(str);
        }

    });
}

// 添加元素
function toAddUserAddressPage() {
    if(canAdd) {
        window.location.href = "/toiletCat/userAddress/userAddressDetailPage.html?type=add&userNo="+userNo+"";
    } else {
        alert("最多只能有5个地址")
    }
}

// 修改元素
function toEditUserAddressPage(id) {

    window.location.href = "/toiletCat/userAddress/userAddressDetailPage.html?type=update&userNo="+userNo+"&id="+id;
}

// 修改元素
function deleteUserAddress(id) {

    if(!confirm("确定要删除吗?")) {
        return;
    }

    $.ajax({
        url:"/toiletCat/userAddress/deleteUserAddressByIdAndUserNo.action",
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

function returnMethod() {
    window.location.href="/toiletCat/user/userIndex.html?type=gameRoom&userNo="+userNo + "&userName="+userName+"&userImg="+userImg+"&userCoin="+userCoin+"&invitationCode="+invitationCode;
}