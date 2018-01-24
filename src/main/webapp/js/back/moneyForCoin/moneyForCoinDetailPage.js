var returnUrl = "/toiletCat/back/moneyForCoin/moneyForCoin.jsp";

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");
        var getUrl = "/toiletCat/api/moneyForCoin/getMoneyForCoinById.action";
        getDataByInfo(getUrl, id, "");
    }
});

// 保存或修改
function updateOrSaveBannerImg() {

    if(getQueryString("type") == "add") {
        var saveUrl = "/toiletCat/api/moneyForCoin/addMoneyForCoin.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/toiletCat/api/moneyForCoin/updateMoneyForCoin.action";
        updateThis(updateUrl, returnUrl);
    }
}

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUrl;
}