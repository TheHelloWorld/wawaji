

$(document).ready(function () {

    var activeCode = getSource("activeCode");
    var code = getSource("code");
    //取活动信息
    $.ajax({
        url:"/sudadai/active/queryActiveInfo.aspx",
        async:false,
        type:"POST",
        data:{activeCode:activeCode,code:code},
        success:function(data){
            var result = data;
            if(result.success){
                window.location.replace(result.activeInfo.activeUrl);
            }else{
                alert(result.reason);
            }
        }
    });
});

function jump() {
    var activeCode = getSource("activeCode");
    var code = getSource("code");
    //取活动信息
    $.ajax({
        url:"/sudadai/active/queryActiveInfo.aspx",
        async:false,
        type:"POST",
        data:{activeCode:activeCode,code:code},
        success:function(data){
            var result = data;
            if(result.success){
                window.location.replace(result.activeInfo.activeUrl);
            }else{
                alert(result.reason);
            }
        }
    });
};

/*截取url字符串参数*/
function getSource(code) {
    var reg = new RegExp("(^|&)" + code + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    var source = unescape(r[2]);
    return source;
}
