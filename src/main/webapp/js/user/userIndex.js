$(function(){
    var hc = $(window).height();

    $(".background-div").css(
        'height',hc
    );

    $(".center-content").css(
        'height',hc*0.29
    );

    $(".center-other").css(
        'height',hc*0.07
    );

    $(".center-img img").css(
        'height',hc*0.1
    );
});