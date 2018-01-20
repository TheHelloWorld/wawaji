
var nowPage = 1;

var otherUserNo = "";

var gameRoomNo = "";

$(function() {

    // 其他用户编号
    otherUserNo = getQueryString("otherUserNo");
    // 游戏房间编号
    gameRoomNo = getQueryString("gameRoomNo");

    // 用户编号
    checkSession();

    // 获得总页数和总数量
    getTotalCountAndPageSizeByUserNo();
    
    // 获得当前用户所有战利品
    getAllUserToyByUserNo(nowPage);

    var indexBodyDivHeight = $(".index-body-div").height();

    var addHeight = $("#userToy").height() + ($(".default-height").height() * 2);

    $(".index-body-div").scroll(function(){

        if ($(this).scrollTop() + indexBodyDivHeight >= addHeight) {
            nextPage();
            addHeight = $("#userToy").height() + ($(".default-height").height() * 2);
        }
    });

});

// 获得总页数和总数量
function getTotalCountAndPageSizeByUserNo() {
    $.ajax({
        url:"/toiletCat/api/userToy/countUserToyByUserNo.action",
        type:"POST",
        async:false,
        data:{
            userNo:otherUserNo
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

                totalPage = parseInt(totalCount / pageSize) +1;
            }
        }
    });
}

// 获得当前用户所有战利品
function getAllUserToyByUserNo(nowPage) {

    var startPage = (nowPage -1 ) * pageSize;

    $.ajax({
        url:"/toiletCat/api/userToy/getUserToyListByUserNo.action",
        type:"POST",
        async:false,
        data:{
            startPage:startPage,
            userNo:otherUserNo
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

                if(i % 2 == 0) {
                    str += "<div class='row' style='margin-bottom: 2%'>";
                }

                str += "<div class='toiletCat-col-xs-6' >";
                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img'>"
                str += "                <img height='100px' width=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
                str += "            </div>";
                str += "            <div style='margin-bottom: 1%'><span>" + list[i]["toyName"] + "</span></div>";
                var newDate = new Date();
                newDate.setTime(list[i]["createTime"]);
                str += "            <div style='margin-bottom: 1%'><span>" +newDate.format('yyyy-MM-dd h:m') + "</span></div>";
                str += "        </div>";
                str += "    </div>";
                str += "</div>";

                if(i % 2 != 0 || i == list.length - 1) {
                    str += "</div>";
                }
            }
            $("#userToy").append(str);
        }
    });
}

function returnMethod() {

    window.location.href = "/toiletCat/game/game.html?userNo=" + userNo + "&gameRoomNo=" + gameRoomNo;

}

function nextPage() {
    nowPage ++;
    if(nowPage <= totalPage) {
        getAllUserToyByUserNo(nowPage);
    } else {
        nowPage = totalPage;
    }
}
