<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>马桶猫管理后台登录</title>

    <%@ include file="/include/header.jsp"%>

    <script type="text/javascript">
        
        function backLogin() {
            $.ajax({
                url:"/toiletCat/api/back/backLogin.action",
                type:"POST",
                async:false,
                data:{
                    loginName: $("#loginName").val(),
                    password: $("#password").val()
                },
                success:function(data) {
                    // 转换数据
                    if(typeof(data) == "string") {
                        data = eval("("+data+")");
                    }

                    var result = data["result"];

                    if(typeof(result) == "string") {
                        result = eval("("+result+")");
                    }

                    if(result["result_flag"] == "login_success") {

                        sessionStorage["toiletCatBackLogin"] = result["result_msg"];

                        sessionStorage["toiletCatBackLogin_Local"] = result["result_msg"];

                        window.location.href = "/toiletCat/back/toiletCatConfig/toiletCatConfig.jsp";

                    } else {
                        alert("用户名或密码错误");
                    }
                }
            });
        }

    </script>

</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">登录</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="loginName" id="loginName" name="登录名称" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" id="password" name="password" type="password" value="">
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <a href="javascript:void(0)" onclick="backLogin()" class="btn btn-lg btn-success btn-block">登录</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>

</html>
