// 每页数据数
var userNo = "";

var nowPage = 1;

$(function() {
    // 获得用户编号
    userNo = getQueryString("userNo");

    // 获得总页数和总数量
    getTotalCountAndPageSizeByUserNo();
    
    // 获得当前用户所有消费记录
    getAllUserSpendRecordByUserNo();

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
                alert(data["result"]);
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

// 获得当前用户所有战利品
function getAllUserSpendRecordByUserNo() {

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
                alert(data["result"]);
                return;
            }

            var list = data["result"];
            var str = "";

            for(var i = 0; i<list.length; i++) {

                if(i % 2 == 0) {
                    str += "<div class='row' style='margin-bottom: 5px'>";

                }

                if(i % 2 == 0) {
                    str += "<div class='machine-col-xs-6-left' onclick='toUserToyDetail("+list[i]["id"]+")' >";
                } else if(i % 2 != 0) {
                    str += "<div class='machine-col-xs-6-right' onclick='toUserToyDetail("+list[i]["id"]+")' >";
                }

                str += "    <div class='machine-panel panel-info'>";
                str += "        <div class='panel-body'>";
                str += "            <div class='toy-img index-img'>"
                str += "                <img height='100px' width=100% src='" + list[i]["toyImg"] + "' class='index-img' />";
                str += "            </div>";
                str += "            <div style='margin-bottom: 2px'><span>" + list[i]["toyName"] + "</span></div>";
                var newDate = new Date();
                newDate.setTime(list[i]["createTime"]);
                str += "            <div style='margin-bottom: 2px'><span>" +newDate.format('yyyy-MM-dd h:m') + "</span></div>";
                str += "        </div>";
                str += "    </div>";
                str += "</div>";

                if(i % 2 != 0) {
                    str += "</div>";
                }
            }
            $("#userToy").append(str);
        }
    });
}