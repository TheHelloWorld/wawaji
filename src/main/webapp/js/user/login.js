$(function() {

    var hc = $(window).height();

    var wc = $(window).width();

    $(".background-div").css(
        'height',hc
    );

    $(".login-mobile").css(
        'width',wc*0.5
    );

    $(".login-safe").css(
        'width',wc*0.3
    );

    $(".login-code").css(
        'width',wc*0.3
    );

    $(".login-get-code").css(
        'width',wc*0.2
    );

    $(".login").css(
        'width',wc*0.4
    );

    $(".safe-img").css(
        'width',wc*0.2
    );

    $(".logo").css(
        'width',wc*0.6
    );
});