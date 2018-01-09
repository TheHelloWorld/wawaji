var returnUrl = "/toiletCat/back/toiletCatConfig/toiletCatConfig.jsp";

// 返回玩具列表页
function cancelThis() {
    window.location.href = returnUrl;
}

$(function(){
    // 判断当前类型为修改
    if(getQueryString("type") == "update") {

        var id = getQueryString("id");

        getDataByInfo(id);
    }
});

// 根据id获得数据信息
function  getDataByInfo(id) {
    $.ajax({
        url:"/toiletCat/api/toiletCatConfig/getToiletCatConfigById.action",
        type:"POST",
        async:false,
        data:{
            id:id
        },
        success:function(data){

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            var info = data["result"];

            if(typeof(info) == "string"){
                info = eval("("+info+")");
            }

            $("#dataInfo").find("span").each(function() {

                var col = $(this).attr("name");
                $("#"+col).val(info[col])
            });

            $("#configKey").attr("disabled", true);
        }
    });
}

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