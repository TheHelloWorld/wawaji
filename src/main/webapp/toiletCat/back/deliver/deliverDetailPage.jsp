<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>用户发货页</title>

    <%@ include file="../../../include/header.jsp"%>

    <script type="text/javascript" src="/js/back/deliver/deliverDetailPage.js"></script>
</head>
<body>
<div id="dataInfo">
    <div id="deliverDefine">
        <div class="modal-header">
            <h4 class="modal-title">
                用户发货页
            </h4>
        </div>
        <div id="deliver-body" class="modal-body">
            <span name = "id">
                <input type = 'hidden' id = "id">
            </span>
            <div class="row">
                <div class="col-xs-5">
                    用户编号:
                    <span name = "userNo">
                        <input class="form-control" id = "userNo" disabled />
                    </span>
                </div>
                <div class="col-xs-5">
                    用户姓名:
                    <span name = "userName">
                        <input id="userName" class="form-control" disabled />
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    手机号:
                    <span name = "mobileNo">
                        <input class="form-control" id = "mobileNo" disabled />
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    地址:
                    <span name = "address">
                        <input class="form-control" id = "address">
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    发货单号:
                    <span name = "deliverNo">
                        <input class="form-control" id="deliverNo" />
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    快递公司:
                    <span name = "company">
                        <input class="form-control" id="company" />
                    </span>
                </div>
                <div class="col-xs-5">
                    发货状态:
                    <span name = "deliverStatus">
                        <select id="deliverStatus" class="form-control" >
                            <option value="0">待发货</option>
                            <option value="1">已发货</option>
                        </select>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div class="text-center">
        <button type="button" class="btn btn-danger btn-lg" onclick="cancelThis()">取 消</button>
        &nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary btn-lg" onclick="updateDeliver()">
            提 交
        </button>
    </div>
</div>
</body>
</html>
