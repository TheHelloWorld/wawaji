// 每页数据数
var userNo = "";

var nowPage = 1;

var type = "";

$(function() {
    // 获得用户编号
    userNo = getQueryString("userNo");

    type = getQueryString("type");

    // 获得总页数和总数量
    getTotalCountAndPageSizeByUserNo();
    
    // 获得当前用户所有消费记录
    getAllUserSpendRecordByUserNo(nowPage);

    var indexBodyDivHeight = $(".index-body-div").height();

    var addHeight = $("#userSpend").height() + ($(".default-height").height() * 2);

    $(".index-body-div").scroll(function(){

        if ($(this).scrollTop() + indexBodyDivHeight >= addHeight) {
            nextPage();
            addHeight = $("#userSpend").height() + ($(".default-height").height() * 2);
        }
    });

});

// 获得总页数和总数量
function getTotalCountAndPageSizeByUserNo() {
    $.ajax({
        url:"/toiletCat/api/userSpendRecord/countUserSpendRecordByUserNo.action",
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

                totalPage = parseInt(totalCount / pageSize);
            }
        }
    });
}

// 获得当前用户所有消费记录
function getAllUserSpendRecordByUserNo(nowPage) {

    var startPage = (nowPage -1 ) * pageSize;

    $.ajax({
        url:"/toiletCat/api/userSpendRecord/getUserSpendRecordByUserNo.action",
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

                var newDate = new Date();
                newDate.setTime(list[i]["tradeTime"]);

                str += "<div id='userSpendRecord"+list[i]["id"]+"' class='user-spend-row' >";

                str += "		<div class='user-spend-div'>";
                str += "			<div class='user-spend-title'>";
                str += "                <p>" + list[i]["tradeTypeDesc"] + "</p>";
                str += "                <p>" + newDate.format('yyyy-MM-dd hh:mm:ss') + "</p>";
                str += "			</div>";
                str += "			<div class='user-spend-coin'>";
                str += "                <img width='10%' align='absmiddle' src='/image/background/coin_img.png'>";
                str += "                <span style='height: 100%;margin-bottom: 5%'>&nbsp;" + list[i]["coin"] + "</span>";
                str += "			</div>";
                str += "			<div class='user-spend-result'>";
                if(list[i]["tradeStatus"] == "10") {
                    str += "            <span>成功</span>";
                } else {
                    str += "            <span>失败</span>";
                }
                str += "			</div>";
                str += "		</div>";
                str += "</div>";
            }
            $("#userSpend").append(str);
        }
    });
}

function nextPage() {
    nowPage ++;
    if(nowPage <= totalPage) {
        getAllUserSpendRecordByUserNo(nowPage);
    } else {
        nowPage = totalPage;
    }
}

function returnMethod() {
    window.location.href="/toiletCat/user/userIndex.html?type=" + type + "&userNo" + userNo;
}