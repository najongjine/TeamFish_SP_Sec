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
<script src="https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=l7xxe287b2ab163049a58075ca366a804bea"></script>
<script type="text/javascript" >
$(function(){
	var map;
    let mapx=${fishVO.mapx}
    let mapy=${fishVO.mapy}
    map=new Tmapv2.Map("map_div",{
      width:'90%',
      height:'500px',
      center:new Tmapv2.LatLng(mapy,mapx),
      zoom: 15
    })
    //map.setLanguage("EN",true); // 영문
    var marker = new Tmapv2.Marker({
		position: new Tmapv2.LatLng(mapy,mapx), //Marker의 중심좌표 설정.
		map: map //Marker가 표시될 Map 설정..
	})
	$("#daumMap").hide()
	$("#showDaumMap").click(function() {
		$("#daumMap").toggle()
	})
	
	
	$.ajax({
		url:"${rootPath}/marinelifeapi/marinedetailpage", type:'get',
		success:function(result){
			
			$('.marineBoxDiv').html(result)
			
		},error:function(){
			alert('서버 에러')
		}
	})
	
	
  })
</script>



<style>

.titleDiv{
	font-weight: bold;
	font-size: x-large;
	margin-top: 5%;
	border-bottom:2px solid darkblue;
}

.imgAndInfoBoxDiv{
	display: flex;
	padding: 10px;
	margin-top: 5%;
}

.InfoBox{

	    font-size: large;
	    display:grid;
	    padding: 10px;
}

.descriptionDiv{
	margin-top: 5%;
	font-size: large;
	font-weight: bold;
}


div.Info:after{
         content: "";
        display: block;
        width: 60px;
        border-bottom: 2px solid darkblue;

}


#daumMapButton{
	margin-top:5%;
}


#showDaumMap{
	font-weight: bold;
    font-size: x-large;
    background-color: darkblue;
    color: yellow;
    border: 5px solid darkblue;
    border-radius: 10px;
}

.mapAlertMSG{

margin-top:5%;
color: darkred;

}
</style>

<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	
	<section>

		
		<div class="titleDiv">
			<p>${fishVO.title}</p>
		</div>
		
		<div class="imgAndInfoBoxDiv">
			<div>
				<img src="${fishVO.firstimage}" >
			</div>
			<div class="InfoBox">
				<div class="Info">area code: ${fishVO.areacode}</div>
				<div class="Info">city code: ${fishVO.sigungucode}</div>
				<div class="Info">Addr 1: ${fishVO.addr1}</div>
				<div class="Info">Addr 2: ${fishVO.addr2}</div>
				<div class="Info">zipcode: ${fishVO.zipcode}</div>
				<div id="mapX" class="Info">${fishVO.mapy}</div>
				<div id="mapY" class="Info">${fishVO.mapx}</div>
				<div class="Info">mlevel: ${fishVO.mlevel}</div>
			</div>
			
		</div>
		
		<div style="padding: 10px;"><img src="${fishVO.firstimage2}"></div>

		<div class="descriptionDiv">${fishVO.overview}</div>
		
		
		
		
		
		<div style="text-align: right; margin-top:5%;">
		<c:if test="${fishVO.booktour!=null}">
			<p>Introduced in Text book :${fishVO.booktour}</p>
		</c:if>
		<c:if test="${fishVO.homepage!=null}">
			<p>Home Page: ${fishVO.homepage}</p>
		</c:if>
		<c:if test="${fishVO.tel!=null}">
			<p>tel: ${fishVO.tel}</p>
		</c:if>
		<c:if test="${fishVO.telname!=null}">
			<p>tel name: ${fishVO.telname}</p>
		</c:if>
		</div>
		
	</section>
	
	
	<div class="marineBoxDiv">
	
	
	
	</div>
	
	<section id="daumMapButton">
	<br/>
	<hr/>
	<button id="showDaumMap">use daum map(korean version)</button>    
    </section>
    
    <section id="daumMap">
    <br/>
    <iframe src="https://m.map.kakao.com/" width="100%" height="1000px"></iframe>
    </section>
	
	<section id="map_div">
	<hr/>
	
	<div class="mapAlertMSG">
	map: (it only shows in korean... sorry for inconvenience... for detailed explanation, please use google map 
	or navigation application on your gadget.)
	</div>
    </section>
    
    <%@ include file="/WEB-INF/views/chatbot.jspf" %>
</body>
</html>