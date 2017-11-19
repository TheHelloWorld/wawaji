//记录收缩列的字符串
var shrinkStr = "";
var totalPage = 0;

var totalCount = 0;
//当前日期
var now = new Date();
//今天本周的第几天
var nowDayOfWeek = now.getDay()-1;
//当前日
var nowDay = now.getDate();
//当前月
var nowMonth = now.getMonth();
//当前年
var nowYear = now.getYear();
nowYear += (nowYear < 2000) ? 1900 : 0;

//上月日期
var lastMonthDate = new Date();
lastMonthDate.setDate(1);
lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
var lastYear = lastMonthDate.getYear();
var lastMonth = lastMonthDate.getMonth();

//显示图表或表格的标志位
var tableOrPicture = false;
// 储存或修改用json
var json = {};

//初始化页码
function initPage(num, step){

	$("#splitPage").html("");

	if(totalCount == 0){

		$("#dataDiv").hide();
		var msg = "<div class='alert alert-warning'>";
		msg += "<strong>什么都找不到 QAQ </strong>没有符合的数据。";
		msg += "</div>";
		$("#splitPage").append(msg);
		conmmonSlide("splitPage");
		//$( "#splitPage" ).effect( "slide", options, 500, callback );
		return;
	}

	if(num>step){
		num = step;
	}

	var str = "";
	str += "<ul class='pagination'>";
	str += "<li><a href='javascript:lastPage()'>&laquo;</a></li>";
	for(var i = 1;i<=num;i++){
		str += "<li id=page"+i+"><a href='javascript:getPage("+i+")'>"+i+"</a></li>";
	}
	str += "<li><a href='javascript:nextPage()'>&raquo;</a></li>";
	str += "</ul>";

	$("#splitPage").append(str);
	$("#page1").addClass("active");

}

//普通公共搜索方法
function commonMethon(url,keyWord,page){
	$("#groupDiv").hide();
	$.ajax({
		url:url,
		type:"POST",
		data:{
			keyWord:keyWord,
			page:page
		},
		async:false,
		success:function(data){
			$("#dataTbody").html("");
			var str = "";
			if(typeof(data) == "string"){
				data = eval("("+data+")");
			}
			totalPage = data.num;
			//console.info(totalPage);
			if(totalPage == 0){
				$("#dataDiv").hide();
				return;
			}
			var s = eval(data.list);
			//console.info("length:"+s.length);
			for(var i = 0;i<s.length;i++){
				str += "<tr class='even gradeA'>";

				$("#titlecolumn").find("lable").each(function(){
					var col = $(this).attr("name");
					var td = "<td>";

					if($(this).attr("class") == "onright"){
						td = "<td class='onright'>"
					}

					if($(this).attr("lock") != undefined && $(this).attr("lock") != null){
						td += "<span>"
					}

					//如果数据为空则写无
					if((s[i][col] == undefined) || (s[i][col] == null) || (s[i][col] == "null")){
						td+="无";
					}else{
						td+=s[i][col]+"";
					}
					if($(this).attr("lock") != undefined && $(this).attr("lock") != null){
						td += "</span>&nbsp;&nbsp;&nbsp;&nbsp;"
					}
					td += "</td>";
					str += td;
				});
				str += "</tr>";
			}
			$("#dataTbody").append(str);

			$("#dataDiv").show();

		}
	});
	return totalPage;
}

