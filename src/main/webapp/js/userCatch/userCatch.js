// 每页数据数
var pageSize = 0;

var nowPage = 1;

$(function() {
    // 用户编号
    checkSession();

    // 获得所有用户可见游戏房间数量及分页
    getTotalCountAndPageSizeByUserNo();

    // 分页获得当前用户所有抓取记录
    getAllUserCatchRecordByUserNo(userNo);

});

// 获得当前数据总数量和分页数据
function getTotalCountAndPageSizeByUserNo() {
    $.ajax({
        url:"/toiletCat/catchRecord/countCatchRecordByUserNo.action",
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
                toiletCatMsg(data["result"]);
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

// 分页获得当前用户所有抓取记录
function getAllUserCatchRecordByUserNo(userNo) {

    var startPage = (nowPage - 1) * pageSize;

    $.ajax({
        url:"/toiletCat/catchRecord/getCatchRecordListByUserNo.action",
        type:"POST",
        async:false,
        data:{
            userNo:userNo,
            startPage:startPage
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
    window.location.href="/toiletCat/user/userIndex.html?type=gameRoom&userNo"+userNo;
}