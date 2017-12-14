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

    // 分页获得当前用户所有抓取记录
    getAllUserCatchRecordByUserNo(userNo);

});

// 分页获得当前用户所有抓取记录
function getAllUserCatchRecordByUserNo(userNo) {

    $.ajax({
        url:"/toiletCat/catchRecord/getCatchRecordListByUserNo.action",
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

                str += "<div id='userCatchRecord"+list[i]["id"]+"' class='row'>";
                str += "    <div class='panel-body' >";
                str += "        <div class='catch-toy-img'>";
                str += "            <img src='" + list[i]["toyImg"] + "' />";
                str += "        </div>";
                var newDate = new Date();
                newDate.setTime(list[i]["catchTime"]);
                str += "        <div class='catch-toy-time' ><span>" +newDate.format('yyyy-MM-dd h:m:s') + "</span></div>";
                str += "        <div class='catch-toy-result' >"
                if(list[i]["catchResult"] == "1") {
                    str += "        <span>抓取成功</span>";
                } else {
                    str += "        <span>抓取失败</span>";
                }
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

            $("#catchRecord").append(str);
        }

    });
}

function returnMethod() {
    window.location.href="/toiletCat/user/userIndex.html?type=gameRoom&userNo="+userNo + "&userName="+userName+"&userImg="+userImg+"&userCoin="+userCoin+"&invitationCode="+invitationCode;
}