<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>机器页</title>
    <%@ include file="../../include/header.jsp"%>
    <script type="text/javascript" src="/js/machine/machineDetailPage.js"></script>
</head>
<body>
<div id="machineInfo">
    <div id="machineDefine">
        <div class="modal-header">
            <h4 class="modal-title">
                机器页
            </h4>
        </div>
        <div id="machine-body" class="modal-body">
            <span name = "id">
                <input type = 'hidden' id = "id">
            </span>
            <div class="row">
                <div class="col-xs-5">
                    机器编号:
                    <span name = "machineNo">
                            <input class="form-control" id = "machineNo">
                        </span>
                </div>
                <div class="col-xs-5">
                    是否可用:
                    <span name = "currentState">
                        <select id="currentState" class="form-control">
                            <option value="1">可用</option>
                            <option value="0">不可用</option>
                        </select>
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    玩具编号:
                    <span name = "toyNo">
                        <input class="form-control" id = "toyNo">
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    正面直播地址:
                    <span name = "frontUrl">
                        <input class="form-control" id = "frontUrl">
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    侧面直播地址:
                    <span name = "sideUrl">
                        <input class="form-control" id="sideUrl" />
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    占用端口号:
                    <span name = "usePort">
                        <input class="form-control" id="usePort" />
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div class="text-center">
        <button type="button" class="btn btn-danger btn-lg" onclick="cancelThis()">取 消</button>
        &nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary btn-lg" onclick="updateOrSaveMachine()">
            提 交
        </button>
    </div>
</div>
</body>
</html>
