var json = {};

// 返回玩具列表页
function cancelThis() {
    window.location.href = "/wawaji/machine/machine.jsp";
}

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");
        var toyNo = getQueryString("toyNo");
        $.ajax({
            url:"/wawaji/machine/getMachineByIdAndMachineNo.action",
            type:"POST",
            async:false,
            data:{
                id:id,
                toyNo:toyNo
            },
            success:function(data){

                var machine = eval("(" + data + ")");

                $("#machineInfo").find("span").each(function() {

                    var col = $(this).attr("name");
                    $("#"+col).val(machine[col])
                });
            }
        });
    }
});

function updateOrSaveToy() {

    if(getQueryString("type") == "save") {
        saveMachine();
    } else {
        updateMachine();
    }

}

// 储存机器
function saveMachine() {

    $("#machineInfo").find("span").each(function() {

        var col = $(this).attr("name");
        if(col != "id") {
            json[col] = $("#"+col).val();
        }
    });
    var machineStr = JSON.stringify(json);

    $.ajax({
        url:"/wawaji/machine/addMachine.action",
        type:"POST",
        async:false,
        data:{
            machineStr:machineStr
        },
        success:function(data){

            console.info(data);
            if(data == "success") {
                window.location.href="/wawaji/machine/machine.jsp";
            }
        }

    });
}

// 修改机器
function updateMachine() {

    $("#machineInfo").find("span").each(function() {
        var col = $(this).attr("name");
        json[col] = $("#"+col).val();
    });
    var machineStr = JSON.stringify(json);

    $.ajax({
        url:"/wawaji/machine/updateMachineByIdAndMachineNo.action",
        type:"POST",
        async:false,
        data:{
            machineStr:machineStr
        },
        success:function(data){

            console.info(data);
            if(data == "success") {
                window.location.href="/wawaji/machine/machine.jsp";
            }
        }

    });
}