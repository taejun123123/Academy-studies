<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 폼</title>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.3/themes/base/jquery-ui.css">
 <script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>
<script type="text/javascript">
$(function(){
	   let now = new Date();
	   let startYear = now.getFullYear();
	   let yearRange = (startYear - 100) +":" + startYear ;
	//날짜 입력 설정 
	$(".datepicker").datepicker({
		//입력란의 데이터 포맷
		dateFormat: "yy-mm-dd",
		//월 선택 입력 추가
		changeMonth:true,
		//년 선택 입력 추가
		changeYear:true,
		//월 선택 때의 이름 - 원래는 영어가 기본
		 monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
		//달력의 요일 표시
		 dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
		//선택된 나이 값 계산 100살까지
	      yearRange: yearRange,
		//오늘까지만의 날짜를 선택 가능
	      maxDate : now,
	});
	
	
	$("#id").keyup(function(){ //keyup 키가 눌리면 
		let id = $("#id").val();
		console.log("id="+id);
		if(id.length < 3){
			// class 지정 
			$("#checkIdDiv").removeClass("alert alert-success alert alert-danger")
			.addClass("alert alert-danger");
			$("#checkIdDiv").text("아이디는 필수 입력 입니다.3글자 이상입니다.");
		}else{
			//서버에 가서 데이터를 확인한다 -> 결과를 jsp로 받는다.
			//("#checkIdDIV")에 넣을 문구를 가져 오도록 한다. 
			//ajax의 load 함수 사용 >>>문법 load(uri,data,function(){})		
			//callback 함수 - function(결과 , 상태-success/error,통신객체 )
			$("#checkIdDiv").load("/ajax/checkId.do?id="+id,function(result){
				
			if($("#checkIdDiv").text().indexOf("중복") >= 0){
				""
				$("#checkIdDiv").removeClass("alert alert-success alert alert-danger")
				.addClass("alert alert-danger");
			}else
				$("#checkIdDiv").removeClass("alert alert-success alert alert-danger")
				.addClass("alert alert-success");
			}); //checkId Ajax 처리의 끝 
		} // if else의 끝 
			
	});//keyup(function())의 끝
	//비밀번호와 비밀번호 확인의 이벤트 시작
	$("#pw, #pw2").keyup(function(){
		// 비밀번호 길이 체크
		let pw =$("#pw").val();
		let pw2 =$("#pw2").val();
		//비밀번호 체크 
		if(pw.length < 4){
			// 디자인 부분 적용 
		$("#pwDiv").removeClass("alert alert-success alert alert-danger")
		.addClass("alert alert-danger");
		//글자 오류 적용 
		$("#pwDiv").text("비밀번호는 필수 입력 입니다.4글자 이상입니다.");
		}else{
			// 디자인 부분 적용 
		$("#pwDiv").removeClass("alert alert-success alert alert-danger")
		.addClass("alert alert-success");
		//글자 오류 적용 
		$("#pwDiv").text("적당한 비밀번호가 잘 적용했습니다.");
		}
		if(pw2.length < 4){
			// 디자인 부분 적용 
			$("#pw2Div").removeClass("alert alert-success alert alert-danger")
			.addClass("alert alert-danger");
			//글자 오류 적용 
			$("#pw2Div").text("비밀번호는 필수 입력 입니다.4글자 이상입니다.");
		}else{
			if(pw != pw2){
				// 디자인 부분 적용 
				$("#pw2Div").removeClass("alert alert-success alert alert-danger")
				.addClass("alert alert-danger");
				//글자 오류 적용 
				$("#pw2Div").text("비밀번호와 비밀번호 확인이 일치해야 합니다..");
			}else{
				$("#pw2Div").removeClass("alert alert-success alert alert-danger")
				.addClass("alert alert-success");
				//글자 오류 적용 
				$("#pw2Div").text("비밀번호와 비밀번호 맞습니다.");
			}
		
		}
	});

	//비밀번호와 비밀번호 확인의 이벤트 끝
});//function() 끝
</script>

</head>
<body>
	<div class="container">
		<h2>회원가입 폼</h2>
		<form action="write.do" method="post" enctype="multipart/form-data">

			<div class="form-group">
				<label for="id">ID</label> <input id="id" name="id" required
					autocomplete="off" class="form-control" maxlength="20"
					pattern="^[a-zA-Z].[a-zA-Z0-9]{2,19}$"
					title="맨앞에 글자 영문자 뒤에는 영숫자 입력. 3~20자 입력"
					placeholder="아이디 입력 : 3자 이상 20자 이내">
			</div>
			<div id="checkIdDiv" class="alert alert-danger">아이디는 필수 입력
				입니다.3글자 이상입니다.</div>

			<div class="form-group">
				<label for="pw">PassWord</label><br> <input type="password"
					name="pw" id="pw" required pattern="^.{4,20}$"
					title="비밀번호는 4~20자로 작성하셔야 합니다." placeholder="비밀번호 입력"
					maxlength="20" class="textInput">
			</div>

			<div id="pwDiv" class="alert alert-danger">비밀번호는 필수 입력 입니다.4글자
				이상입니다.</div>

			<div class="form-group">
				<label for="pw2">PassWord Check</label><br> <input
					type="password" id="pw2" required pattern="^.{4,20}$"
					title="비밀번호는 4~20자로 작성하셔야 합니다." placeholder="비밀번호 확인"
					maxlength="20" class="textInput">
			</div>

			<div id="pw2Div" class="alert alert-danger">비밀번호는 확인은 필수 입력
				입니다.4글자 이상입니다.</div>

			<div class="form-group">
				<label for="name">Name</label><br> <input type="text" id="name" name="name"
					required pattern="^.{2,10}$" maxlength="10"
					title="한글로 2~10자로 이내로 입력" placeholder="이름 확인" class="textInput">
			</div>
			<div class="form-group">
			<label>Gender</label>
			<div class="form-check">
				<label class="form-check-label"> <input type="radio"
					class="form-check-input" name="gender" value="남자">Man
				</label>
			</div>
			<div class="form-check">
				<label class="form-check-label"> <input type="radio"
					class="form-check-input" name="gender" value="여자">Girl
				</label>
			</div>
			</div>
			<div class="form-group">
				<label for="birth">Birth</label> <input id="birth" name="birth" required class="form-control datepicker"
				autocomplete="off">
			</div>
			
			<div class="form-group">
				<label for="tel">Tel</label> <input id="tel" name="tel" required class="form-control"
				pattern="\d{3}-\d{3,4}-\d{4}"
		  		title="xxx-xxxx-xxxx으로 작성"
				placeholder="xxx-xxxx-xxxx">
			</div>
			<div class="form-group">
				<label for="mail">Email</label><br> <input type="text" name="email" id="email" required placeholder="xxx@xxxxx.xx" 	
				class="textInput">
			</div>	
			<div class="form-group">
				<label for="photoFile">사진 이미지</label><input type="file"
					id="imageName" name="imageName" required class="form-control">
			</div>	


			<!-- a tag : 데이터를 클릭하면 href의 정보를 가져와서 페이지 이동시킨다. -->
			<button class="btn btn-primary">등록</button>
			<button type="reset" class="btn btn-secondary">다시입력</button>
			<button type="button" onclick="history.back();"
				class="btn btn-warning">취소</button>
		</form>
	</div>
</body>
</html>