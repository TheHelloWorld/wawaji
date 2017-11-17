// 返回玩具列表页
function cancelThis() {
    window.location.href = "/wawaji/toy/toy.jsp";
}


function updateOrSaveToy() {

    if(getQueryString("type") == "save") {
        saveToy();
    } else {

    }

}

function saveToy() {
    console.info(""+$("#toyForCoin").val());

    console.info(""+$("#toyDesc").val());
    console.info(""+$("#toyNowCoin").val());
    console.info(""+$("#toyOriginCoin").val());
    console.info(""+$("#toyCost").val());
    console.info(""+$("#toyForCoin").val());
    console.info(""+$("#toyForCoin").val());

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