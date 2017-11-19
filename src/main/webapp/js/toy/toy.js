// 开始页码
var startPage = 0;

// 总数据数
var totalCount = 0;

// 每页数据数
var pageSize = 0;

var step = 5;

var nowPage = 1;


$(function(){
    // 获得总页数和总数量
    var countAndPageSizeUrl = "/wawaji/toy/getTotalCountAndPageSize.action";
    getTotalCountAndPageSize(countAndPageSizeUrl);

    // 分页获得所有记录
    var showUrl = "/wawaji/toy/getAllToyByPage.action";
    getAllByPage(showUrl);

    // 初始化页码
    initPage(totalPage,step);
});



function getPage(page){
    nowPage = getPageByNum(nowPage,page,totalPage,step);

}

function nextPage(){
    nowPage = nextPageNum(nowPage,totalPage,step);

}

//上一页
function lastPage(){
    nowPage = lastPageNum(nowPage,totalPage,step);
}

function addToyPage() {
    window.location.href="/wawaji/toy/toyDetailPage.jsp?type=save";
}

// 删除行元素
function deleteThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];
    var toyNo = dataParam["toyNo"];

    $.ajax({
        url:"/wawaji/toy/deleteToyByIdAndToyNo.action",
        type:"POST",
        async:false,
        data:{
            id:id,
            toyNo:toyNo
        },
        success:function(data){
            // 若后台操作成功则删除当前行
            if(data == "success") {
                $("#tr"+id).remove();
            }
        }
    });
}

// 修改元素
function updateThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];
    var toyNo = dataParam["toyNo"];

    window.location.href = "/wawaji/toy/toyDetailPage.jsp?type=update&id="+id+"&toyNo="+toyNo+"";
}