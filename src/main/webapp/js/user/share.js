document.write("<script src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>")
var configData = [];
var shareUrl = "www.9w83c6.cn/toiletCat/share/share.html?shareId="+sessionStorage["toiletCatInvitationCode"];
var current = window.location.href;
$.ajax({
    url:"/toiletCat/api/weChat/getWxShareInfo.action",
    type:"post",
    data:{"url":currurl},
    success:function(data){
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: data.appId, // 必填，公众号的唯一标识
            timestamp: data.timestamp, // 必填，生成签名的时间戳
            nonceStr: data.nonceStr, // 必填，生成签名的随机串
            signature: data.signature,// 必填，签名，见附录1
            jsApiList: ['onMenuShareAppMessage', 'onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });

        wx.ready(function() {

            wx.onMenuShareAppMessage({
                title : '快和我一起来马桶猫抓娃娃吧', // 分享标题
                desc : '快和我一起来马桶猫抓娃娃吧', // 分享描述
                link : shareUrl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                imgUrl : 'http://www.9w83c6.cn/image/logo_small.png', // 分享图标
                type : '', // 分享类型,music、video或link，不填默认为link
                dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
                success : function() {
                    // 用户确认分享后执行的回调函数
                    // alert('share successful');
                },
                cancel : function() {
                    // 用户取消分享后执行的回调函数
                    // alert('share cancel');
                }
            }); // end onMenuShareAppMessage


            wx.onMenuShareTimeline({
                title : '快和我一起来马桶猫抓娃娃吧', // 分享标题
                link : shareUrl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                imgUrl : 'http://www.9w83c6.cn/image/logo_small.png', // 分享图标
                success : function() {
                    // 用户确认分享后执行的回调函数
                },
                cancel : function() {
                    // 用户取消分享后执行的回调函数
                }
            }); // end onMenuShareTimeline
        }); // end ready

        wx.error(function(res) {
            // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
            alert('error');
        });
    }
})