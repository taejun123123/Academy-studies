<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	//DispatcherServlet을 걸치지 않고 들어오는 경우 
 	//uri가 없어서 request를 넘긴다.
 	if(session.getAttribute("uri")==null){
 		session.setAttribute("uri",  request.getRequestURI());  
 	}
 %>
    <!-- sitemesh 미적용 페이지 - 웹라이브러리 없음. -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404 Error</title>
<!-- Bootstrap 4 + jquery 라이브러리 등록 - CDN 방식 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- icon 라이브러리 등록 - Font Awesome 4 / google -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<style type="text/css">
#errorDiv>.row{
	padding: 5px;
	border-bottom: 1px dotted #aaa;
}
#errorDiv{
	padding: 10px
}

</style>
</head>
<body>
<div class="container">
		<div class="card">
			<div class="card-header">
				<h4>
					요청 자원 오류(404)
					</h4>
			</div>
			<div class="card-body" id="errorDiv">
			<div class="text-center">
			<i class="material-icons" style="font-size:48px;color:red">error</i>
			</div>
				<div class="row">
					<div class="col-md-3">요청 URI</div>
					<div class="col-md-9">${uri }</div> 
				</div>
				<div class="row">
					<div class="col-md-3">오류 메시지</div>
					<div class="col-md-9">요청하신 페이지의 주소는 존재하지 않거나 지원하지 않습니다.
						<p>다시 확인하시고 시대해 주세요.
					</div>
				</div>
				<div>
					<div class="row">
						<div class="col-md-3">조치 사항</div>
						<div class="col-md-9">
						요청하신 페이지의 주소를 확인하시고 다시 시대해 주세요.
						</div>
					</div>
				</div>
				<div class="card-footer">
					<a href="/board/list.do" class="btn btn-dark">일반 게시판으로 가기</a>
				</div>
			</div>
		</div>
		</div>	
</body>
</html>