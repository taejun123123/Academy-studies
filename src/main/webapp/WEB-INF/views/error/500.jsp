<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>500 Error</title>
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
					처리 프로세스 오류(500)
					</h4>
			</div>
			<div class="card-body" id="errorDiv">
			<div class="text-center">
			<i class="material-icons" style="font-size:48px;color:red">error</i>
			</div>
				<div class="row">
					<div class="col-md-3">오류 객체</div>
					<div class="col-md-9">${e.getClass().simpleName }</div>
				</div>
				<div class="row">
					<div class="col-md-3">오류 메시지</div>
					<div class="col-md-9">${e.message }</div>
				</div>
				<div>
					<div class="row">
						<div class="col-md-3">조치 사항</div>
						<div class="col-md-9">
							<b>오류로 인해서 불편을 드려 ㅈㅅ 다시 한번 시도해 주세요.<br>
								오류가 계속 발생하면 전산 담당자에게 연락해주세요.
								</b>
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