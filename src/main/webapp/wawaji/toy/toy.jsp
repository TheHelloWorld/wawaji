<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Navigation -->
<%@ include file="../../include/nav.jsp"%>

<!-- 公共样式css -->
<link href="../../css/commonUtil.css" rel="stylesheet">

<!-- 锁表样式css -->
<link href="../../css/tableLock.css" rel="stylesheet">

<!-- 自动补全css -->
<link href="../../css/autocomplete.css" rel="stylesheet">

<!-- 画图js -->
<script type="text/javascript" src="../../common/js/echarts.min.js"></script>

<!-- autocomplete兼容js -->
<script type="text/javascript" src="../../common/js/jquery-migrate.js"></script>

<!-- jquery-ui js -->
<script type="text/javascript" src="../../common/js/jquery-ui.js"></script>

<!-- 自动补全js -->
<script type="text/javascript" src="../../common/js/autocomplete.js"></script>

<!-- 公共方法js -->
<script type="text/javascript" src="../../common/js/commonUtil.js"></script>

<!-- 锁表js -->
<script type="text/javascript" src="../../common/js/tableLock.js"></script>

<!-- 该页面功能js -->
<script type="text/javascript" src="../../js/toy/toy.js"></script>

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
                <div class="panel-body" id="row1">
                    <div class="dataTable_wrapper" id="tableDiv">
                        <div id="searchDiv" class="input-group custom-search-form">
                            <div class="input-group custom-search-form">
                                <div  class="input-group custom-search-form">
                                    <div class="form-group input-group">
                                        <select id="category_name" class="form-control" style="width:25%;">
                                            <option value="all">全部</option>
                                            <option value="其他">其他</option>
                                            <option value="增值">增值</option>
                                            <option value="差错">差错</option>
                                            <option value="归集">归集</option>
                                            <option value="收单">收单</option>
                                            <option value="结算">结算</option>
                                            <option value="费用">费用</option>
                                        </select>
                                        <select id="direction" class="form-control" style="width:25%;">
                                            <option value="all">全部</option>
                                            <option value="正向">正向</option>
                                            <option value="逆向">逆向</option>
                                        </select>
                                        <select id="instit_no" class="form-control" style="width:25%;">
                                            <option value="all">全部</option>
                                            <option value="LEFU">乐富</option>
                                            <option value="NLEFU">非乐富</option>
                                        </select>
                                        <select id="organization" class="form-control" style="width:25%;">
                                            <option value="all">全部</option>
                                            <option value="LEFU">乐富</option>
                                            <option value="NJJFT">南京佳付通</option>
                                        </select>
                                    </div>
                                    <div class="form-group input-group">
                                        <span>会计日:</span>
                                        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                               name="start_time" id="start_time" class="Wdate width80" value="" />
                                        -
                                        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                               name="end_time" id="end_time" class="Wdate width80" value="" />
                                        &nbsp;&nbsp;
                                        <button type="button" onclick="downloadExcel()" class="btn btn-link">导出Excel</button>
                                    </div>
                                    <div>
                                        <button type="button" onclick="getDate('yesterday','start_time','end_time')" class="btn btn-outline btn-primary btn-sm">昨日</button>
                                        <button type="button" onclick="getDate('today','start_time','end_time')" class="btn btn-outline btn-primary btn-sm">今日</button>
                                        <button type="button" onclick="getDate('lastWeek','start_time','end_time')" class="btn btn-outline btn-primary btn-sm">上周</button>
                                        <button type="button" onclick="getDate('thisWeek','start_time','end_time')" class="btn btn-outline btn-primary btn-sm">本周</button>
                                        <button type="button" onclick="getDate('lastMonth','start_time','end_time')" class="btn btn-outline btn-primary btn-sm">上月</button>
                                        <button type="button" onclick="getDate('thisMonth','start_time','end_time')" class="btn btn-outline btn-primary btn-sm">本月</button>
                                        <button type="button" onclick="getEmpty('start_time','end_time')" class="btn btn-outline btn-danger btn-sm">清空</button>
                                        &nbsp;&nbsp;
                                        <label>
                                            <input id="addSum" type="checkbox" value="1">查看合计
                                        </label>
                                    </div>
                                    <br>
                                    <div id="cnDiv" class="form-group input-group">
                                        <input type="text" id="cn_name" class="form-control" placeholder="产品名...">
                                    </div>

                                </div>
                                <button type="button" id="search" onclick="searchMethod()" class="btn btn-primary">搜索</button>

                            </div>

                        </div>

                        <div style="float:left;width:100%;" id="dataDiv">
                            <table class="table table-striped table-bordered table-hover" id="dataTable">
                                <thead>
                                <tr id="titleColumn">
                                    <th><lable name="toyNo">玩具编号</lable></th>
                                    <th><lable name="toyForCoin">娃娃兑换游戏币数</lable></th>
                                    <th><lable name="toyImg">娃娃图片地址</lable></th>
                                    <th><lable name="toyDesc">娃娃描述</lable></th>
                                    <th><lable name="toyNowCoin">娃娃当前游戏币数</lable></th>
                                    <th><lable name="toyOriginCoin">娃娃原本游戏币数</lable></th>
                                    <th><lable name="toyCost">娃娃成本</lable></th>
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
