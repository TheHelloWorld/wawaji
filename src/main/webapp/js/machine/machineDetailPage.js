var returnUrl = "/wawaji/machine/machine.jsp";

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUrl;
}

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");
        console.info(id);
        var machineNo = getQueryString("machineNo");

        var getUrl = "/wawaji/machine/getMachineByIdAndMachineNo.action";
        getDataByInfo(getUrl, id, machineNo);
    }
});

// 储存或修改
function updateOrSaveMachine() {

    if(getQueryString("type") == "save") {
        var saveUrl = "/wawaji/machine/addMachine.action";
        saveThis(saveUrl, returnUrl);
    } else {
        var updateUrl = "/wawaji/machine/updateMachineByIdAndMachineNo.action";
        updateThis(updateUrl, returnUrl);
    }

}