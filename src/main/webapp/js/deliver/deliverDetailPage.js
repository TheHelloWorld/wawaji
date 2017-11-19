var returnUtl = "/wawaji/deliver/deliver.jsp";

$(function(){
    // 判断当前类型为修改
    var id = getQueryString("id");
    var userNo = getQueryString("userNo");
    var getUrl = "/wawaji/deliver/getDeliverByIdAndUserNo.action";
    getDataByInfo(getUrl, id, userNo);
});

// 修改
function updateDeliver() {
    var updateUrl = "/wawaji/deliver/updateDeliverMsgByIdAndUserNo.action";
    updateThis(updateUrl, returnUtl);
}

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUtl;
}

