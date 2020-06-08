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

.backImg{
    display: table-column;
    position: inherit;
	background-blend-mode: hard-light;
    background-image: url(/tour/images/cartoon-wave.jpg);
    background-size: cover;
    opacity: 0.5;
    z-index: -1;
}


.allInputBoxDiv{
	float:right;
}

.sendToAdminTitle{
	text-align: center;
	text-decoration: underline;
}

.backgroundIMG{
	position: relative;
    opacity: 0.2;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	
	
	<section class="sendToAdminBoxSection">
	
	<div class="img-thumbnail backImg"></div>
	
	<h3 class="sendToAdminTitle">운영자에게 메일 보내기</h3>
		<form method="post">
		<div class="allInputBoxDiv">
			<div class="addrInputBoxDiv">
			<label><b>나의 이메일 주소:</b> </label>
			<input name="from_email">
			</div>
			
			<div class="addrInputBoxDiv" style="float:right;">
			<label><b>제목: </b></label>
			<input name="subject">
			</div>
		</div>
		
		<div>
			<textarea name="content" cols="100" rows="10"></textarea>
		</div>
		<div>
			<button class="btn btn-info">메일보내기</button>
		</div>
		</form>
	</section>
	<%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>