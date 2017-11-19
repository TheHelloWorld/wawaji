// 每页数据数
var pageSize = 0;

var step = 5;

var nowPage = 1;

$(function(){
    // 获得总页数和总数量
    var countAndPageSizeUrl = "/wawaji/deliver/getDeliverTotalCountAndPageSize.action";
    getTotalCountAndPageSize(countAndPageSizeUrl);

    // 分页获得所有记录
    getAllDeliverByPage(nowPage);

    // 初始化页码
    initPage(totalPage, step);
});

// 公共展示方法
function getAllDeliverByPage(startPage) {

    startPage = (startPage - 1) * pageSize;

    $.ajax({
        url:"/wawaji/deliver/getAllDeliverByPage.action",
        type:"POST",
        async:false,
        data:{
            startPage:startPage
        },
        success:function(data) {

            $("#dataBody").html("");

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(totalPage == 0){
                $("#dataDiv").hide();
                return;
            }

            var list = data["list"];
            var str = "";

            for(var i = 0;i<list.length;i++) {

                str += "<tr id=tr"+list[i]["id"]+" class='even gradeA'>";

                $("#titleColumn").find("lable").each(function() {

                    var col = $(this).attr("name");

                    var td = "<td>";

                    if(col == "operation") {

                        var dataStr = JSON.stringify(list[i]);

                        td += "<a href='javascript:void(0);' onclick=updateThis('"+dataStr+"')>修改</a>";

                    } else if(col == "deliverStatus") {

                        if(list[i][col] == "0") {
                            td += "待发货";
                        } else if(list[i][col] == "1") {
                            td += "已发货";
                        } else if(list[i][col] == "2") {
                            td += "已签收";
                        }
                    } else {
                        //如果数据为空则写无
                        if((list[i][col] == undefined) || (list[i][col] == null) || (list[i][col] == "null")) {
                            td += "无";
                        } else {
                            td += list[i][col] + "";
                        }
                    }

                    td += "</td>";

                    str += td;
                });
                str += "</tr>";
            }

            $("#dataBody").append(str);
        }

    });
}

function getPage(page){
    nowPage = getPageByNum(nowPage, page, totalPage, step);
    getAllDeliverByPage(nowPage);
}

function nextPage(){
    nowPage = nextPageNum(nowPage, totalPage, step);
    getAllDeliverByPage(nowPage);
}

//上一页
function lastPage(){
    nowPage = lastPageNum(nowPage, totalPage, step);
    getAllDeliverByPage(nowPage);
}

// 修改元素
function updateThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];
    var userNo = dataParam["userNo"];

    window.location.href = "/wawaji/deliver/deliverDetailPage.jsp?id="+id+"&userNo="+userNo+"";
}