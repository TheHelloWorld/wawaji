var returnUrl = "/toiletCat/back/toiletCatConfig/toiletCatConfig.jsp";

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUrl;
}

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");

        var getUrl = "/toiletCat/api/toiletCatConfig/getMachineByIdAndMachineNo.action";
        getDataByInfo(getUrl, id, machineNo);
    }
});

// 储存或修改
function updateOrSaveConfig() {

    if(getQueryString("type") == "add") {
        var saveUrl = "/toiletCat/api/toiletCatConfig/addToiletCatConfig.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/toiletCat/api/toiletCatConfig/updateToiletCatConfig.action";
        updateThis(updateUrl, returnUrl);
    }

}