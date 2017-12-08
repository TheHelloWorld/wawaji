var returnUrl = "/toiletCat/back/toy/toy.jsp";

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");
        var toyNo = getQueryString("toyNo");
        var getUrl = "/toiletCat/toy/getToyByIdAndToyNo.action";
        getDataByInfo(getUrl, id, toyNo);
    }
});

// 保存或修改
function updateOrSaveToy() {

    if(getQueryString("type") == "add") {
        var saveUrl = "/toiletCat/toy/addToy.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/toiletCat/toy/updateToyByIdAndToyNo.action";
        updateThis(updateUrl, returnUrl);
    }
}

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUrl;
}