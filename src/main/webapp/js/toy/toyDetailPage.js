// 返回玩具列表页
function cancelThis() {
    window.location.href = "/wawaji/toy/toy.jsp";
}


$(function(){
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

                console.info(toy);
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

    }

}

// 储存玩具
function saveToy() {

    $.ajax({
        url:"/wawaji/toy/addToy.action",
        type:"POST",
        async:false,
        data:{
            toyNo:$("#toyNo").val(),
            toyForCoin:$("#toyForCoin").val(),
            toyDesc:$("#toyDesc").val(),
            toyNowCoin:$("#toyNowCoin").val(),
            toyOriginCoin:$("#toyOriginCoin").val(),
            toyCost:$("#toyCost").val(),
            toyImg:"q1"

        },
        success:function(data){

            console.info(data);

        }

    });
}

// 修改玩具
function updateToy() {
    $.ajax({
        url:"/wawaji/toy/addToy.action",
        type:"POST",
        async:false,
        data:{
            id:$("#id").val(),
            toyNo:$("#toyNo").val(),
            toyForCoin:$("#toyForCoin").val(),
            toyDesc:$("#toyDesc").val(),
            toyNowCoin:$("#toyNowCoin").val(),
            toyOriginCoin:$("#toyOriginCoin").val(),
            toyCost:$("#toyCost").val(),
            toyImg:"q1"

        },
        success:function(data){

            console.info(data);

        }

    });
}