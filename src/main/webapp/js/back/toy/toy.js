// 每页数据数
var pageSize = 0;

var step = 5;

var nowPage = 1;

// 展示url
var showUrl = "/toiletCat/api/toy/getAllToyByPage.action";

$(function() {
    // 获得总页数和总数量
    var countAndPageSizeUrl = "/toiletCat/api/toy/getTotalCountAndPageSize.action";
    getTotalCountAndPageSize(countAndPageSizeUrl);

    // 分页获得所有记录
    getAllByPage(showUrl, nowPage);

    // 初始化页码
    initPage(totalPage,step);
});



function getPage(page) {
    nowPage = getPageByNum(nowPage,page,totalPage,step);
    getAllByPage(showUrl, nowPage);

}

function nextPage() {
    nowPage = nextPageNum(nowPage,totalPage,step);
    getAllByPage(showUrl, nowPage);

}

//上一页
function lastPage() {
    nowPage = lastPageNum(nowPage,totalPage,step);
    getAllByPage(showUrl, nowPage);
}

function addToyPage() {
    window.location.href="/toiletCat/back/toy/toyDetailPage.jsp?type=add";
}

// 删除行元素
function deleteThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];
    var toyNo = dataParam["toyNo"];

    $.ajax({
        url:"/toiletCat/api/toy/deleteToyByIdAndToyNo.action",
        type:"POST",
        async:false,
        data:{
            id:id,
            toyNo:toyNo
        },
        success:function(data){

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            $("#tr"+id).remove();
        }
    });
}

// 修改元素
function updateThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];
    var toyNo = dataParam["toyNo"];

    window.location.href = "/toiletCat/back/toy/toyDetailPage.jsp?type=update&id="+id+"&toyNo="+toyNo+"";
}