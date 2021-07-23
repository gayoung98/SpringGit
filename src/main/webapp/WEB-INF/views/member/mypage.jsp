<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SignUp</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<style>
@import url(https://fonts.googleapis.com/css?family=Varela+Round);

* {
	box-sizing: border-box;
}

.container {
	text-align: center;
	margin-top: 7%;
	width: 700px;
	font-family: 'Nanum Gothic', 'Malgun Gothic', sans-serif;
	font-weight: 600;
}

body {
	background-color: rgb(230, 183, 177);
}

center {
	font-size: 30px;
	margin-top: 5%;
	margin-bottom: 3%;
}

input {
	float: right;
}

#inputBox>div {
	margin-top: 6%;
}

#signup {
	margin-top: 10%;
}

div>button {
	display: inline-block;
}
</style>
</head>

<body>
	<c:choose>
		<c:when test="${loginId != null }">
			<c:forEach var="loginId" items="${mypage }">
			<div class="container p-5 shadow bg-white rounded">
				<center>마이 페이지</center>
				<form action="/member/signupProc" method="post">
					<div class="row">
						<div class="col-1"></div>
						<div class="col-8">
							<div class="row" id="inputBox">
								<div class="col-9">
									아이디 
								</div>
								 <div class="input">${loginId.id}</div>
								<div class="col-9">
									이름 :
								</div>
									<div class="input">${loginId.name}</div>
								<div class="col-9">
									전화번호 :
								</div>
									<div class="input">${loginId.phone}</div>
								<div class="col-8">
									이메일 :
								</div>
									<div class="input">${loginId.email}</div>
								<div class="col-9">
									우편번호 :
								</div>
									<div class="input">${loginId.zipcode}</div>
								<div class="col-9">
									주소1 :
								</div>
									<div class="input">${loginId.address1}</div>
								<div class="col-9">
									주소2 :
								</div>
									<div class="input">${loginId.address2}</div>
							</div>
						</div>
						<div class="col-1"></div>
					</div>

					<br>
					<hr>
					<div class="row">

						<div class="col-2"></div>
						<div class="col-8">
							<button class="btn btn-dark w-100" id="signup">비밀번호 수정</button>
							<br><br>
							<button class="btn btn-dark w-100">
								<a href="/">뒤로 가기</a>
							</button>

						</div>

						
					</div>

				</form>
			</div>
			</c:forEach>
		</c:when>
	</c:choose>

</body>

</html>