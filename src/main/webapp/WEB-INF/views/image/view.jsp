		<%@ page language="java" contentType="text/html; charset=UTF-8"
		    pageEncoding="UTF-8"%>
		    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html>
		<html>
		<head>
		<meta charset="UTF-8">
		<title>일반 게시판 글보기</title>
		<script type="text/javascript">
		$(function(){
			 $('[data-toggle="tooltip"]').tooltip();
			 
			 //이벤트 처리 
			 $("#deleteBtn").click(function(){
				//경고창 뜨게 하는 - alert : 일반 경고 , confirm:확인/취소 ,prompt: 키로 입력 
			 	//확인 창이 나타는데 취소를 누르면 삭제 페이지 이동을 취소한다.
			 	
				if(!confirm("정말 삭제 하시겠습니까?")) return false;
			 });
		});
		</script>
		</head>
		<body>
		<div class="container">
			<h1>이미지 내용보기</h1>
			<div class="card">
		  		<div class="card-header"><b>${vo.no }. ${vo.title }</b></div>
		  		<div class="card-body">
				<div class="card" style="width:500px">
					<!-- card-img-top - width의 기본이 100% -->
					  <img class="card-img-top" src="${vo.fileName }" alt="Card image">
					    <div class="card-img-overlay">
					    	<c:if test="${login.id == vo.id }">
						    	<button type="button" class="btn btn-dark" data-toggle="modal" data-target="#changImageModal">
		 							 이미지 변경
								</button>
					    	</c:if>
						    <a href="${vo.fileName }" class="btn btn-info"download>다운로드</a>
						  </div>
					  <div class="card-body">
					    <p class="card-text"><pre>${vo.content }</pre></p>
					  </div>
					</div>
				</div>
		 		 <div class="card-footer">
		 		 	<span class="float-right">
		 		 	${vo.writeDate }</span>	 	
		 		 	${vo.name }(${vo.id })
		 		 </div>
		</div>
			<!-- a tag : 데이터를 클릭하면 href의 정보를 가져와서 페이지 이동시킨다. -->
			<c:if test="${!empty login && login.id == vo.id }">
			<a href="updateForm.do?no=${param.no }&page=${param.page }&perPageNum=${param.perPageNum}&key=${param.key}&word=${param.word}" 
			class="btn btn-primary" title="이미지를 제외한 정보를 수정할 수 있습니다."
			  data-toggle="tooltip" data-placement="top" id="updateBtn">수정</a>
			  <a href="delete.do?no=${vo.no }&deleteFileName=${vo.fileName}&&perPageNum=${param.perPageNum}" class="btn btn-danger" id="deleteBtn">삭제</a>
			</c:if>
			<a href="list.do?page=${param.page }&perPageNum=${param.perPageNum}&key=${param.key}&word=${param.word}" class="btn btn-info">리스트</a>
		
		</div>
		<!-- container의 끝 -->
		
		
		<!-- The Modal -->
		<div class="modal" id="changImageModal">
		  <div class="modal-dialog">
		    <div class="modal-content">
		
		      <!-- Modal Header -->
		      <div class="modal-header">
		        <h4 class="modal-title">이미지 변경하기</h4>
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		      </div>
			
			<form action="changeImage.do"  method="post" enctype="multipart/form-data">
			<!-- 숨겨서 넘겨야할 데이터 - 이미지 번호,현재 파일이름(삭제) -->
			<input name="no" value="${vo.no }" type="hidden">
			<input name="deleteFileName" value="${vo.fileName }" type="hidden">
		      <!-- 페이지 정보도 넘긴다. -->
			<input name="page" value="${param.page }" type="hidden">
			<input name="perPageNum" value="${param.perPageNum }" type="hidden">
			<input name="key" value="${param.key }" type="hidden">
			<input name="word" value="${param.word }" type="hidden">
		      <!-- Modal body -->
		      <div class="modal-body">
		        <div class="form-group">
				<label for="imageName">첨부 이미지</label> <input type="file"
				id="imageName" name="imageName" required class="form-control" >
				</div>

		      </div>
		
		      <!-- Modal footer -->
		      <div class="modal-footer">
		      	<button class="btn btn-dark">바꾸기</button>
		        <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
		      </div>
			</form>
		    </div>
		  </div>
		</div>
		
		</body>
		</html>