//合计公共搜索方法
function commonGroupMethon(url,keyWord,page){
	$("#dataDiv").hide();
	$.ajax({
		url:url,
		type:"POST",
		data:{
			keyWord:keyWord,
			page:page
		},
		async:false,
		success:function(data){

			var str = "";
			if(typeof(data) == "string"){
				data = eval("("+data+")");
			}
			totalPage = data.num;
			//console.info(totalPage);
			if(totalPage == 0){
				$("#groupDiv").hide();
				return;
			}

			$("#groupTbody").html("");

			var s = eval(data.list);
			//console.info("length:"+s.length);
			for(var i = 0;i<s.length;i++){
				str += "<tr class='even gradeA'>";

				$("#groupcolumn").find("lable").each(function(){
					var col = $(this).attr("name");
					var td = "<td>";

					if($(this).attr("class") == "onright"){
						td = "<td class='onright'>"
					}

					if($(this).attr("lock") != undefined && $(this).attr("lock") != null){
						td += "<span>"
					}

					//如果数据为空则写无
					if((s[i][col] == undefined) || (s[i][col] == null) || (s[i][col] == "null")){
						td+="无";
					}else{
						td+=s[i][col]+"";
					}
					if($(this).attr("lock") != undefined && $(this).attr("lock") != null){
						td += "</span>&nbsp;&nbsp;&nbsp;&nbsp;"
					}
					td += "</td>";
					str += td;
				});
				str += "</tr>";
			}
			$("#groupTbody").append(str);
			$("#drawDiv").hide();

			$("#groupDiv").show();
			$("#groupTable").show();

		}
	});
	return totalPage;
}

//根据页码获得数据
function getPageByNum(nowpage,page,totalPage,step){
	if(page % step == 0){
		var num = 0;
		if(page + step > totalPage){
			num = totalPage;
		}else{
			num = page + step;
		}

		var str = "";
		str += "<ul class='pagination'>";
		str += "<li><a href='javascript:lastPage()'>&laquo;</a></li>";
		for(var i = page;i<=num;i++){
			str += "<li id=page"+i+"><a href='javascript:getPage("+i+")'>"+i+"</a></li>";
		}
		str += "<li><a href='javascript:nextPage()'>&raquo;</a></li>";
		str += "</ul>";
		$("#splitPage").html("");
		$("#splitPage").append(str);
	}else if((page+1) % step == 0){
		var num = 1;
		if((page + 1)-step > 0){
			num = (page + 1)-step;
		}

		var lastNum = page +1;
		if(lastNum > totalPage){
            lastNum = totalPage;
		}
		var str = "";
		str += "<ul class='pagination'>";
		str += "<li><a href='javascript:lastPage()'>&laquo;</a></li>";
		for(var i = num;i<=lastNum;i++){
			str += "<li id=page"+i+"><a href='javascript:getPage("+i+")'>"+i+"</a></li>";
		}
		str += "<li><a href='javascript:nextPage()'>&raquo;</a></li>";
		str += "</ul>";
		$("#splitPage").html("");
		$("#splitPage").append(str);

	}else{
		$("#page"+nowpage).removeClass("active");
	}
	$("#page"+page).addClass("active");
	nowpage = page;
	return nowpage;
}

//下一页
function nextPageNum(nowpage,totalPage,step){
	if(nowpage+1<=totalPage){
		return getPageByNum(nowpage,(nowpage+1),totalPage,step);
	}else{
		return 1;
	}

}

//上一页
function lastPageNum(nowpage,totalPage,step){
	if(nowpage-1>0){
		return getPageByNum(nowpage,(nowpage-1),totalPage,step);
	}else{
		return 1;
	}
}


//导出excel
function downloadExcelUtil(url,excelUrl,keyword){

	if(url != ""){
		$.ajax({
			url:url,
			type:"POST",
			async:false,
			data:{
				keyWord:keyword
			},
			async:false,
			success:function(data){
				if(data == 0){
					$("#dataTable").hide();
					$("#splitPage").html("");
					var msg = "<div class='alert alert-warning'>";
					msg += "<strong>什么都找不到 QAQ </strong>没有符合的数据。";
					msg += "</div>";
					$("#splitPage").append(msg);
					conmmonSlide("splitPage");
					flag = 1;

				}else{
					if(flag == 1){
						$("#splitPage").html("");

					}
					flag = 0;
				}
			}

		});
		if(flag == 1){
			return;
		}
	}



	//自定义一个隐藏的表单用于提交下载文件用的参数
	//定义一个form表单
	var form = $("<form>");
	//在form表单中添加查询参数
	form.attr('style', 'display:none');
	form.attr('target', '');
	form.attr('method', 'post');
	form.attr('action', excelUrl);

	var input1 = $('<input>');
	input1.attr('type', 'hidden');
	input1.attr('name', 'keyWord');
	input1.attr('value', keyword);
	//将表单放置在web中
	$('body').append(form);
	//将查询参数控件提交到表单上
	form.append(input1);
	form.submit();

}


