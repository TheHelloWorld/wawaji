// 每页数据数
var pageSize = 0;

var nowPage = 1;

var showUrl = "/wawaji/machine/getAllMachineByPage.action";

var width = $(window).width() / 2 - 20;

$(function(){
    var url = "/wawaji/machine/getMachineTotalCountAndPageSize.action";
    getTotalCountAndPageSize(url);
    getAllMachineByPage(nowPage);

});

function getAllMachineByPage(nowPage) {
    var startPage = (nowPage - 1) * pageSize;

    $.ajax({
        url:"/wawaji/machine/getUserAllMachineByPage.action",
        type:"POST",
        async:false,
        data:{
            startPage:startPage
        },
        success:function(data) {
            // 转换数据
            if(typeof(data) == "string") {
                data = eval("("+data+")");
            }

            // 判断是否成功
            if(data["result"] != "success") {
                alert(data["result"]);
            }

            var list = data["list"];

            var str = "";

            for(var i = 0; i<list.length; i++) {
                if(i % 2 == 0) {
                    str += "<div class='row' style='margin-bottom: 5px'>";
                }

                str += "<div class='machine-col-xs-6' >";
                str += "<div class='machine-panel panel-info'>";
                str += "<div class='panel-heading'></div>";
                str += "<div class='panel-body' style='background: #ffff99'>";
                str += "<img style='margin: 0 auto;' width=100% src='" + list[i]["toyImg"] + "' />";
                str += "<p>" + list[i]["toyName"] + "</p>";
                str += "<p>围观:" + list[i]["viewer"] + "</p>";
                str += "<p>游戏币:" + list[i]["toyNowCoin"] + "</p>";

                if(list[i]["available"] == "true") {
                    str += "<p>空闲</p>";
                } else {
                    str += "<p>使用中</p>";
                }
                str += "</div>";
                str += "<div class='panel-footer'></div>";
                str += "</div>";
                str += "</div>"

                if(i % 2 != 0) {
                    str += "</div>";
                }
            }
            $("#main").append(str);

        }
    });
}



function getPage(page) {
    nowPage = getPageByNum(nowPage, page, totalPage, step);
    getAllByPage(showUrl, nowPage);

}

function nextPage() {
    nowPage = nextPageNum(nowPage, totalPage, step);
    getAllByPage(showUrl, nowPage);

}

//上一页
function lastPage() {
    nowPage = lastPageNum(nowPage, totalPage, step);
    getAllByPage(showUrl, nowPage);
}