<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>游戏房间页</title>

    <%@ include file="/include/header.jsp"%>

    <script type="text/javascript" src="/js/back/gameRoom/gameRoomDetailPage.js"></script>
</head>
<body>
<div id="dataInfo">
    <div id="deliverDefine">
        <div class="modal-header">
            <h4 class="modal-title">
                游戏房间页
            </h4>
        </div>
        <div id="deliver-body" class="modal-body">
            <span name = "id">
                <input type = 'hidden' id = "id">
            </span>
            <div class="row">
                <div class="col-xs-5">
                    游戏房间编号:
                    <span name = "gameRoomNo">
                        <input class="form-control" id = "gameRoomNo" />
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
            <div class="row">
                <div class="col-xs-5">
                    玩具编号:
                    <span name = "toyNo">
                        <input id="toyNo" class="form-control" />
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    房间幸运值:
                    <span name = "roomLuckyNum">
                        <input class="form-control" id = "roomLuckyNum" />
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    房间当前幸运值:
                    <span name = "roomNowLuckyNum">
                        <input class="form-control" id = "roomNowLuckyNum">
                    </span>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-5">
                    房间每次累加幸运值:
                    <span name = "addLuckyNum">
                        <input class="form-control" id="addLuckyNum" />
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div class="text-center">
        <button type="button" class="btn btn-danger btn-lg" onclick="cancelThis()">取 消</button>
        &nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary btn-lg" onclick="updateOrSaveGameRoom()">
            提 交
        </button>
    </div>
</div>
</body>
</html>
