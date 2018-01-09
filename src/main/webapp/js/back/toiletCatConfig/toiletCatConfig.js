
$(function(){
    // 分页获得所有记录
    getAllByPage();
});

// 公共展示方法
function getAllByPage() {

    $.ajax({
        url:"/toiletCat/api/toiletCatConfig/getAllConfig.action",
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

                        td += "<a href='javascript:void(0);' onclick=updateThis('"+dataStr+"')>修改</a> | ";
                        td += "<a href='javascript:void(0);' onclick=deleteThis('"+dataStr+"')>删除</a>";

                    } else if(col == "currentState") {

                        if(list[i][col] == "0") {
                            td += "禁用";
                        } else if(list[i][col] == "1") {
                            td += "可用";
                        }
                    } else {
                        //如果数据为空则写无
                        if((list[i][col] == undefined) || (list[i][col] == null) || (list[i][col] == "null")) {
                            td += "无";
                        } else {
                            td += list[i][col];
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

function addConfigPage() {
    window.location.href="/toiletCat/back/toiletCatConfig/toiletCatConfigDetailPage.jsp?type=add";
}

// 删除行元素
function deleteThis(dataStr) {

    var dataParam = eval("(" + dataStr + ")");

    var id = dataParam["id"];

    $.ajax({
        url:"/toiletCat/api/toiletCatConfig/deleteToiletCatConfig.action",
        type:"POST",
        async:false,
        data:{
            dataStr:dataStr
        },
        success:function(data){

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            $("#tr"+id).remove();
        }
    });
}

// 修改元素
function updateThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];

    window.location.href = "/toiletCat/back/toiletCatConfig/toiletCatConfigDetailPage.jsp?type=update&id="+id;
}