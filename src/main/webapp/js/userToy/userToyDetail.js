// 每页数据数
var userNo = "";

var id = 0;

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

            var str = " <div class='user-toy-div'>";
            str += "<img class='user-toy-img toy-img index-img' src='" + result["toyImg"] + "'>";
            str += "    </div>";



            $("#userToy").append(str);

            console.info(result);
        }
    });
}

// 返回用户战利品也
function toUserToy() {
    window.location.href = "/wawaji/userToy/userToy.html?userNo="+userNo;
}