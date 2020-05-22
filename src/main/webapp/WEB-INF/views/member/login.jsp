<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
<section>
<form:form action="${rootPath }/login" modelAttribute="memberVO" method="post">
<p>
<b>user name:</b>
<form:input path="username"/>
<form:errors path="username" />
</p>
<p>
<b>password:</b>
<form:input id="pass1" path="password" type="password"/>
<form:errors path="password" />
</p>
<button id="btn-login">submit</button>
<a id="regiLink" href="${rootPath }/member/register">Sign Up</a>
<a id="findID" href="${rootPath}/member/findID">ID/비밀번호 찾기</a>
</form:form>
</section>
</body>
</html>