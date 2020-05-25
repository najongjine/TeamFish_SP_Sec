<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <c:set var="rootPath" value="${pageContext.request.contextPath}"/>
    








<style>


.marineListBoxDiv{
	background: whitesmoke;
	text-align: center;
	border-left: 2px solid mediumblue;
	border-right: 2px solid mediumblue;
    width: 90%;
    margin: 0 auto;
    flex-wrap: wrap;
    display: flex;
    padding: 0;
   
}

.listContent{

	width:150px;
	height:150px;
	margin: 0 auto;
	display: none;
}

.listContentTitle{
	background-color:white;
	border: 1px solid black;
	width:150px;
	height:150px;
	margin: 0 auto;
	font-weight: bold;
	display: none;
}

.listThumbnail{
	width:150px;
	height:150px;
}

.img-thumbnail{
	border:none;
}

.allCoverBoxDiv{

	display: flex;
}

.prevBtn{
	display:flex;
}

.nextBtn{
	display:flex;
}

.inputCollectionDiv{
	text-align: center;
	margin: 10px 0;
}

.inputClass{
	width: 1%;
	margin: 0 10px;
}

.nextBtnA{
	align-self: center;
	    font-weight: bold;
    font-size: xxx-large;
    color:  mediumblue;
}


.nextBtn:hover{
	cursor: pointer;
	background-color: mediumblue;
}

.nextBtn:hover .nextBtnA{
	color:white;
}



.prevBtnA{
	align-self: center;
	    font-weight: bold;
    font-size: xxx-large;
  	color:  mediumblue;
}

.prevBtn:hover{
	cursor: pointer;
	background-color: mediumblue;
}

.prevBtn:hover .prevBtnA{
	color:white;
}

.title{
	display: none;
}

.description{
	display:none;
}

.descriptionAtTitle{
	display:none;
}

.tooltip-inner{
	background-color: mediumblue;
}

.marineBoxTitle{
	font-size: xx-large;
	font-weight: bold;
	margin: 50px 50px;
}




.chatBoxDiv{
	display: block;
	
}


.flexDiv{
	display: flex;
}

.connectUserDiv{
	border: 1px solid black;
	width: 200px;
	height: 400px;
}

.chatBox{
	border: 1px solid black;
	width: 400px;
	height: 400px;
}

.chat{
	color:red;
}



