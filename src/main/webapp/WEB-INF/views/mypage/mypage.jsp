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
width: 650px;
border: 2px solid blue;
}

.my-info{
margin: 20px;
}

.my-text{
margin-left: auto;
margin-right: 0;
}

</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
<%@ include file="/WEB-INF/views/include/include-mypage.jsp"%>

<section class="container mypage">
<h2>회원정보</h2>
	<div class="my-info d-inline-flex justify-content-center">
		<form:form modelAttribute="memberVO" method="GET" class="list-group">
		
			<div class="list-group-item list-group-item-primary">
			<label for="username">ID : </label>
			<form:input path="username" readonly="true" class="my-text"/>
			</div>
			
			<!-- 
			<div class="list-group-item list-group-item-primary">
			<label for="password">비밀번호 : </label>
			<form:input path="password" readonly="true" class="my-text"/>
			</div>
			 -->
			
			<div class="list-group-item list-group-item-primary">
			<label for="email">E-mail : </label>
			<form:input path="email" readonly="true" class="my-text"/>
			</div>
			
			<div class="list-group-item list-group-item-primary">
			<label for="phone">Phone : </label>
			<form:input path="phone" readonly="true" class="my-text"/>
			</div>
			
			<div class="list-group-item list-group-item-primary">
			<label for="address">Address : </label>
			<form:input path="address" readonly="true" class="my-text"/>
			</div>
			
			<div class="list-group-item list-group-item-primary">
			<label for="point">Point : </label>
			<form:input path="point" readonly="true" class="my-text"/>
			</div>
			
		</form:form>
		
		<div class="">
			<img width="280px" src="${rootPath}/files/${memberVO.profile_pic}">
		</div>
	</div>
</section>

</body>
</html>