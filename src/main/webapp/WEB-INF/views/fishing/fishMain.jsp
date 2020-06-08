<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<title>Detail</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<section>
	<a href="${rootPath }/fish/water?searchOption="" ">
		<img src="${rootPath }/images/water fishing.jpg" width="800" height="500">
		<p>Water Fishing</p>
	</a>
	<br/>
	<hr/>
	<br/>
	<a href="${rootPath }/fish/sea?searchOption="" ">
		<img src="${rootPath }/images/sea fishing.png" width="800" height="500">
		<p>Sea Fishing</p>
	</a>
	<br/>
	</section>
	<%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>