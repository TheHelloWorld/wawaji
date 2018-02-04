
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
    getAllUserToyHandleByUserNo();

    var indexBodyDivHeight = $(".index-body-div").height();

    var addHeight = $("#userToyHandle").height() + ($(".default-height").height() * 2);

    $(".index-body-div").scroll(function(){

        if ($(this).scrollTop() + indexBodyDivHeight >= addHeight) {
            nextPage();
            addHeight = $("#userToyHandle").height() + ($(".default-height").height() * 2);
        }
    });

});

// 获得总页数和总数量
function getTotalCountAndPageSizeByUserNo() {
    $.ajax({
        url:"/toiletCat/api/userToyHandle/countUserToyHandleByUserNo.action",
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
function getAllUserToyHandleByUserNo() {

    var startPage = (nowPage -1 ) * pageSize;

    $.ajax({
        url:"/toiletCat/api/userToyHandle/getUserToyHandleListByUserNo.action",
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

                str += "<div class='toiletCat-col-xs-6' onclick='toUserToyHandleDetail("+list[i]["id"]+")' >";
                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img' style='text-align: center'>";
                str += "                <img height='100px' maxwidth=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
                str += "            </div>";
                str += "            <div style='margin-bottom: 1%'><span>" + list[i]["toyName"] + "</span></div>";

                var msg = "";

                if(list[i]["choiceType"] == 1) {
                    msg = "已兑换" + list[i]["toyForCoin"] + "个马桶币";
                } else if(list[i]["choiceType"] == 2) {

                    if(list[i]["deliverStatus"] == 0) {
                        msg = "待发货";
                    } else if(list[i]["deliverStatus"] == 1) {
                        msg = "已发货";
                    }
                }

                str += "            <div style='margin-bottom: 1%'><span>" + msg+"</span></div>";
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
function toUserToyHandleDetail(id) {

    window.location.href = "/toiletCat/userToy/userToyHandleDetailPage.html?type=update&userNo=" + userNo + "&id=" + id;
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
        getAllUserToyHandleByUserNo();
    } else {
        nowPage = totalPage;
    }
}

function toUnHandlePage() {
    
    window.location.href = "/toiletCat/userToy/userToy.html?nowType=login&userNo=" + userNo;

}
