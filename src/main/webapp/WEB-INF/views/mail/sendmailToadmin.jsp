<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<title>insert</title>
<script type="text/javascript">
/*$(function() {
	let text="${userVO.uf_text }"+""
	alert(text)
	$("#uf_text").val(text)
})*/
</script>



<style>


.sendToAdminBoxSection{

	display:table;
	margin: 10% auto;
	    border: 2px solid black;
    border-radius: 5px;
    padding: 10px;
}

.allInputBoxDiv{
	float:right;
}

.sendToAdminTitle{
	text-align: center;
	text-decoration: underline;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<section class="sendToAdminBoxSection">
	<h3 class="sendToAdminTitle">운영자에게 메일 보내기</h3>
		<form method="post">
		<div class="allInputBoxDiv">
			<div class="addrInputBoxDiv">
			<label>나의 이메일 주소: </label>
			<input name="from_email">
			</div>
			
			<div class="addrInputBoxDiv" style="float:right;">
			<label>제목: </label>
			<input name="subject">
			</div>
		</div>
		
		<div>
			<textarea name="content" cols="100" rows="10"></textarea>
		</div>
		<div>
			<button>메일보내기</button>
		</div>
		</form>
	</section>
	<%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>