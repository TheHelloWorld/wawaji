// 每页数据数
var userNo = "";

var id = 0;

var choiceTypeFlag = false;

$(function() {

    // 获得用户编号
    userNo = getQueryString("userNo");

    id = getQueryString("id");
    
    // 根据用户编号和id获得记录信息
    getUserToyByUserNoAndId();

});

// 根据用户编号和id获得记录信息
function getUserToyByUserNoAndId() {

    $.ajax({
        url:"/wawaji/userToy/getUserToyByUserNoAndId.action",
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

            if(typeof(result) == "string") {
                result = eval("("+result+")");
            }
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

            $("#userAddress").before(str);
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

                str += "<div class='my-bottom-line div-radius' id='userAddress"+list[i]["id"]+"' class='row'>";
                str += "    <div class='panel-body' >";
                str += "        <div class='my-margin-bottom'>";
                str += "            <span class='my-inline-right' >收货人:" + list[i]["userName"] + "</span>";
                str += "            <span class='my-inline-left' >手机号:" + list[i]["mobileNo"] + "</span>";
                str += "        </div>";
                str += "        <div class='my-margin-bottom' >" + list[i]["province"] + "&nbsp;"+ list[i]["city"] +"&nbsp;"+ list[i]["district"] +"</div>";
                str += "        <div class='my-margin-bottom' >地址:" + list[i]["address"] + "</div>";
                str += "    </div>";
                str += "</div>"
            }

            $("#userAddress").append(str);
        }

    });
}

// 修改选择方式
function updateChoiceType() {
    console.info($("#choiceType").val())
    $.ajax({
        url:"/wawaji/userToy/updateChoiceTypeByIdAndUserNo.action",
        type:"POST",
        async:false,
        data:{
            choiceType:$("#choiceType").val(),
            id:id,
            userNo:userNo
        },
        success:function (data) {
            // 转换数据
            if(typeof(data) == "string") {
                data = eval("("+data+")");
            }

            // 判断是否成功
            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            window.location.href = "/wawaji/userToy/userToyDetailPage.html?type=update&userNo="+userNo+"&id="+id;
        }
    });
}

// 返回用户战利品也
function toUserToy() {
    window.location.href = "/wawaji/userToy/userToy.html?userNo="+userNo;
}