//收缩列方法
function shrinkMethon(tableId){

	$(".shrinkMethon").click(function(){

		if($(this).find("span").length > 0){

			if($(this).find("span").first().attr("class") == "fa fa-chevron-circle-left"){

				$("#"+tableId+" tr").find("td:eq("+$(this).index()+")").each(function(){
					$(this).find("span").hide();
				});
				$(this).find("lable").hide();
				$(this).find("span").first().attr("class","fa fa-chevron-circle-right");
				shrinkStr += $(this).index()+";";
			}else{
				$("#"+tableId+" tr").find("td:eq("+$(this).index()+")").each(function(){
					$(this).find("span").show();
				});
				$(this).find("lable").show();
				$(this).find("span").first().attr("class","fa fa-chevron-circle-left");
				if(shrinkStr!=""){
					shrinkStr = shrinkStr.replace($(this).index()+";","");
				}
			}
		}
	});

	if(shrinkStr!=""){
		shrinkStr.split(";").forEach(function(e){

			if(e!=""){
				$("#"+tableId+" tr").find("td:eq("+e+")").each(function(){
					$(this).find("span").hide();
				});
				$("#"+tableId+" tr").find("th:eq("+e+")").find("lable").hide();
				$("#"+tableId+" tr").find("th:eq("+e+")").find("span").first().attr("class","fa fa-chevron-circle-right");
			}

		});
	}

}

//准备自动补全数据
function prepareData(url, keyword, id, datas, widthId){
	$.ajax({
		url:url,
		type:"POST",
		async: false,
		data:{
			keyWord:keyword
		},
		success:function(data){
			datas = eval(data);

		}
	});
	bindToInputHide(id,datas,widthId);
}

//将自动补全绑定到特定的input上
function bindToInputHide(id,datas,widthId){

	$("#"+id).unautocomplete();
	$("#"+id).autocomplete(datas, {
		max: 5,    //列表里的条目数
		minChars: 0,    //自动完成激活之前填入的最小字符
		width: $("#"+widthId).width(),     //提示的宽度，溢出隐藏
		scrollHeight: 100,   //提示的高度，溢出显示滚动条
		matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
		autoFill: false,    //自动填充
		formatItem: function(row, i, max) {

			return row.name ;
		},
		formatMatch: function(row, i, max) {
			return row.name + row.quanpin + row.jianpin;
		},
		formatResult: function(row,i,max) {
			return row.name;
		}
	}).result(function(event, row, formatted) {

	});
}


