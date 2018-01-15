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

    var indexBodyDivHeight = $(".index-body-div").height();

    var addHeight = $("#main").height() + $("#banner-box").height() + ($(".default-height").height() * 2);

    $(".index-body-div").scroll(function(){

        if ($(this).scrollTop() + indexBodyDivHeight >= addHeight) {
            nextPage();
            addHeight = $("#main").height() + $("#banner-box").height() + ($(".default-height").height() * 2);
        }
    });

});

// 获得当前数据总数量和分页数据
function getTotalCountAndPageSizeByUserNo() {
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

            totalCount = data["totalCount"];

            pageSize = data["pageSize"];

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

                str += "<div id='userCatchRecord"+list[i]["id"]+"' class='catch-toy-row' >";

                str += "    <div class='catch-toy-div' >";
                str += "        <div class='catch-toy-img'>";
                str += "            <img src='" + list[i]["toyImg"] + "' />";
                str += "        </div>";
                var newDate = new Date();
                newDate.setTime(list[i]["catchTime"]);
                str += "        <div class='catch-toy-time' ><span>" + newDate.format('yyyy-MM-dd hh:mm:ss') + "</span>";
                str += "            <div class='catch-toy-result' >";
                if(list[i]["catchResult"] == "1") {
                    str += "            <span>抓取成功</span>";
                } else {
                    str += "            <span>抓取失败</span>";
                }
                str += "            </div>";
                str += "        </div>";
                str += "    </div>";
                str += "</div>";

            }

            $("#catchRecord").append(str);

            $(".catch-toy-row").height($(".catch-toy-div").height());
        }

    });
}

// 返回用户主页
function returnMethod() {
    window.location.href = "/toiletCat/user/userIndex.html?type=gameRoom&userNo" + userNo;
}