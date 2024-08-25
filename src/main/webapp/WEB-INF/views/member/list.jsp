<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.dataRow:hover{
	background: #ddd;
	cursor: pointer;
}
</style>

<script type="text/javascript">
$(function(){
	
	$(".status").click(function(){
		let grade = $(this).closest(".dataRow").find(".grade").val();
		let status = $(this).closest(".dataRow").find(".status").data("data");
		
		if(status != '정상' && grade==9){
			
			return false;
		}
		
		
	});
	
	
	// 회원 한줄을 클릭하면 회원 정보보기로 이동 시키는 처리
	function dataRowClick(){
		alert("dataRow click");
	}
	
	// 이벤트처리
	$(".dataRow").on("click", function(){
		dataRowClick();
	});
	
	$(".grade, .status").parent()
	.on("mouseover", function(){
		// dataRow의 click 이벤트를 없앤다.
		$(".dataRow").off("click");
	})
	.on("mouseout", function(){
		// dataRow의 click 이벤트를 다시 설정해준다..
		$(".dataRow").on("click", function(){
			dataRowClick()
		});
	});
	
	$(".dataRow").on("change", ".grade, .status", function(){
		// alert("값이 바뀜");
		// this - select 태그 선택 .next() 다음 태그 - div 태그
		// 변경되었는지 알아내는 것 처리.
		let changeData =  $(this).val();
		let data = $(this).data("data");
		console.log("원래 데이터=" + data + ", 바꿀 데이터=" + changeData);
		if(changeData == data)
			$(this).next().find("button").prop("disabled", true);
		else
			$(this).next().find("button").prop("disabled", false);
	});

});
</script>
</head>
<body>
<div class="container">
	<h2>회원 리스트</h2>
	<table class="table">
		<thead>
			<tr>
				<th>사진</th>
				<th>아이디</th>
				<th>이름</th>
				<th>성별</th>
				<th>생년월일</th>
				<th>연락처</th>
				<th>이메일</th>
				<th>등급</th>
				<th>상태</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list }" var="vo">
			<tr class="dataRow">
				<td>
					<c:if test="${!empty vo.photo }">
						<img  src="${vo.photo }" style="width:30px; height:30px;">
					</c:if>
					<c:if test="${empty vo.photo }">
						<i class="fa fa-user-circle" style="font-size:30px"></i>
					</c:if>
				</td>
				<td class="id">${vo.id }</td>
				<td>${vo.name }</td>
				<td>${vo.gender }</td>
				<td>${vo.birth }</td>
				<td>${vo.tel }</td>
				<td>${vo.email }</td>
				<td>
						<form action="changeGrade.do">
						<input name="id" value="${vo.id}" type="hidden">
							<div class="input-group mb-3">
					 	 <select class="form-control grade" name="gradeNo" data-data="${vo.gradeNo }"  >
					  	<option value="1" ${(vo.gradeNo==1)?"selected":""}>일반회원</option> <!-- 데이터가 많으면 저렇게 써야됨 데이터가 한개면 jquery 가능 -->
					  	<option value="9" ${(vo.gradeNo==9)?"selected":""}>관리자</option>
					  	</select>
					  <div class="input-group-append">
					    <button class="btn btn-success grade" type="submit" disabled>변경</button>
					  </div>
					</div>
					</form>
				</td>
				
				<td>
					<form action="changeStatus.do">
						<input name="id" value="${vo.id}" type="hidden">
							<div class="input-group mb-3">
					 	 <select class="form-control status" name="status" data-data="${vo.status }">
					  	<option ${(vo.status=='정상')?"selected":""}>정상</option> <!-- 데이터가 많으면 저렇게 써야됨 데이터가 한개면 jquery 가능 -->
					  	<option ${(vo.status=='탈퇴')?"selected":""}>탈퇴</option>
					  	<option ${(vo.status=='휴면')?"selected":""}>휴면</option>
					  	<option ${(vo.status=='강퇴')?"selected":""}>강퇴</option>
					  	</select>
					  <div class="input-group-append">
					    <button class="btn btn-success status" type="submit" disabled>변경</button>
					  </div>
					</div>
					</form>				
				</td>
			</tr>
		</c:forEach>
		<tbody>
	</table>
</div>
</body>
</html>