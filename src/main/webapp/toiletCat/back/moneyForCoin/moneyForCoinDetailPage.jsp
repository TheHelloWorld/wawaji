<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="zikun.liu">

    <title>钱和游戏币对应关系页</title>

    <%@ include file="/include/header.jsp"%>

    <%@ include file="/include/checkLogin.jsp"%>

    <script type="text/javascript" src="/js/back/moneyForCoin/moneyForCoinDetailPage.js"></script>

</head>
<body>
    <div id="dataInfo">
        <div id="bannerImgDefine">
            <div class="modal-header">
                <h4 class="modal-title">
                    钱和游戏币对应关系页
                </h4>
            </div>
            <div id="moneyForCoin-body" class="modal-body">
                <span name = "id">
                    <input type = 'hidden' id = "id" />
                </span>
                <div class="row">
                    <div class="col-xs-5">
                        钱数:
                        <span name = "money">
                            <input class="form-control" id = "money">
                        </span>
                    </div>
                    <div class="col-xs-5">
                        是否可用:
                        <span name = "currentState">
                            <select id="currentState" class="form-control">
                                <option value="1">可用</option>
                                <option value="0">禁用</option>
                            </select>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        游戏币数:
                        <span name = "coin">
                            <input class="form-control" id = "coin">
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        前端展示的游戏币文案:
                        <span name = "showText">
                            <input class="form-control" id = "showText">
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        前端展示的游戏币充值文案:
                        <span name = "coinText">
                            <input class="form-control" id = "coinText">
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        每天充值限制次数:
                        <span name = "rechargeLimit">
                            <input class="form-control" id = "rechargeLimit">
                        </span>
                    </div>
                    <div class="col-xs-5">
                        首充标志位:
                        <span name = "firstFlag">
                            <select id="firstFlag" class="form-control">
                                <option value="0">不启用</option>
                                <option value="1">启用首充</option>
                            </select>
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        赠送的游戏币数:
                        <span name = "giveCoin">
                            <input class="form-control" id = "giveCoin">
                        </span>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-xs-5">
                        展示顺序:
                        <span name = "showOrder">
                            <input class="form-control" id = "showOrder">
                        </span>
                    </div>
                </div>
                <br/>
            </div>
        </div>
        <div class="text-center">
            <button type="button" class="btn btn-danger btn-lg" onclick="cancelThis()">取 消</button>
            &nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-primary btn-lg" onclick="updateOrSaveBannerImg()">
                提 交
            </button>
        </div>
    </div>
</body>
</html>
