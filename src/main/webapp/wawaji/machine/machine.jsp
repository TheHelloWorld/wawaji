<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Navigation -->
<%@ include file="../../include/nav.jsp"%>

<!-- 公共样式css -->
<link href="${staticFilePath}${pageContext.request.contextPath}/css/commonUtil.css" rel="stylesheet">

<!-- 锁表样式css -->
<link href="${staticFilePath}${pageContext.request.contextPath}/css/tableLock.css" rel="stylesheet">

<!-- 自动补全css -->
<link href="${staticFilePath}${pageContext.request.contextPath}/css/autocomplete.css" rel="stylesheet">

<!-- 画图js -->
<script type="text/javascript" src="${staticFilePath}${pageContext.request.contextPath}/common/js/echarts.min.js"></script>

<!-- autocomplete兼容js -->
<script type="text/javascript" src="${staticFilePath}${pageContext.request.contextPath}/common/js/jquery-migrate.js"></script>

<!-- jquery-ui js -->
<script type="text/javascript" src="${staticFilePath}${pageContext.request.contextPath}/common/js/jquery-ui.js"></script>

<!-- 自动补全js -->
<script type="text/javascript" src="${staticFilePath}${pageContext.request.contextPath}/common/js/autocomplete.js"></script>

<!-- 公共方法js -->
<script type="text/javascript" src="${staticFilePath}${pageContext.request.contextPath}/common/js/commonUtil.js"></script>

<!-- 锁表js -->
<script type="text/javascript" src="${staticFilePath}${pageContext.request.contextPath}/common/js/tableLock.js"></script>

<!-- 该页面功能js -->
<script type="text/javascript" src="${staticFilePath}${pageContext.request.contextPath}/js/productDayStat.js"></script>

<!-- <div id="page-wrapper"> -->
<input type="hidden" id="num" value="${num}">
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default" >
            <div class="panel-heading" id="ph">
                <strong>收入毛利报表</strong>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body" id="row1">
                <div class="dataTable_wrapper" id="tableDiv">
                    <div id="searchDiv" class="input-group custom-search-form">
                        <div class="input-group custom-search-form">
                            <div  class="input-group custom-search-form">
                                <div class="form-group input-group">
                                    <select id="category_name" class="form-control" style="width:25%;" id=columns>
                                        <option value="all">全部</option>
                                        <option value="其他">其他</option>
                                        <option value="增值">增值</option>
                                        <option value="差错">差错</option>
                                        <option value="归集">归集</option>
                                        <option value="收单">收单</option>
                                        <option value="结算">结算</option>
                                        <option value="费用">费用</option>
                                    </select>
                                    <select id="direction" class="form-control" style="width:25%;" id=columns>
                                        <option value="all">全部</option>
                                        <option value="正向">正向</option>
                                        <option value="逆向">逆向</option>
                                    </select>
                                    <select id="instit_no" class="form-control" style="width:25%;" id=columns>
                                        <option value="all">全部</option>
                                        <option value="LEFU">乐富</option>
                                        <option value="NLEFU">非乐富</option>
                                    </select>
                                    <select id="organization" class="form-control" style="width:25%;" id=columns>
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
                            <button type="button" id="search" onclick="searchMethod()" class="btn btn-primary"">搜索</button>

                        </div>

                    </div>

                    <div style="float:left;width:100%;" id="dataDiv">
                        <table class="table table-striped table-bordered table-hover" id="dataTable">
                            <thead>
                            <tr id="titlecolumn">
                                <th class="shrinkMethon" lock="true"><lable lock="true" name="date">会计日&nbsp;&nbsp;</lable><span class="fa fa-chevron-circle-left"></span></th>
                                <th class="shrinkMethon" lock="true"><lable lock="true" name="category_name">产品分类&nbsp;&nbsp;</lable><span class="fa fa-chevron-circle-left"></span></th>
                                <th class="shrinkMethon" lock="true"><lable lock="true" name="cn_name">产品名&nbsp;&nbsp;</lable><span class="fa fa-chevron-circle-left"></span></th>
                                <th class="shrinkMethon" lock="true"><lable lock="true" name="direction">正向逆向&nbsp;&nbsp;</lable><span class="fa fa-chevron-circle-left"></span></th>
                                <th><lable name="trans_num">交易笔数</lable></th>
                                <th><lable class="onright" name="trans_fee">交易金额</lable></th>
                                <th><lable class="onright" name="sx_fee">手续费</lable></th>
                                <th><lable class="onright" name="bank_cost">银行成本</lable></th>
                                <th><lable class="onright" name="income">收入</lable></th>
                                <th><lable class="onright" name="fr_fee">服务商分润</lable></th>
                                <th><lable class="onright" name="ret_fee">合伙人分润</lable></th>
                                <th><lable class="onright" name="gross_profit">毛利</lable></th>
                                <th><lable class="onright" name="avg_trans_fee">平均交易金额</lable></th>
                                <th><lable class="onright" name="avg_sx_fee">平均手续费</lable></th>
                                <th><lable class="onright" name="avg_bank_cost">平均银行成本</lable></th>
                                <th><lable class="onright" name="avg_income">平均收入</lable></th>
                                <th><lable class="onright" name="avg_fr_fee">平均服务商分润</lable></th>
                                <th><lable class="onright" name="avg_ret_fee">平均合伙人分润</lable></th>
                                <th><lable class="onright" name="avg_gross_profit">平均毛利</lable></th>
                            </tr>
                            </thead>
                            <tbody id="dataTbody">
                            </tbody>
                        </table>
                    </div>


                    <div style="float:left;width:100%;" id="groupDiv">
                        <div style="text-align:center">
                            <ul class="pagination">
                                <li id="tableLi" class="active" onclick="switchMethod('table')">
                                    <a>表格展示</a>
                                </li>
                                <li id="drawLi" onclick="switchMethod('draw')">
                                    <a>图表展示</a>
                                </li>
                            </ul>

                            <table class="table table-striped table-bordered table-hover" id="groupTable">
                                <thead>
                                <tr id="groupcolumn">
                                    <th><lable name="date">会计日&nbsp;&nbsp;</lable></th>
                                    <th><lable class="onright" name="sx_fee">手续费</lable></th>
                                    <th><lable class="onright" name="bank_cost">银行成本</lable></th>
                                    <th><lable class="onright" name="income">收入</lable></th>
                                    <th><lable class="onright" name="fr_fee">服务商分润</lable></th>
                                    <th><lable class="onright" name="ret_fee">合伙人分润</lable></th>
                                    <th><lable class="onright" name="gross_profit">毛利</lable></th>
                                </tr>
                                </thead>
                                <tbody id="groupTbody">
                                </tbody>
                            </table>
                        </div>
                        <div style="text-align:center">
                            <div id="drawDiv" >

                            </div>
                        </div>
                    </div>

                    <div id="splitPage" >
                    </div>
                </div>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- </div> -->
<!-- /#page-wrapper -->
<!-- </div> -->

<!-- Page-Level Demo Scripts - Tables - Use for reference -->

</body>

</html>
