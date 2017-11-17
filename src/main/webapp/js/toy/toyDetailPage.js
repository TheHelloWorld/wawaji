var json = {};

// 返回玩具列表页
function cancelThis() {
    window.location.href = "/wawaji/toy/toy.jsp";
}



$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");
        var toyNo = getQueryString("toyNo");
        $.ajax({
            url:"/wawaji/toy/getToyByIdAndToyNo.action",
            type:"POST",
            async:false,
            data:{
                id:id,
                toyNo:toyNo
            },
            success:function(data){

                var toy = eval("(" + data + ")");

                $("#toyInfo").find("span").each(function() {

                    var col = $(this).attr("name");
                    $("#"+col).val(toy[col])
                });
            }
        });
    }
});

function updateOrSaveToy() {

    if(getQueryString("type") == "save") {
        saveToy();
    } else {
        updateToy();
    }

}

// 储存玩具
function saveToy() {

    $("#toyInfo").find("span").each(function() {

        var col = $(this).attr("name");
        if(col != "id") {
            json[col] = $("#"+col).val();
        }
    });
    json["toyImg"] = "img";
    var toyStr = JSON.stringify(json);

    $.ajax({
        url:"/wawaji/toy/addToy.action",
        type:"POST",
        async:false,
        data:{
            toyStr:toyStr
        },
        success:function(data){

            console.info(data);
            if(data == "success") {
                window.location.href="/wawaji/toy/toy.jsp";
            }
        }

    });
}

// 修改玩具
function updateToy() {

    $("#toyInfo").find("span").each(function() {
        var col = $(this).attr("name");
        json[col] = $("#"+col).val();
    });
    json["toyImg"] = "img";
    var toyStr = JSON.stringify(json);

    $.ajax({
        url:"/wawaji/toy/updateToyByIdAndToyNo.action",
        type:"POST",
        async:false,
        data:{
            toyStr:toyStr
        },
        success:function(data){

            console.info(data);
            if(data == "success") {
                window.location.href="/wawaji/toy/toy.jsp";
            }
        }

    });
}