//计算日期
function getDate(time,id1,id2){
	var str1 = "";
	var str2 = "";
	if(time == "yesterday"){
		//昨天
		var getYesterdayDate = new Date(nowYear, nowMonth, nowDay - 1);
		str1 =  formatDate(getYesterdayDate);
		str2 =  formatDate(getYesterdayDate);
	}else if(time == "today"){
		//今天
		var getCurrentDate = new Date(nowYear, nowMonth, nowDay);
		str1 = formatDate(getCurrentDate);
		str2 = formatDate(getCurrentDate)
	}else if(time == "lastWeek"){
		//获得上周的开始日期
		var getUpWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -7);
		str1 = formatDate(getUpWeekStartDate);
		//获得上周的结束日期
		var getUpWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 7));
		str2 = formatDate(getUpWeekEndDate);
	}else if(time == "thisWeek"){
		//获得本周的开始日期
		var getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
		str1 = formatDate(getWeekStartDate);
		//获得本周的结束日期
		var getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
		str2 = formatDate(getWeekEndDate);
	}else if(time == "lastMonth"){
		//获得上月开始时间
		var getLastMonthStartDate = new Date(nowYear, lastMonth, 1);
		str1 = formatDate(getLastMonthStartDate);
		//获得上月结束时间
		var getLastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
		str2 = formatDate(getLastMonthEndDate);
	}else if(time == "thisMonth"){
		//获得本月的开始日期
		var getMonthStartDate = new Date(nowYear, nowMonth, 1);
		str1 =  formatDate(getMonthStartDate);
		//获得本月的结束日期
		var getMonthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
		str2 = formatDate(getMonthEndDate);
	}

	if(id1 != "null"){
		$("#"+id1).val(str1);
	}

	if(id2 != "null"){
		$("#"+id2).val(str2);
	}

}


//格式化日期：yyyy-MM-dd
function formatDate(date) {
	var myyear = date.getFullYear();
	var mymonth = date.getMonth()+1;
	var myweekday = date.getDate();

	if(mymonth < 10){
		mymonth = "0" + mymonth;
	}
	if(myweekday < 10){
		myweekday = "0" + myweekday;
	}
	return (myyear+"-"+mymonth + "-" + myweekday);
}

//获得某月的天数
function getMonthDays(myMonth){
	var monthStartDate = new Date(nowYear, myMonth, 1);
	var monthEndDate = new Date(nowYear, myMonth + 1, 1);
	var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
	return days;
}

//获得本季度的开始月份
function getQuarterStartMonth(){
	var quarterStartMonth = 0;
	if(nowMonth<3){
		quarterStartMonth = 0;
	}
	if(2<6){
		quarterStartMonth = 3;
	}
	if(5<9){
		quarterStartMonth = 6;
	}
	if(nowMonth>8){
		quarterStartMonth = 9;
	}
	return quarterStartMonth;
}

//清空输入框
function getEmpty(id1,id2){
	if(id1 != "null"){
		$("#"+id1).val("");
	}

	if(id2 != "null"){
		$("#"+id2).val("");
	}
}

//判断是否选中
function judgeCheck(id){
	if($("#"+id+"").prop("checked")){
		return true;
	}else{
		return false;
	}
}

//选择表格或图表
function selectTableOrPicture(flag){
	if(flag == "table"){
		tableOrPicture = true;
	}else if(flag == "picture"){
		tableOrPicture = false;
	}
}


//画图
function drawing(url,keyword){


	$.ajax({
		url:url,
		type:"POST",
		data:{
			keyWord:keyword
		},
		async:false,
		success:function(data){
			// 基于准备好的dom，初始化echarts实例


			// 使用刚指定的配置项和数据显示图表。
			$("#groupTable").hide();
			$("#dataDiv").hide();
			$("#splitPage").html("");

			$("#drawDiv").width($("#groupDiv").width()*3/4);
			$("#drawDiv").height($("#groupDiv").width()/3);

			$("#drawDiv").css("margin-left", ($("#groupDiv").width()-$("#drawDiv").width())/2);

			$("#drawDiv").show();
			$("#groupDiv").show();
			var myChart = echarts.init(document.getElementById("drawDiv"));
			myChart.setOption(data);
		}
	});

}

//公共滑动
function conmmonSlide(id){
	var options = {};
	$( "#"+id ).effect( "slide", options, 500, callback );
}

function callback() {
/*	setTimeout(function() {
	$( "#splitPage" ).removeAttr( "style" ).hide().fadeIn();
	}, 1000 );*/
};

//休眠方法
function sleep(numberMillis) {
	var now = new Date();
	var exitTime = now.getTime() + numberMillis;

	while (true) {
		now = new Date();
		if (now.getTime() > exitTime){
			return;
		}
	}
}


