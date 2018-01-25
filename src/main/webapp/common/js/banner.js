
// banner滑动
function bannerSilder() {

    $(".js-silder").silder({
        auto: true,//自动播放，传入任何可以转化为true的值都会自动轮播
        speed: 30,//轮播图运动速度
        sideCtrl: false,//是否需要侧边控制按钮
        bottomCtrl: false,//是否需要底部控制按钮
        defaultView: 0,//默认显示的索引
        interval: 3000,//自动轮播的时间，以毫秒为单位，默认3000毫秒
        activeClass: "active"//小的控制按钮激活的样式，不包括作用两边，默认active
    });
}

// 根据类型获得banner图
function getBannerByType(type) {

    $.ajax({
        url:"/toiletCat/api/bannerImg/getBannerImgByBannerType.action",
        type:"POST",
        async:false,
        data:{
            bannerType : type
        },
        success:function(data){

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            var list = data["result"];

            if(list == null || list.length == 0) {
                return;
            }

            var str = " <div class='banner'>";
            str += "        <div class='js-silder'>";
            str += "            <div class='silder-scroll'>";
            str += "                <div class='silder-main'>";


            for(var i = 0; i<list.length; i++) {
                str += "<div class='silder-main-img'>";
                str += "    <img class='banner-img' src='"+list[i]["imgUrl"]+"' onclick=bannerClick('"+list[i]["clickUrl"]+"','"+list[i]["clickType"]+"') alt=''>";
                str += "</div>";
            }

            str += "            </div>";
            str += "        </div>";
            str += "    </div>";
            str += "</div>";

            $("#banner-box").append(str);

            $("#banner-box").height($(window).height() * 0.3);

            if(list.length > 1) {
                bannerSilder();
            }
        }
    });
}

// 点击banner跳转
function bannerClick(url, type) {

    if(type == "0") {
        window.location.href = url;
    } else {
        eval(url);
    }


}