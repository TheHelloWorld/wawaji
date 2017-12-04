// 每页数据数
var pageSize = 0;

var step = 5;

var nowPage = 1;

// 展示url
var showUrl = "/wawaji/gameRoom/getAllGameRoomByPage.action";

$(function(){
    // 获得总页数和总数量
    var countAndPageSizeUrl = "/wawaji/gameRoom/getGameRoomTotalCountAndPageSize.action";
    getTotalCountAndPageSize(countAndPageSizeUrl);

    // 分页获得所有记录
    getAllByPage(showUrl, nowPage);

    // 初始化页码
    initPage(totalPage, step);
});



function getPage(page) {
    nowPage = getPageByNum(nowPage, page, totalPage, step);
    getAllByPage(showUrl, nowPage);

}

function nextPage() {
    nowPage = nextPageNum(nowPage, totalPage, step);
    getAllByPage(showUrl, nowPage);

}

//上一页
function lastPage() {
    nowPage = lastPageNum(nowPage, totalPage, step);
    getAllByPage(showUrl, nowPage);
}

function addToyPage() {
    window.location.href="/wawaji/back/gameRoom/gameRoomDetailPage.jsp?type=add";
}


// 修改元素
function updateThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];
    var gameRoomNo = dataParam["gameRoomNo"];

    window.location.href = "/wawaji/back/gameRoom/gameRoomDetailPage.jsp?type=update&id="+id+"&gameRoomNo="+gameRoomNo+"";
}