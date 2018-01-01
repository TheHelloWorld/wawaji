<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath() + "/";
%>

<img src="<%=path %>loan/vcode/vcodeImg.jsp" alt="看不清,换一张" id="codeImg" style="margin-right:5px;margin-bottom: -3px;cursor:pointer" onclick="random();"/>
<a href="javascript:void(0);" id="random" style="color:black" onclick="random();">看不清，换一张</a>

<script type="text/javascript">
	function random() {
		document.getElementById("codeImg").setAttribute("src","<%=path %>loan/vcode/vcodeImg.jsp?time="+Math.random());
	}
</script>
