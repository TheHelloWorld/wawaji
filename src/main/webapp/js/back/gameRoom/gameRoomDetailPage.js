var returnUrl = "/wawaji/back/gameRoom/gameRoom.jsp";

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUrl;
}

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");
        console.info(id);
        var gameRoomNo = getQueryString("gameRoomNo");

        var getUrl = "/wawaji/gameRoom/getGameRoomByGameRoomNoAndId.action";
        getDataByInfo(getUrl, id, gameRoomNo);
    }
});

// 储存或修改
function updateOrSaveGameRoom() {

    if(getQueryString("type") == "add") {
        var saveUrl = "/wawaji/gameRoom/addGameRoom.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/wawaji/gameRoom/updateGameRoomByGameRoomNoAndId.action";
        updateThis(updateUrl, returnUrl);
    }

}