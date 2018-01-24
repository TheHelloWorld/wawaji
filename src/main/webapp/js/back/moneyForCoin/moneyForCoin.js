// 每页数据数
var pageSize = 0;

var step = 5;

var nowPage = 1;

// 展示url
var showUrl = "/toiletCat/api/moneyForCoin/getAllMoneyForCoin.action";

$(function() {

    // 分页获得所有记录
    getAllMoneyForCoin(showUrl);

});


// 公共展示方法
function getAllMoneyForCoin(url) {

    $.ajax({
        url:url,
        type:"POST",
        async:false,
        success:function(data) {

            $("#dataBody").html("");

            if(totalPage == 0){
                $("#dataDiv").hide();
                return;
            }

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            var list = data["result"];
            var str = "";

            for(var i = 0; i<list.length; i++) {

                str += "<tr id=tr"+list[i]["id"]+" class='even gradeA'>";

                $("#titleColumn").find("lable").each(function() {

                    var col = $(this).attr("name");

                    var td = "<td>";

                    if(col == "operation") {

                        var dataStr = JSON.stringify(list[i]);

                        td += "<a href='javascript:void(0);' onclick=updateThis('"+dataStr+"')>修改</a>";

                    } else {
                        //如果数据为空则写无
                        if((list[i][col] == undefined) || (list[i][col] == null) || (list[i][col] == "null")) {
                            td += "无";
                        } else {
                            td += list[i][col] + "";
                        }
                    }

                    td += "</td>";

                    str += td;
                });
                str += "</tr>";
            }

            $("#dataBody").append(str);
        }
    });
}


function addMoneyForCoinPage() {
    window.location.href="/toiletCat/back/moneyForCoin/moneyForCoinDetailPage.jsp?type=add";
}


// 修改元素
function updateThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];

    window.location.href = "/toiletCat/back/moneyForCoin/moneyForCoinDetailPage.jsp?type=update&id="+id;
}