
var nowPage = 1;

// 类型
var type= "";

$(function() {

    type = getQueryString("type");

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
            userNo:userNo
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

                str += "<div class='toiletCat-col-xs-6' onclick='toUserToyDetail("+list[i]["toyNo"]+")' >";
                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img' style='text-align: center'>";
                str += "                <img height='100px' maxwidth=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
                str += "            </div>";
                str += "            <div style='margin-bottom: 1%'><span>" + list[i]["toyName"] + "</span></div>";
                str += "            <div style='margin-bottom: 1%'><span>" + list[i]["unHandleNum"] + "/"+list[i]["deliverNum"]+"</span></div>";
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

// 修改元素
function toUserToyDetail(toyNo) {

    window.location.href = "/toiletCat/userToy/userToyDetailPage.html?type=update&userNo=" + userNo + "&toyNo=" + toyNo;
}

function returnMethod() {
    // 根据类型返回不同的首页
    if(type= "gameRoom") {

        window.location.href = "/toiletCat/gameRoom/gameRoom.html?nowType=login&userNo=" + userNo;

    } else if(type= "machineRoom"){

        window.location.href = "/toiletCat/machineRoom/machineRoom.html?nowType=login&&userNo=" + userNo;

    }
}

function nextPage() {
    nowPage ++;
    if(nowPage <= totalPage) {
        getAllUserToyByUserNo(nowPage);
    } else {
        nowPage = totalPage;
    }
}
