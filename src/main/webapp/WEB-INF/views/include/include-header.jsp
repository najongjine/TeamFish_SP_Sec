<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />

<header class="jumbotron text-center">
	<a href="${rootPath }/"><h1>Korea Fishing</h1></a>
	<p>Welcome to Korea Fishing</p>
</header>
<script>
$(function() {
	$(document).on("click","a.logout",function(){
		if(confirm("logout??")){
			$.post("${rootPath}/logout",{${_csrf.parameterName}:"${_csrf.token}"},function(){
				document.location.replace("${rootPath}/")
			})
		}
	})
})
</script>
<nav>
<ul class="nav nav-pills nav-justified">
<li class="nav-item">
<a href="${rootPath }/fish/water?searchOption="" ">
		<p>Water Fishing</p>
	</a>
</li>

<li class="nav-item">
<a href="${rootPath }/fish/sea?searchOption="" ">
		<p>Sea Fishing</p>
	</a>
</li>

<li class="nav-item">
<a href="${rootPath }/mail ">
		<p>SendMail To Administrator</p>
	</a>
</li>

<sec:authorize access="isAnonymous()">
<li class="nav-item">
<a href="${rootPath }/member/login">Login</a>
</li>

<li class="nav-item">
<a href="${rootPath }/member/register">Sign Up</a>
</li>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
<li><a href="${rootPath}/chat">실시간채팅</a></li>
<li class="nav-item">
<a href="${rootPath}/mypage/view">
(마이페이지)
</a>
</li>

<form:form>
<li class="nav-item">
<a href="javascript:void(0)" class="logout">
(click to logout)</a>
</li>
</form:form>
</sec:authorize>
</ul>
</nav>