<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>玩具页</title>
    <%@ include file="../../include/header.jsp"%>
    <script type="text/javascript" src="/js/toy/toyDetailPage.js"></script>
</head>
<body>
    <div id="toyInfo">
        <div id="toyDefine">
            <div class="modal-header">
                <h4 class="modal-title">
                    玩具页
                </h4>
            </div>
            <div id="toy-body" class="modal-body">
                    <span name = "id">
                        <input type = 'hidden' id = "id">
                    </span>
                <div class="row">
                    <div class="col-xs-5">
                        玩具编号:
                        <span name = "toyNo">
                            <input class="form-control" id = "toyNo">
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
                        娃娃可兑换游戏币数:
                        <span name = "toyForCoin">
                            <input class="form-control" id = "toyForCoin">
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        玩具描述:
                        <span name = "toyDesc">
                            <input class="form-control" id="toyDesc" />
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-4">
                        娃娃当前游戏币数:
                        <span name = "toyNowCoin">
                           <input class="form-control" id="toyNowCoin" />
                        </span>
                    </div>
                    <div class="col-xs-4">
                        娃娃原本游戏币数:
                        <span name = "toyOriginCoin">
                            <input class="form-control" id="toyOriginCoin" />
                        </span>
                    </div>
                    <div class="col-xs-4">
                        娃娃成本:
                        <span name = "toyCost">
                            <input class="form-control" id="toyCost" />
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="text-center">
            <button type="button" class="btn btn-danger btn-lg" onclick="cancelThis()">取 消</button>
            &nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-primary btn-lg" onclick="updateOrSaveToy()">
                提 交
            </button>
        </div>
    </div>
</body>
</html>
