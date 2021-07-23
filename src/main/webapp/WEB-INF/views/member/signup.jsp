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

div>button{
	display:inline-block;
}
</style>
</head>

<body>
	<div class="container p-5 shadow bg-white rounded">
		<center>회원가입</center>
		<form action="/member/signupProc" method="post">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="row" id="inputBox">
						<div class="col-8">
							아이디 <input type=text name=id id="id">
						</div>
						<div class="col-4">
							<button type=button class="btn btn-dark w-100" id="duplCheck">중복검사</button>
						</div>
						<div class="col-8">
							비밀번호 <input type=password name=pw id="pw">
						</div>
						<div class="col-8">
							이름 <input type=text name=name id="name">
						</div>
						<div class="col-8">
							전화번호 <input type=text name=phone id="phone">
						</div>
						<div class="col-8">
							이메일 <input type=text name=email id="email">
						</div>
						<div class="col-8">
							우편번호 <input type="text" name="zipcode" id="postcode">
						</div>
						<div class="col-4">
							<button type="button" class="btn btn-dark w-100" id="search">찾기</button>
						</div>
						<div class="col-8">
							주소1 <input type="text" name="address1" id="address1"
								placeholder="주소를 입력해 주세요.">
						</div>
						<div class="col-8">
							주소2 <input type="text" name="address2" id="address2"
								placeholder="주소를 입력해 주세요.">
						</div>
					</div>
				</div>
				<div class="col-1"></div>
			</div>
			<div class="row">
				<div class="col-2"></div>
				<div class="col-8">
					<button class="btn btn-dark w-100" id="signup">가입하기</button>
				</div>
				<div class="col-2"></div>
			</div>
		</form>
	</div>
	<script>
       $("#duplCheck").on("click",function(){
          $.ajax({
             url:"/member/duplCheck",
             data:{id:$("#id").val()}
          }).done(function(resp){
             if(resp=="1"){
                alert("이미 사용중인 ID입니다.");
             }else{
                alert("사용가능한 ID입니다.");
             }
          })
       })
       
       document.getElementById("search").onclick = function () {
			new daum.Postcode({
				oncomplete: function (data) {
					let roadAddr = data.roadAddress; 

					document.getElementById('postcode').value = data.zonecode;
					document.getElementById("address1").value = roadAddr;
				}
			}).open();
		};
    </script>
</body>

</html>