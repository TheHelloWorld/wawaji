<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Navigation -->
<%@ include file="/include/nav.jsp"%>

<!-- 该页面功能js -->
<script type="text/javascript" src="/js/back/toy/toy.js"></script>

<!-- <div id="page-wrapper"> -->
<%--<input type="hidden" id="num" value="${num}">--%>
<!-- /.row -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" >
                <div class="panel-heading" id="ph">
                    <strong>玩具管理</strong>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body" id="row1" style="overflow: scroll">
                    <div class="dataTable_wrapper" id="tableDiv">
                        <div id="searchDiv" class="input-group custom-search-form">
                            <button type="button" id="addToy" onclick="addToyPage()" class="btn btn-primary">添加记录</button>

                        </div>

                        <div style="float:left;width:100%;" id="dataDiv">
                            <table class="table table-striped table-bordered table-hover" id="dataTable">
                                <thead>
                                <tr id="titleColumn">
                                    <th><lable name="toyNo">玩具编号</lable></th>
                                    <th><lable name="toyName">玩具名称</lable></th>
                                    <th><lable name="toyForCoin">娃娃兑换游戏币数</lable></th>
                                    <th><lable name="toyNowCoin">娃娃当前游戏币数</lable></th>
                                    <th><lable name="toyOriginCoin">娃娃原本游戏币数</lable></th>
                                    <th><lable name="toyCost">娃娃成本</lable></th>
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
