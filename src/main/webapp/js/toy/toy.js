// 开始页码
var startPage = 0;

// 总数据数
var totalCount = 0;

// 每页数据数
var pageSize = 0;

var step = 5;

var nowPage = 1;

$(function(){
    getTotalCountAndPageSize();
    getAllToyByPage();
    console.info(totalPage);
    initPage(totalPage,step);
});

// 根据分页获得所有玩具数据
function getAllToyByPage() {
    $.ajax({
        url:"/wawaji/toy/getAllToyByPage.action",
        type:"POST",
        async:false,
        data:{
            startPage:startPage
        },
        success:function(data) {

            data = eval("(" + data + ")");

            var list = data["list"];
            var str = "";
            //console.info("length:"+s.length);
            for(var i = 0;i<list.length;i++) {

                str += "<tr class='even gradeA'>";
                $("#titleColumn").find("lable").each(function() {

                    var col = $(this).attr("name");

                    var td = "<td>";

                    if(col == "operation") {

                        td += "修改 | 删除";

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

// 获得当前玩具总数量和分页数据
function getTotalCountAndPageSize() {
    $.ajax({
        url:"/wawaji/toy/getTotalCountAndPageSize.action",
        type:"POST",
        async:false,
        data:{
            startPage:startPage
        },
        success:function(data){

            data = eval("(" + data + ")");

            totalCount = data["totalCount"];

            pageSize = data["pageSize"];

            if(totalCount <= pageSize) {
                totalPage = 1;
            } else if(totalCount % pageSize == 0) {
                totalPage = totalCount/pageSize;
            } else {
                totalPage = parseInt(totalCount/pageSize);
            }


        }

    });
}

function getPage(page){
    //console.info("now:"+nowpage);
    nowPage = getPageByNum(nowPage,page,totalPage,step);

}

function nextPage(){
    nowPage = nextPageNum(nowPage,totalPage,step);

}

//上一页
function lastPage(){
    nowPage = lastPageNum(nowPage,totalPage,step);
}