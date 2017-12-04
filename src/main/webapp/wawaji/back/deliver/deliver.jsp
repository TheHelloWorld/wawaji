<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Navigation -->
<%@ include file="../../../include/nav.jsp"%>

<!-- 该页面功能js -->
<script type="text/javascript" src="../../js/deliver/deliver.js"></script>

<!-- /.row -->
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default" >
                <div class="panel-heading" id="ph">
                    <strong>发货管理</strong>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body" id="row1">
                    <div class="dataTable_wrapper" id="tableDiv">

                        <div style="float:left;width:100%;" id="dataDiv">
                            <table class="table table-striped table-bordered table-hover" id="dataTable">
                                <thead>
                                <tr id="titleColumn">
                                    <th><lable name="userNo">用户编号</lable></th>
                                    <th><lable name="userName">用户姓名</lable></th>
                                    <th><lable name="mobileNo">手机号</lable></th>
                                    <th><lable name="address">地址</lable></th>
                                    <th><lable name="deliverNo">发货单号</lable></th>
                                    <th><lable name="company">快递公司</lable></th>
                                    <th><lable name="deliverMsg">货物信息</lable></th>
                                    <th><lable name="deliverStatus">发货状态</lable></th>
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
