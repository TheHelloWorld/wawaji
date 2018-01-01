// 每页数据数
var pageSize = 0;

var step = 5;

var nowPage = 1;

// 展示url
var showUrl = "/toiletCat/api/machine/getAllMachineByPage.action";

$(function(){
    // 获得总页数和总数量
    var countAndPageSizeUrl = "/toiletCat/api/machine/getMachineTotalCountAndPageSize.action";
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
    window.location.href="/toiletCat/back/machine/machineDetailPage.jsp?type=add";
}

// 删除行元素
function deleteThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];
    var machineNo = dataParam["machineNo"];

    $.ajax({
        url:"/toiletCat/api/machine/deleteMachineByIdAndToyNo.action",
        type:"POST",
        async:false,
        data:{
            id:id,
            machineNo:machineNo
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
    var machineNo = dataParam["machineNo"];

    window.location.href = "/toiletCat/back/machine/machineDetailPage.jsp?type=update&id="+id+"&machineNo="+machineNo+"";
}