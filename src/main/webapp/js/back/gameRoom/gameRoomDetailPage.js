var returnUrl = "/toiletCat/back/gameRoom/gameRoom.jsp";

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUrl;
}

$(function(){

    // 获得所有玩具信息
    getAllAvailableToy();
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");

        var gameRoomNo = getQueryString("gameRoomNo");

        var getUrl = "/toiletCat/api/gameRoom/getGameRoomByGameRoomNoAndId.action";
        getDataByInfo(getUrl, id, gameRoomNo);
    }
});

// 储存或修改
function updateOrSaveGameRoom() {

    if(getQueryString("type") == "add") {
        var saveUrl = "/toiletCat/api/gameRoom/addGameRoom.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/toiletCat/api/gameRoom/updateGameRoomByGameRoomNoAndId.action";
        updateThis(updateUrl, returnUrl);
    }

}