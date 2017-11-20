// 每页数据数
var userNo = "";

$(function() {
    userNo = getQueryString("userNo");
    // 判断当前用户是否看继续添加
    judeUserAddress(userNo);

    // 获得当前用户所有地址记录
    getAllUserAddressByUserNo(userNo);

    // 初始化页码
    initPage(totalPage,step);
});



function getPage(page) {
    nowPage = getPageByNum(nowPage,page,totalPage,step);
    getAllByPage(showUrl, nowPage);

}

function nextPage() {
    nowPage = nextPageNum(nowPage,totalPage,step);
    getAllByPage(showUrl, nowPage);

}

//上一页
function lastPage() {
    nowPage = lastPageNum(nowPage,totalPage,step);
    getAllByPage(showUrl, nowPage);
}

function addToyPage() {
    window.location.href="/wawaji/toy/toyDetailPage.jsp?type=save";
}

function judeUserAddress(userNo) {
    $.ajax({
        url:"/wawaji/userAddress/judgeUserAddressIsMaxNum.action",
        type:"POST",
        async:false,
        data:{
            userNo:userNo
        },
        success:function(data){
            // 若后台操作成功则删除当前行
            if(data == "fail") {
               $("#addUserAddress").attr("disable", true);
            }
        }
    });
}

// 获得当前用户所有地址
function getAllUserAddressByUserNo(userNo) {

    dataParam = eval("(" + dataParam + ")");

    var id = dataParam["id"];
    var toyNo = dataParam["toyNo"];

    $.ajax({
        url:"/wawaji/userAddress/getUserAddressByUserNo.action",
        type:"POST",
        async:false,
        data:{
            id:id,
            toyNo:toyNo
        },
        success:function(data){
            $("#dataBody").html("");

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(totalPage == 0){
                $("#dataDiv").hide();
                return;
            }

            var list = data["list"];
            var str = "";

            for(var i = 0;i<list.length;i++) {


            }

            $("#dataBody").append(str);

        }
    });
}

// 添加元素
function toAddUserAddressPage() {

    window.location.href = "/wawaji/userAddress/userAddressDetailPage.jsp?type=add&userNo="+userNo+"";
}

// 修改元素
function toEditUserAddressPage() {

    window.location.href = "/wawaji/userAddress/userAddressDetailPage.jsp?type=update&userNo="+userNo+"";
}