// 获取URL参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

// 判断是否为空
function isNotNull(id) {
    if($("#"+id).val() != null && $("#"+id).val() != undefined && $("#"+id).val() != "" && $("#"+id).val().trim().length >0) {
        return true;
    }
    return false;
}

// 判断id对象是否存在
function isExistsId(id) {
    if($("#"+id).length>0) {
        return true;
    } else {
        return false;
    }
}

// 公共展示方法
function getAllByPage(url) {
    $.ajax({
        url:url,
        type:"POST",
        async:false,
        data:{
            startPage:startPage
        },
        success:function(data) {

            data = eval("(" + data + ")");

            var list = data["list"];
            var str = "";

            for(var i = 0;i<list.length;i++) {

                str += "<tr id=tr"+list[i]["id"]+" class='even gradeA'>";

                $("#titleColumn").find("lable").each(function() {

                    var col = $(this).attr("name");

                    var td = "<td>";

                    if(col == "operation") {

                    	var dataStr = JSON.stringify(list[i]);

                        td += "<a href='javascript:void(0);' onclick=updateThis('"+dataStr+"')>修改</a> | ";
                        td += "<a href='javascript:void(0);' onclick=deleteThis('"+dataStr+"')>删除</a>";

                    } else if(col == "currentState") {

                        if(list[i][col] == "0") {
                            td += "禁用";
                        } else if(list[i][col] == "1") {
                            td += "可用";
                        }
                    } else {
                        //如果数据为空则写无
                        if((list[i][col] == undefined) || (list[i][col] == null) || (list[i][col] == "null")) {
                            td += "无";
                        } else {
                            td += list[i][col] + "";
                        }
                    }

                    td += "</td>";

                    str += td;
                });
                str += "</tr>";
            }

            $("#dataBody").append(str);
        }

    });
}

// 获得当前数据总数量和分页数据
function getTotalCountAndPageSize(url) {
    $.ajax({
        url:url,
        type:"POST",
        async:false,
        success:function(data){

            data = eval("(" + data + ")");

            totalCount = data["totalCount"];

            pageSize = data["pageSize"];

            if(totalCount <= pageSize) {
                totalPage = 1;
            } else if(totalCount % pageSize == 0) {
                totalPage = totalCount/pageSize;
            } else {
                totalPage = parseInt(totalCount/pageSize);
            }

        }

    });
}

// 储存机器
function saveThis(saveUrl, returnUrl) {

    $("#dataInfo").find("span").each(function() {

        var col = $(this).attr("name");
        if(col != "id") {
            json[col] = $("#"+col).val();
        }
    });
    var paramStr = JSON.stringify(json);

    $.ajax({
        url:saveUrl,
        type:"POST",
        async:false,
        data:{
            paramStr:paramStr
        },
        success:function(data){

            if(data == "success") {
                window.location.href = returnUrl;
            }
        }

    });
}

// 修改
function updateThis(updateUrl, returnUrl) {

    $("#dataInfo").find("span").each(function() {
        var col = $(this).attr("name");
        json[col] = $("#"+col).val();
    });
    var paramStr = JSON.stringify(json);

    $.ajax({
        url:updateUrl,
        type:"POST",
        async:false,
        data:{
            paramStr:paramStr
        },
        success:function(data){

            if(data == "success") {
                window.location.href=returnUrl;
            }
        }

    });
}

// 根据id和No获得数据信息
function  getDataByInfo(url, id, dataNo) {
    $.ajax({
        url:url,
        type:"POST",
        async:false,
        data:{
            id:id,
            dataNo:dataNo
        },
        success:function(data){

            var info = eval("(" + data + ")");

            $("#dataInfo").find("span").each(function() {

                var col = $(this).attr("name");
                $("#"+col).val(info[col])
            });
        }
    });
}

