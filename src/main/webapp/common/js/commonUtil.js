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

// 用户编号
var userNo = "";

// 用户名
var userName = "";

// 用户游戏币数
var userCoin = "";

// 用户头像
var userImg = "";

// 用户邀请码
var invitationCode = "";

$(function(){

	// 设置div为屏幕高度
    $(".index-body-div").height($(window).height());

    // 设置div为屏幕宽度
    $(".index-body-div").width($(window).width());
});

//初始化页码
function initPage(num, step){

	$("#splitPage").html("");

	if(totalCount == 0){

		$("#dataDiv").hide();
		var msg = "<div class='alert alert-warning'>";
		msg += "<strong>什么都找不到 QAQ </strong>没有符合的数据。";
		msg += "</div>";
		$("#splitPage").append(msg);
        commonSlide("splitPage");
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

//根据页码获得数据
function getPageByNum(nowPage, page, totalPage, step) {
	if(page % step == 0) {

		var num = 0;
		if(page + step > totalPage) {
			num = totalPage;
		} else {
			num = page + step;
		}

		var str = "";
		str += "<ul class='pagination'>";
		str += "<li><a href='javascript:lastPage()'>&laquo;</a></li>";
		for(var i = page; i<=num; i++) {
			str += "<li id=page"+i+"><a href='javascript:getPage("+i+")'>"+i+"</a></li>";
		}
		str += "<li><a href='javascript:nextPage()'>&raquo;</a></li>";
		str += "</ul>";
		$("#splitPage").html("");
		$("#splitPage").append(str);

	} else if((page+1) % step == 0) {
		var num = 1;
		if((page + 1)-step > 0) {
			num = (page + 1)-step;
		}

		var lastNum = page +1;
		if(lastNum > totalPage) {
            lastNum = totalPage;
		}
		var str = "";
		str += "<ul class='pagination'>";
		str += "<li><a href='javascript:lastPage()'>&laquo;</a></li>";
		for(var i = num; i<=lastNum; i++) {
			str += "<li id=page"+i+"><a href='javascript:getPage("+i+")'>"+i+"</a></li>";
		}
		str += "<li><a href='javascript:nextPage()'>&raquo;</a></li>";
		str += "</ul>";
		$("#splitPage").html("");
		$("#splitPage").append(str);

	} else {
		$("#page"+nowPage).removeClass("active");
	}
	$("#page"+page).addClass("active");
    nowPage = page;
	return nowPage;
}

//下一页
function nextPageNum(nowPage,totalPage,step) {
	if(nowPage + 1 <= totalPage) {
		return getPageByNum(nowPage, (nowPage+1), totalPage, step);
	} else {
		return 1;
	}

}

//上一页
function lastPageNum(nowPage,totalPage,step) {
	if(nowPage-1>0) {
		return getPageByNum(nowPage, (nowPage-1), totalPage, step);
	} else {
		return 1;
	}
}

//导出excel
function downloadExcelUtil(url,excelUrl,keyword){

	if(url != "") {
		$.ajax({
			url:url,
			type:"POST",
			async:false,
			data:{
				keyWord:keyword
			},
			async:false,
			success:function(data) {
				if(data == 0) {
					$("#dataTable").hide();
					$("#splitPage").html("");
					var msg = "<div class='alert alert-warning'>";
					msg += "<strong>什么都找不到 QAQ </strong>没有符合的数据。";
					msg += "</div>";
					$("#splitPage").append(msg);
                    commonSlide("splitPage");
					flag = 1;

				} else {
					if(flag == 1) {
						$("#splitPage").html("");
					}
					flag = 0;
				}
			}
		});
		if(flag == 1) {
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

//准备自动补全数据
function prepareData(url, keyword, id, dataArray, widthId) {
	$.ajax({
		url:url,
		type:"POST",
		async: false,
		data:{
			keyWord:keyword
		},
		success:function(data){
            dataArray = eval(data);

		}
	});
	bindToInputHide(id,dataArray,widthId);
}

//将自动补全绑定到特定的input上
function bindToInputHide(id,dataArray,widthId) {

	$("#"+id).unautocomplete();
	$("#"+id).autocomplete(dataArray, {
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
function getDate(time,id1,id2) {

	var str1 = "";

	var str2 = "";

	if(time == "yesterday")  {

		//昨天
		var getYesterdayDate = new Date(nowYear, nowMonth, nowDay - 1);
		str1 =  formatDate(getYesterdayDate);
		str2 =  formatDate(getYesterdayDate);

	}else if(time == "today") {

		//今天
		var getCurrentDate = new Date(nowYear, nowMonth, nowDay);
		str1 = formatDate(getCurrentDate);
		str2 = formatDate(getCurrentDate);

	}else if(time == "lastWeek") {

		//获得上周的开始日期
		var getUpWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -7);
		str1 = formatDate(getUpWeekStartDate);
		//获得上周的结束日期
		var getUpWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 7));
		str2 = formatDate(getUpWeekEndDate);

	}else if(time == "thisWeek") {

		//获得本周的开始日期
		var getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
		str1 = formatDate(getWeekStartDate);
		//获得本周的结束日期
		var getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
		str2 = formatDate(getWeekEndDate);

	}else if(time == "lastMonth") {

		//获得上月开始时间
		var getLastMonthStartDate = new Date(nowYear, lastMonth, 1);
		str1 = formatDate(getLastMonthStartDate);
		//获得上月结束时间
		var getLastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
		str2 = formatDate(getLastMonthEndDate);

	}else if(time == "thisMonth") {

		//获得本月的开始日期
		var getMonthStartDate = new Date(nowYear, nowMonth, 1);
		str1 =  formatDate(getMonthStartDate);
		//获得本月的结束日期
		var getMonthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
		str2 = formatDate(getMonthEndDate);
	}

	if(id1 != "null") {
		$("#"+id1).val(str1);
	}

	if(id2 != "null") {
		$("#"+id2).val(str2);
	}

}


//格式化日期：yyyy-MM-dd
function formatDate(date) {
	var myYear = date.getFullYear();
	var myMonth = date.getMonth()+1;
	var myWeekDay = date.getDate();

	if(myMonth < 10){
        myMonth = "0" + myMonth;
	}
	if(myWeekDay < 10){
        myWeekDay = "0" + myWeekDay;
	}
	return (myYear+"-"+myMonth + "-" + myWeekDay);
}

//获得某月的天数
function getMonthDays(myMonth) {
	var monthStartDate = new Date(nowYear, myMonth, 1);
	var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    return (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
}

//获得本季度的开始月份
function getQuarterStartMonth() {

	var quarterStartMonth = 0;

	if(nowMonth < 3) {

		quarterStartMonth = 0;

	} else if(nowMonth < 6) {

		quarterStartMonth = 3;

	}else if(nowMonth < 9) {

		quarterStartMonth = 6;

	}else if(nowMonth>8) {
		quarterStartMonth = 9;
	}
	return quarterStartMonth;
}

//清空输入框
function getEmpty(id1,id2) {
	if(id1 != "null") {
		$("#"+id1).val("");
	}
	if(id2 != "null") {
		$("#"+id2).val("");
	}
}

//判断是否选中
function judgeCheck(id) {
	if($("#"+id+"").prop("checked")) {
		return true;
	}else {
		return false;
	}
}

//选择表格或图表
function selectTableOrPicture(flag)  {
	if(flag == "table"){
		tableOrPicture = true;
	}else if(flag == "picture"){
		tableOrPicture = false;
	}
}


//画图
function drawing(url,keyword) {

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
function commonSlide(id) {
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
function getAllByPage(url, startPage) {

    startPage = (startPage - 1) * pageSize;

    $.ajax({
        url:url,
        type:"POST",
        async:false,
        data:{
            startPage:startPage
        },
        success:function(data) {

            $("#dataBody").html("");

            if(totalPage == 0){
                $("#dataDiv").hide();
                return;
            }

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            var list = data["result"];
            var str = "";

            for(var i = 0; i<list.length; i++) {

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

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
                return;
            }

            totalCount = data["totalCount"];

            pageSize = data["pageSize"];

            if(totalCount <= pageSize) {

                totalPage = 1;

            } else if(totalCount % pageSize == 0) {

                totalPage = totalCount / pageSize;

            } else {

                totalPage = parseInt(totalCount / pageSize) + 1;
            }
        }
    });
}

// 储存
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

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
                return;
            }

			window.location.href = returnUrl;
        }

    });
}

// 修改
function updateThis(updateUrl, returnUrl) {

    $("#dataInfo").find("span").each(function() {
        var col = $(this).attr("name");
        json[col] = $("#"+col).val();
    });

    // 将json转换为字符串
    var paramStr = JSON.stringify(json);

    $.ajax({
        url:updateUrl,
        type:"POST",
        async:false,
        data:{
            paramStr:paramStr
        },
        success:function(data){

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                toiletCatMsg(data["result"], null);
                return;
            }

			window.location.href=returnUrl;
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

            if(typeof(data) == "string"){
                data = eval("("+data+")");
            }

            if(data["is_success"] != "success") {
                alert(data["result"]);
                return;
            }

            var info = data["result"];

            if(typeof(info) == "string"){
                info = eval("("+info+")");
            }

            $("#dataInfo").find("span").each(function() {

                var col = $(this).attr("name");
                $("#"+col).val(info[col])
            });
        }
    });
}

// 设置cookie
function setCookie(name,value)
{
    var Days = 3650;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

Date.prototype.format = function(format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
};

function checkSession() {
    // 用户编号
    userNo = sessionStorage["toiletCatUserNo"];

    if(userNo == undefined) {
        userNo = getQueryString("userNo");
        getUserInfoByUserNo(userNo);
    }
}

// 跳转到战利品页
function toUserToy() {
    window.location.href = "/toiletCat/userToy/userToy.html?userNo=" + userNo + "&type=gameRoom";
}

// 跳转到用户主页
function toUserIndex() {
    window.location.href = "/toiletCat/user/userIndex.html?type=gameRoom&userNo=" + userNo;
}

function toIndex() {
    window.location.href="/toiletCat/gameRoom/gameRoom.html?nowType=login&userNo="+userNo;
}

// 检查手机号
function checkMobileNo(id) {

    var numbers = /^1\d{10}$/;
    
    //获取输入手机号码
    var val = $("#"+id).val().replace(/\s+/g,"");

    if(!numbers.test(val) || val.length ==0) {
        return false;
    }

    return true;
}

function getUserInfoByUserNo(userNo) {
    $.ajax({
        url:"/toiletCat/api/user/getUserByUserNo.action",
        type:"POST",
        async:false,
		data:{
        	userNo:userNo
		},
        success:function(data) {
            // 转换数据
            if(typeof(data) == "string") {
                data = eval("("+data+")");
            }

            // 判断是否成功
            if(data["is_success"] != "success") {

                toiletCatMsg(data["result"],"toLoginPage()");
                return;
            }

            var user = data["result"];

            // 判断是否成功获取用户信息
            if(user == "fail") {
                console.info(user);
                return;
            }

            if(typeof(user) == "string") {
                user = eval("("+user+")");
            }

            // 赋值操作
            // 用户编号
            sessionStorage["toiletCatUserNo"] = user["userNo"];
            // 用户名
            sessionStorage["toiletCatUserName"] = user["userName"];
            // 用户游戏币数
            sessionStorage["toiletCatUserCoin"] = user["userCoin"];
            // 用户头像
            sessionStorage["toiletCatUserImg"] = user["userImg"];
            // 用户邀请码
            sessionStorage["toiletCatInvitationCode"] = user["invitationCode"];

        }
    });
}

function toLoginPage() {
	window.location.href = "/toiletCat/user/login.html?from=gameIndex&type=gameRoom&checkType=checkCode";
}


// 弹出提示框
function toiletCatMsg(msg, method) {
	var str = "	<div class='toiletCat-msg' >";

	str += "	</div>";
    str += "		<div class='toiletCat-msg-div'>";
    str += "			<div class='toiletCat-msg-alert'>";
    str += "				提示";
    str += "			</div>";
    str += "			<div class='toiletCat-msg-text'>";
    str += 					msg;
    str += "			</div>";
    str += "			<div class='toiletCat-msg-button' ontouchend='closeToiletCatMsg("+method+")'>";
    str += "				确定";
    str += "			</div>";
    str += "		</div>";
	$("body").append(str);
	$(".toiletCat-msg").height($(window).height());
}

// 关闭提示框
function closeToiletCatMsg(method) {
    $(".toiletCat-msg").remove();
    $(".toiletCat-msg-div").remove();

    if(method != null) {
    	eval(method);
	}
}