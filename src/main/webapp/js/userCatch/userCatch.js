// 每页数据数
var pageSize = 0;

var nowPage = 1;

var totalPage = 0;

$(function() {
    // 用户编号
    checkSession();

    // 获得所有用户可见游戏房间数量及分页
    getTotalCountAndPageSizeByUserNo();

    // 分页获得当前用户所有抓取记录
    getAllUserCatchRecordByUserNo(userNo);

    var indexBodyDivHeight = $(".index-body-div").height();

    var addHeight = $("#catchRecord").height();

    $(".index-body-div").scroll(function(){

        if ($(this).scrollTop() + indexBodyDivHeight >= addHeight) {

            nextPage(userNo);
            addHeight = $("#catchRecord").height();
        }
    });

});

// 获得当前数据总数量和分页数据
function getTotalCountAndPageSizeByUserNo() {

    console.info("totalCount:" + 123123);

    $.ajax({
        url:"/toiletCat/api/catchRecord/countCatchRecordByUserNo.action",
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
                toiletCatMsg(data["result"], null);
                return;
            }

            var totalCount = data["totalCount"];

            pageSize = data["pageSize"];

            console.info("totalCount:" + totalCount);

            if(totalCount <= pageSize) {

                totalPage = 1;

            } else if(totalCount % pageSize == 0) {

                totalPage = totalCount / pageSize;

            } else {

                totalPage = parseInt(totalCount / pageSize) + 1;
            }
        }
    });
}

function nextPage(userNo) {
    console.info(nowPage);
    console.info(totalPage);

    nowPage ++;
    if(nowPage <= totalPage) {
        getAllUserCatchRecordByUserNo(userNo);
    } else {
        nowPage = totalPage;
    }
}

// 分页获得当前用户所有抓取记录
function getAllUserCatchRecordByUserNo(userNo) {

    var startPage = (nowPage - 1) * pageSize;

    $.ajax({
        url:"/toiletCat/api/catchRecord/getCatchRecordListByUserNo.action",
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
                toiletCatMsg(data["result"], null);
                return;
            }

            var list = data["result"];
            var str = "";

            for(var i = 0; i<list.length; i++) {

                str += "<div id='userCatchRecord"+list[i]["id"]+"' class='catch-toy-row' style='height: " + $('.my-body').height()/5 + "px;' >";

                str += "    <div class='catch-toy-div' >";
                str += "        <div class='catch-toy-img'>";
                str += "            <img src='" + list[i]["toyImg"] + "' />";
                str += "        </div>";
                str += "        <div class='catch-toy-time' >";
                str += "            <div class='catch-toy-result' >";
                if(list[i]["catchResult"] == "1") {
                    str += "            <img src='/image/userCatch/catch_success.png'>";
                } else {
                    str += "            <img src='/image/userCatch/catch_fail.png'>";
                }
                str += "            </div>";
                str += "            <div>" + list[i]["toyName"] + "</div>";
                var newDate = new Date();
                newDate.setTime(list[i]["catchTime"]);
                str += "            <div>" + newDate.format('yyyy-MM-dd hh:mm:ss') + "</div>";
                str += "        </div>";
                str += "    </div>";
                str += "</div>";

            }

            $("#catchRecord").append(str);
        }

    });
}

// 返回用户主页
function returnMethod() {
    window.location.href = "/toiletCat/user/userIndex.html?type=gameRoom&userNo" + userNo;
}