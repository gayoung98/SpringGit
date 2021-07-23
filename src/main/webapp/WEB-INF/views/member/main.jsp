<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
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
	padding-top: 5%;
	padding-bottom: 5%;
	font-family: 'Nanum Gothic', 'Malgun Gothic', sans-serif;
	width: 700px;
	font-size: 40px;
	font-weight: 600;
}
.container>div{
    margin-top: 5%;
}

.container>div:hover{
    cursor: pointer;
    color: rgb(255, 242, 62);
}
</style>
</head>
<body>
	<div class="container shadow bg-white rounded">
		<div>Board</div>
		<div>MyPage</div>
		<div>LogOut</div>
		<div>SignOut</div>
	</div>
</body>
</html>