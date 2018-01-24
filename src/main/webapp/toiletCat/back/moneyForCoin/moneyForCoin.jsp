<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Navigation -->
<%@ include file="/include/nav.jsp"%>

<!-- 该页面功能js -->
<script type="text/javascript" src="/js/back/moneyForCoin/moneyForCoin.js"></script>

<!-- <div id="page-wrapper"> -->
<%--<input type="hidden" id="num" value="${num}">--%>
<!-- /.row -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" >
                <div class="panel-heading" id="ph">
                    <strong>钱和游戏币对应关系</strong>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body" id="row1">
                    <div class="dataTable_wrapper" id="tableDiv">
                        <div id="searchDiv" class="input-group custom-search-form">
                            <button type="button" id="addMoneyForCoin" onclick="addMoneyForCoinPage()" class="btn btn-primary">添加记录</button>

                        </div>

                        <div style="float:left;width:100%;" id="dataDiv">
                            <table class="table table-striped table-bordered table-hover" id="dataTable">
                                <thead>
                                <tr id="titleColumn">
                                    <th><lable name="money">钱数</lable></th>
                                    <th><lable name="coin">游戏币数</lable></th>
                                    <th><lable name="showText">前端展示的游戏币文案</lable></th>
                                    <th><lable name="rechargeLimit">每天充值限制次数</lable></th>
                                    <th><lable name="firstFlag">首充标志位</lable></th>
                                    <th><lable name="giveCoin">赠送的游戏币数</lable></th>
                                    <th><lable name="currentState">是否可用</lable></th>
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
