<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="/css/common.css">
<link rel="stylesheet" href="/css/login.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />s
</head>
<body>

	<div class="login-container">
		<h2 class="login-title">로그인</h2>

		<!-- 로그인 실패 메시지 -->
		<c:if test="${not empty message}">
			<div id="login-error-modal" class="modal show">
				<div class="modal-content">
					<p>${message}</p>
					<button
						onclick="document.getElementById('login-error-modal').style.display='none'">확인</button>
				</div>
			</div>
		</c:if>

		<form action="/login" method="post">
			<div class="form-group">
				<label for="userId">아이디</label> <input type="text" id="userId"
					name="userId" required>
			</div>

			<div class="form-group">
				<label for="pwd">비밀번호</label> <input type="password" id="pwd"
					name="pwd" required>
			</div>

			<button type="submit" class="login-btn">로그인</button>
		</form>

		<div class="login-links">
			<a href="/user/findId">아이디 찾기</a> | <a href="/user/findPwd">비밀번호
				찾기</a> | <a href="/signup">회원가입</a>
		</div>
	</div>
	<button id="homeBtn" class="home-btn" onclick="location.href='/main'"
		title="홈으로">
		<span class="home-icon"><i class="fa-solid fa-house"></i></span>
	</button>
</body>
<script src="/js/login.js"></script>
</html>
