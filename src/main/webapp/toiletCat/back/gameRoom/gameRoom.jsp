<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Navigation -->
<%@ include file="../../../include/nav.jsp"%>

<!-- 该页面功能js -->
<script type="text/javascript" src="/js/back/gameRoom/gameRoom.js"></script>

<!-- /.row -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" >
                <div class="panel-heading" id="ph">
                    <strong>游戏房间管理</strong>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body" id="row1">
                    <div class="dataTable_wrapper" id="tableDiv">
                        <div id="searchDiv" class="input-group custom-search-form">
                            <button type="button" id="addToy" onclick="addGameRoomPage()" class="btn btn-primary">添加记录</button>
                        </div>
                        <div style="float:left;width:100%;" id="dataDiv">
                            <table class="table table-striped table-bordered table-hover" id="dataTable">
                                <thead>
                                <tr id="titleColumn">
                                    <th><lable name="gameRoomNo">游戏房间编号</lable></th>
                                    <th><lable name="toyNo">玩具编号</lable></th>
                                    <th><lable name="roomLuckyNum">房间幸运值</lable></th>
                                    <th><lable name="roomNowLuckyNum">房间当前幸运值</lable></th>
                                    <th><lable name="addLuckyNum">房间每次累加幸运值</lable></th>
                                    <th><lable name="currentState">状态</lable></th>
                                    <th><lable name="operation">操作</lable></th>
                                </tr>
                                </thead>
                                <tbody id="dataBody">
                                </tbody>
                            </table>
                        </div>

                        <div id="splitPage" >
                        </div>
                    </div>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>

    </div>
</div>

</body>

</html>
