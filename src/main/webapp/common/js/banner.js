
var curIndex = 0;

$(function(){
    // 图片总数
    var imgLen = $(".imgList li").length;

    // 定时器自动变换2.5秒每次
    var autoChange = setInterval(function(){
        if(curIndex < imgLen-1){
            curIndex ++;
        }else{
            curIndex = 0;
        }
        //调用变换处理函数
        changeTo(curIndex);
    },2500);
});

function changeTo(num){
    var goLeft = num * 400;
    $(".imgList").animate({left: "-" + goLeft + "px"},500);
    $(".infoList").find("li").removeClass("infoOn").eq(num).addClass("infoOn");
    $(".indexList").find("li").removeClass("indexOn").eq(num).addClass("indexOn");
}