var objects = null;
function TableLock(_id,_lock,_indexs,_width){
	this.id= _id;
	this.lock = _lock;
	this.indexs = _indexs;
	this.width = _width;
}

function groupbyorder(tableid){
	var indexs = [];
	var upindex = 0,uplock = null;
	$("#"+tableid).find("tr th").each(function(){
		var index = $(this).index();
		if(index - upindex == 1 && uplock == $(this).attr("lock")){
			var obj = indexs[indexs.length-1];
			var _indexs = obj.indexs;
			_indexs[_indexs.length] = index;
			obj.indexs = _indexs;
			indexs[indexs.length-1] = obj;
		}else{
			var obj = new TableLock(tableid+(indexs.length+1),$(this).attr("lock"),[index],0);
			indexs[indexs.length] = obj;
		}
		upindex = index;
		uplock = $(this).attr("lock");
	});
	//console.info(indexs);
	return indexs;
}
// 添加表格
function addtable(id,obj){
	// 开始创建table模型
	var trlength = $("#"+id).find("tbody tr").length;
	var trhtml = "";
	for (var i=0; i < trlength; i++) {
	trhtml +="<tr></tr>";
	}
	var addWidth = "",addClass = "",addLock = "";
	if(!obj.lock){
		addWidth = " style=\"width:"+obj.width+"\"";
		addClass = " lockdiv";
		addLock = " lock=\"true\"";
	}
	$("#"+id).before("<div class=\"locktable"+addClass+"\""+addWidth+"><table class='table table-striped table-bordered table-hover' id=\""+obj.id+"\""+addLock+"><thead><tr></tr></thead><tbody>"+trhtml+"</tbody></table></div>");
	// 开始填充数据
	for (var i=0; i < obj.indexs.length; i++) {
		var index = obj.indexs[i];
		var th = $("#"+id+" tr").find("th:eq("+index+")").clone(false);
		$("#"+obj.id).find("thead tr").append(th);
		for (var j=0; j < trlength; j++) {
			var td = $("#"+id).find("tbody tr:eq("+j+")").find("td:eq("+index+")").clone(false);
			$("#"+obj.id).find("tbody tr:eq("+j+")").append(td);
		}
	}
	syncevent();
}

//同步事件
function syncevent(){
	// 移入事件
	$(".locktable table tbody tr").bind("mouseover",function(){
		var index = $(this).index();
		addtabletrclass(index);
		// 移除事件
	}).bind("mouseout",function(){
		var index = $(this).index();
		deltabletrclass(index);
		// 点击事件
	}).bind("click",function(){
		var index = $(this).index();
		selected(index);
	});
}

// 表格tr移入样式 .mouseover
function addtabletrclass(index){
	for (var i=0; i < objects.length; i++) {
		$("#"+objects[i].id).find("tbody tr:eq("+index+")").addClass("mouseover");
	}
}

// 表格tr移除样式 .mouseover
function deltabletrclass(index){
	for (var i=0; i < objects.length; i++) {
		$("#"+objects[i].id).find("tbody tr:eq("+index+")").removeClass("mouseover");
	}
}

// 上次选中的tbody tr 序列,从0开始
var upindex = null;
// 表格tr选中样式  .selected
function selected(index){
	for (var i=0; i < objects.length; i++) {
		// 删除上次选中
		if(upindex!=null){
			$("#"+objects[i].id).find("tbody tr:eq("+upindex+")").removeClass("selected");
		}
		$("#"+objects[i].id).find("tbody tr:eq("+index+")").addClass("selected");

	}
	upindex = index;
}

//固定列
function lockMeth(divId,tableId,num,width){

	$("#"+divId).find("div").each(function(){
		$(this).remove();
	});

	// 隐藏原始表格
	$("#"+tableId).hide();
	// 分组锁定列
	objects = groupbyorder(tableId);
	// 设置分组后列的宽度
	objects[1].width = num;
	// 创建分组列表
	for (var i=0; i < objects.length; i++) {
		var obj = objects[i];
		addtable(tableId,obj);
	};
	$("#"+tableId+"2").css("width",width);
	//fireFox 兼容性问题
	$("#"+tableId+"2").css("max-width","none");
}