<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>


<script type="text/javascript">
	$(function() {
		$(".imageDiv").each(function() {
			
			let imgWidth = $(this).width();
			let imgHeight = imgWidth; // 4:4 비율은 정사각형이므로 너비와 높이가 동일
			$(this).height(imgHeight); // div 높이를 설정
			$(this).find("img").each(function() {
				$(this).width(imgWidth); // 이미지 너비를 div 너비와 동일하게 설정
				$(this).height(imgHeight); // 이미지 높이를 div 높이와 동일하게 설정
				$(".imageDiv").css("background","black");
			});
		});
	});
</script>

		<h4>이미지 게시판 리스트</h4>
		<c:if test="${empty ImageList }">
			<div class="jumbotron">
				<h3>데이터가 존재하지 않습니다.</h3>
			</div>
		</c:if>

		<c:if test="${!empty ImageList }">

			<div class="row">
				<!-- 이미지를 데이터가 있는 만큼 반복해서 하는 처리 시작부분 -->
				<c:forEach items="${ImageList }" var="vo" varStatus="vs">
					<!--  -->
					<!-- 줄바꿈처리 - 찍는 인덱스번호가 3의 배수이면 줄바꿈을 한다. -->
					<c:if test="${(vs.index !=0) && (vs.index % 3 == 0)}">
				${"</div>"} 
				${"<div class='row'>"} 
			</c:if>
					<!-- 데이터를 표시하는 곳 -->
					<div class="col-md-4 dataRow image imageLink">
						<div class="card">
							<div class="imageDiv">
								<img class="card-img-top" src="${vo.fileName }" alt="이미지없음">
							</div>
							<div class="card-body">
								<h5 class="card-title">
									<span class="float-right"> ${vo.writeDate } </span> ${vo.name }(${vo.id })
								</h5>
								<p class="card-text">
									<span class="no">${vo.no}</span> ${vo.title }
								</p>
							</div>
						</div>
					</div>
					<!-- 데이터를 표시하는 곳의 끝-->
				</c:forEach>
				<!-- 이미지를 데이터가 있는 만큼 반복해서 하는 처리 끝 부분 -->
			</div>
			<!-- 페이지 네이션 부분 -->
		</c:if>
