<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />

<script>
$(function() {

	$(document).on("click",".preply",function(){
		$(".replyreplyAtc").html("")
		let id=$(this).attr("id")
		let ufc_pid=$(this).data("ufc_pid")
		let ufc_fk=$(this).data("ufc_fk")
		$.ajax({
			url:"${rootPath}/fishUserWater/replyForm",
			data:{ufc_pid:ufc_pid, ufc_fk:ufc_fk},
			type:"GET",
			success:function(result){
				$("#"+id+"a").html("")
				$("#"+id+"a").html(result)
			}
		})
	})
	
})
</script>
<style>
.body{
border: 2px solid gray;

}
.replyreplyAtc{
border-bottom: 1px solid gray;
}
.for-coment{
background-color: #e6e9ed;
}
.ufc-username{
width: 15%;
}
.ufc-text{
width: 85%;
background-color: #f5f5f5;
}
.preply{
margin-left: 10px;
}
#d-flex{
display: flex;
}
</style>

<br/>
<hr/>
<section class="body">
<c:choose>
	<c:when test="${commentList!=null}">
		<div class="for-coment">
		<c:forEach items="${commentList}" var="vo">
			<section class="preply" id="${vo.ufc_id }" data-ufc_pid="${vo.ufc_id }" data-ufc_fk="${vo.ufc_fk }">
				<div id="d-flex">
					<div class="ufc-username p-2 flex-fill">id-${vo.ufc_id }, pid-${vo.ufc_pid } usr-${vo.ufc_username }</div>
					<div class="ufc-text p-2 flex-fill">${vo.ufc_text }</div>
				</div>
			</section>
			<article id="${vo.ufc_id }a" class="replyreplyAtc" >
			
			</article>
		</c:forEach>
		</div>
	</c:when>
</c:choose>
</section>

<section>
<%@ include file="/WEB-INF/views/include/cmtPagi.jsp" %>
</section>