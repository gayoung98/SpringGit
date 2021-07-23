<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<style>
@import url(https://fonts.googleapis.com/css?family=Varela+Round);

* {
	box-sizing: border-box;
}

body {
	background-color: rgb(230, 183, 177);;
}

div {
	text-align: center;
}

.container {
	margin-top: 7%;
	padding-top: 7%;
	padding-bottom: 6%;
	font-family: 'Nanum Gothic', 'Malgun Gothic', sans-serif;
	width: 700px;
}

center {
	font-size: 40px;
	font-weight: 600;
}

div>input {
	width: 60%;
	height: 40px;
	margin-top: 3%;
}

.inputBox {
	margin-top: 6%;
}

.buttonBox {
	margin-top: 7%;
}

.buttonBox>div {
	display: inline-block;
}

div>button {
	width: 100%;
}
</style>
</head>

<body>
	<div class="container shadow bg-white rounded">
		<form action="/member/loginProc" method="post">
			<center>Login Box</center>
			<div class="row inputBox">
				<div class="col-1"></div>
				<div class="col-10">
					<input type="text" id="id" name="id" placeholder="ID를 입력하세요">
					<input type="password" id="pw" name="pw"
						placeholder="Password를 입력하세요">
				</div>
				<div class="col-1"></div>
			</div>
			<div class="row buttonBox">
				<div class="col-2"></div>
				<div class="col-4">
					<button id="login" class="btn btn-outline-danger">로그인</button>
				</div>
				<div class="col-4">
					<button type="button" id="signup" class="btn btn-outline-warning">회원가입</button>
				</div>
				<div class="col-2"></div>
			</div>
		</form>
	</div>
	<script>
    	$("#signup").on("click",function(){
    		location.href = "/member/signup";
    	})
    </script>
</body>

</html>