</style>



    
<script>
$(function(){
	
	
	var marineList
	
	

	// ajax로 List 불러오기
	
	
	//Math.floor(): 소수점 버림
	//parseFloat
	// 파싱안됨 NaN뜸
	var mapX = $('#mapX').text()
	var mapY = $('#mapY').text()
	

	var xIndex = mapX.indexOf('.')
	var resultX = mapX.substring(0,xIndex).replace(" ", "")

	
	var YIndex = mapY.indexOf('.')
	var resultY = mapY.substring(0,YIndex).replace(" ", "")
	
	
	var dataPerPage = 10 // 한 페이지에 나타낼 데이터 수
	var pageCount = 5 // 한 화면에 나타낼 페이지 수
	var totalPage// 총 페이지 수
	var curPage = 1
	

		
	
	
		
		var lengthVal
		
		
			$.ajax({
				
				url:"${rootPath}/marinelifeapi/getXYmarine", data:{mapX:resultX, mapY:resultY}, type:'get',
				success:function(result){
					// 리스트 넘어옴
					
					
					marineList = result
					
					
					
					
					$.each(marineList,function(i){
					
						
						//alert(i)
						
						
						// 제목만 제공하는 생물
						if(marineList[i].fullInfo == false){
							
							$('.marineListBoxDiv').append("<div id='id" + i + "'  class='listContentTitle' data-html='true' data-toggle='tooltip' title='"+ marineList[i].description +"'>" + marineList[i].title + "<div class='descriptionAtTitle' value='" + marineList[i].description + "'></div></div>")
							
							
						// 모든 정보 제공하는 생물 중에서		
						}else{
						
							// 섬네일이 있는 생물(정보 제공 완전체)
							if(marineList[i].thumbnail.length > 0 ){
							//$('.marineListBoxDiv').append("<div id='id" + i + "' class='listContent' data-html='true' data-toggle='tooltip' title='"+marineList[i].title + '<br>' + marineList[i].description +"'><img class='img-thumbnail listThumbnail' src='"  +  marineList[i].thumbnail  +"'/><div class='title' value='"+ marineList[i].title + "'></div><div class='description' value='"+ marineList[i].description + "'></div></div>")	
							$('.marineListBoxDiv').append("<div id='id" + i + "' class='listContent' data-html='true' data-toggle='tooltip' title='"+marineList[i].title + '<br>' + marineList[i].description +"'><img class='img-thumbnail listThumbnail' src='"  +  marineList[i].thumbnail  +"'/>")
							}
							// 섬네일이 없는 생물
							else{
								$('.marineListBoxDiv').append("<div id='id" + i + "' class='listContentTitle' data-html='true' data-toggle='tooltip' title='"+ marineList[i].description  +"'>" + marineList[i].title + "<div class='descriptionAtTitle' value='" + marineList[i].description + "'></div></div>")
								
							}
						
						}
						
						
						
						if(i < 10){
							var id = 'id'+i
							$('#'+id).css('display','block')
						}
						
						
					})
					
					$('[data-toggle="tooltip"]').tooltip();
					
					
					$('#marineListLength').val(marineList.length)
					
					var totalPage = Math.ceil(marineList.length/dataPerPage)
					
					$('#marineListTotalPage').val(totalPage)
					$('#currentPage').val(curPage)
					
					
					
				},error:function(){
					alert('서버 에러')
				}
				
			})


	// 다음 버튼 클릭됐을 때
	$('.nextBtn').click(function(){
			
	
		// 현재 페이지값이 0이상인가 그리고 totalPage 이하의 값인가
		if( parseInt($('#currentPage').val()) > 0 && parseInt($('#currentPage').val()) < parseInt($('#marineListTotalPage').val()) ){
			
			// 맞으면 currPage +1, 리스트 all display none, 해당 index의 리스트값만 display block
			
			$('.listContent').css('display','none')
			$('.listContentTitle').css('display','none')
			$('#currentPage').val( parseInt($('#currentPage').val()) + parseInt(1) )
			
			
			
			// 10개의 리스트 새로 display block
			var startIndex = (parseInt($('#currentPage').val()) - parseInt(1)) * 10 
			
			
			for(var i = 0; i < 10; i++){
				
				// 
				if( $( '#id'+(parseInt(startIndex)+parseInt(i))) ){
					$( '#id'+(parseInt(startIndex)+parseInt(i)) ).css('display', 'block')
				}else{
					break
				}
			}
			
		}
		
	})
	
	
	
	$('.prevBtn').click(function(){
			
	
		// 현재 페이지값이 2이상인가
		if(parseInt($('#currentPage').val()) > 1){
			
			// 맞으면 currPage -1, 리스트 all display none, 해당 index의 리스트값만 display block
			
			$('.listContent').css('display','none')
			$('.listContentTitle').css('display','none')
			$('#currentPage').val( parseInt($('#currentPage').val()) - parseInt(1) )
			
		
			
			// 10개의 리스트 새로 display block
			var startIndex = (parseInt($('#currentPage').val()) - parseInt(1)) * 10 
			
			
			for(var i = 0; i < 10; i++){
				
				
				if( $( '#id'+(parseInt(startIndex)+parseInt(i))) ){
					$( '#id'+(parseInt(startIndex)+parseInt(i)) ).css('display', 'block')
				}else{
					break
				}
			}
			
		}
		
	})
	
	
	
	
	
	
	
	

	

	
	
	
})
</script>


<div>

<div class="marineBoxTitle">이곳의 생태계는?</div>

<div class="allCoverBoxDiv">

	<div class="prevBtn">
	<div class="prevBtnA">&lt;</div>
	</div>
	
	<div class="marineListBoxDiv">
	
	
	</div>
	
	<div class="nextBtn">
	<div class="nextBtnA">&gt;</div>
	</div>

</div>

</div>

<div class="inputCollectionDiv">
<input id="marineListLength" type="hidden">
<input class="inputClass" id="currentPage" readonly="readonly">/
<input class="inputClass" id="marineListTotalPage" readonly="readonly">
</div>


