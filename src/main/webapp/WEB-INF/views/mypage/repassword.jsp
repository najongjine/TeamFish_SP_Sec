<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<style>

h2{
text-align: center;
}

.mypage{
width: 500px;
border: 2px solid blue;
}

.my-info{
margin: 20px;
}

.my-text{
margin-left: auto;
margin-right: 0;
}

button{
margin-top: 10px;
}

</style>
</head>
<script>
$(document).ready(function() {
<<<<<<< HEAD
	$(document).on("click","#changePassBtn",function(){
		let password=$("#password").val()
		let re_password=$("#re_password").val()
		
		if(password==""){
=======
	$(document).on("click","#changepw",function(){
		let password=$("#password")
		let re_password=$("#re_password")
		
		if(password.val()==""){
>>>>>>> 65b58c37ebb9c0d8c39ad3bd4718a1813428faff
			alert("비밀번호를 입력하세요")
			password.focus()
			return false
		}
		if(re_password==""){
			alert("비밀번호 재입력을 입력하세요")
			re_password.focus()
			return false
		}
		if(password!=re_password){
			alert("비밀번호와 비밀번호 확인이 다릅니다")
			password.focus()
			return false
		}
		
		$("form").submit()
	})
})
</script>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
<%@ include file="/WEB-INF/views/include/include-mypage.jsp"%>

<section class="container mypage">
<h2>비밀번호 변경</h2>
	<div class="my-info">
		<form:form modelAttribute="memberVO" method="POST" class="list-group">
			
			<div class="list-group-item list-group-item-info">
			<label for="password">비밀번호 : </label>
			<form:input path="password" placeholder="비밀번호 입력" class="my-text" type="password"/>
			</div>
			
			<div class="list-group-item list-group-item-info">
			<label for="re_password">비밀번호 재입력 : </label>
			<input id="re_password" name="re_password" placeholder="비밀번호 재입력" class="my-text" type="password"/>
			</div>
			
<<<<<<< HEAD
			<button id="changePassBtn" type="button" class="btn btn-success">비밀번호 변경</button>
=======
			<button id="changepw" type="button" class="btn btn-success">비밀번호 변경</button>
>>>>>>> 65b58c37ebb9c0d8c39ad3bd4718a1813428faff
			
		</form:form>
	</div>
</section>

</body>
</html>