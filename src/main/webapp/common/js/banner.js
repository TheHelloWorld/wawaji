
$(function (){
    $(".js-silder").silder({
        auto: true,//自动播放，传入任何可以转化为true的值都会自动轮播
        speed: 30,//轮播图运动速度
        sideCtrl: false,//是否需要侧边控制按钮
        bottomCtrl: false,//是否需要底部控制按钮
        defaultView: 0,//默认显示的索引
        interval: 3000,//自动轮播的时间，以毫秒为单位，默认3000毫秒
        activeClass: "active",//小的控制按钮激活的样式，不包括作用两边，默认active
    });
});