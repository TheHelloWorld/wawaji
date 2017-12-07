var returnUrl = "/wawaji/back/bannerImg/bannerImg.jsp";

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");
        var getUrl = "/wawaji/bannerImg/getBannerImgById.action";
        getDataByInfo(getUrl, id, "");
    }
});

// 保存或修改
function updateOrSaveBannerImg() {

    if(getQueryString("type") == "add") {
        var saveUrl = "/wawaji/bannerImg/addBannerImg.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/wawaji/bannerImg/updateBannerImg.action";
        updateThis(updateUrl, returnUrl);
    }
}

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUrl;
}