<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
    // 获取后台登录标识
    var loginFlag = sessionStorage["toiletCatBackLogin"];

    // 若未登录则跳转到登录页面
    if(loginFlag == undefined || loginFlag != "toiletCat_true") {
        window.location.href = "/toiletCat/back/login.jsp";
    }

</script>