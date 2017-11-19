var returnUtl = "/wawaji/toy/toy.jsp";

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUtl;
}

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");
        var toyNo = getQueryString("toyNo");
        var getUrl = "/wawaji/toy/getToyByIdAndToyNo.action";
        getDataByInfo(getUrl, id, toyNo);
    }
});

function updateOrSaveToy() {

    if(getQueryString("type") == "save") {
        var saveUrl = "/wawaji/toy/addToy.action";
        saveThis(saveUrl, returnUtl);
    } else {
        var updateUrl = "/wawaji/toy/updateToyByIdAndToyNo.action";
        updateThis(updateUrl, returnUtl);
    }

}