// 每页数据数
var pageSize = 0;

var step = 5;

var nowPage = 1;

// 展示url
var showUrl = "/toiletCat/api/bannerImg/getBannerImgByPage.action";

$(function() {
    // 获得总页数和总数量
    var countAndPageSizeUrl = "/toiletCat/api/bannerImg/getBannerImgTotalCountAndPageSize.action";
    getTotalCountAndPageSize(countAndPageSizeUrl);

    // 分页获得所有记录
    getAllBannerImgByPage(showUrl, nowPage);

    // 初始化页码
    initPage(totalPage,step);
});


// 公共展示方法
function getAllBannerImgByPage(url, startPage) {

    startPage = (startPage - 1) * pageSize;

    $.ajax({
        url:url,
        type:"POST",
        async:false,
        data:{
            startPage:startPage
        },
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

                    } else if(col == "bannerType") {

                        if(list[i][col] == "0") {
                            td += "娃娃机房间";
                        } else if(list[i][col] == "1") {
                            td += "游戏房间";
                        }
                    } else if(col == "clickType") {

                        if(list[i][col] == "0") {
                            td += "跳转页面";
                        } else if(list[i][col] == "1") {
                            td += "触发方法";
                        }
                    }else {
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

function addBannerPage() {
    window.location.href="/toiletCat/back/bannerImg/bannerImgDetailPage.jsp?type=add";
}


// 修改元素
function updateThis(dataParam) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];

    window.location.href = "/toiletCat/back/bannerImg/bannerImgDetailPage.jsp?type=update&id="+id;
}