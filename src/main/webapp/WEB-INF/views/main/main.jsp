<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
<link rel="stylesheet" href="/css/main.css">
<script type="text/javascript" src="/js/main.js"></script>
</head>
<body>
<div class="container">
<h2>짱짱 닷컴</h2>
<div class="row">
	<!-- 한줄을 유지 되는 것 정의 - col-해상도-6: 해상도 md-중간, lg-큰, xl-큰 -->
  <div class="col-md-6 module">
  	<jsp:include page="noticeList.jsp"/>
  </div>
  <div class="col-md-6 module">
  	<jsp:include page="boardList.jsp"/>
  </div>
</div>
<div class="row">
  <div class="col-md-12 module">
  	<jsp:include page="imageList.jsp"/>
  </div>
</div>
</div>
</body>
</html>