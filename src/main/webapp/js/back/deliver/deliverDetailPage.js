// 返回Url
var returnUtl = "/toiletCat/back/deliver/deliver.jsp";

$(function(){
    // 判断当前类型为修改
    var id = getQueryString("id");
    var userNo = getQueryString("userNo");
    var getUrl = "/toiletCat/api/deliver/getDeliverByIdAndUserNo.action";
    getDataByInfo(getUrl, id, userNo);
});

// 修改
function updateDeliver() {
    var updateUrl = "/toiletCat/api/deliver/updateDeliverMsgByIdAndUserNo.action";
    updateThis(updateUrl, returnUtl);
}

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUtl;
}

