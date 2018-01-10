<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>配置页</title>

    <%@ include file="/include/header.jsp"%>

    <%@ include file="/include/checkLogin.jsp"%>

    <script type="text/javascript" src="/js/back/toiletCatConfig/toiletCatConfigDetailPage.js"></script>
</head>
<body>
<div id="dataInfo">
    <div id="machineDefine">
        <div class="modal-header">
            <h4 class="modal-title">
                配置详情页
            </h4>
        </div>
        <div id="machine-body" class="modal-body">
            <span name = "id">
                <input type = 'hidden' id = "id">
            </span>
            <div class="row">
                <div class="col-xs-5">
                    配置名称:
                    <span name = "configName">
                        <input class="form-control" id = "configName">
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    配置key(程序中用):
                    <span name = "configKey">
                        <input class="form-control" id = "configKey">
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    配置value:
                    <span name = "configValue">
                        <input class="form-control" id = "configValue">
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div class="text-center">
        <button type="button" class="btn btn-danger btn-lg" onclick="cancelThis()">取 消</button>
        &nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary btn-lg" onclick="updateOrSaveConfig()">
            提 交
        </button>
    </div>
</div>
</body>
</html>
