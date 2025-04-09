<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/findId.css" />
</head>
<body>

	<div class="find-container">
		<h2 class="find-title">비밀번호 찾기</h2>

		<form action="/user/findPwd" method="post"
			onsubmit="return showLoading(this);">
			<div class="form-group">
				<label for="userName">이름</label> <input type="text" id="userName"
					name="userName" required />
			</div>

			<div class="form-group">
				<label for="userId">아이디</label> <input type="text" id="userId"
					name="userId" required />
			</div>

			<div class="form-group">
				<label for="email">이메일</label> <input type="email" id="email"
					name="email" required />
			</div>

			<button type="submit" class="find-btn" id="submitBtn">비밀번호 찾기</button>

			<div class="links">
				<a href="/signup">회원가입</a> | <a href="/loginForm">로그인</a>
			</div>
		</form>

		<!-- 에러 모달 -->
		<c:if test="${not empty error}">
			<div id="errorModal" class="modal" style="display: block;">
				<div class="modal-content">
					<p>${error}</p>
					<button
						onclick="document.getElementById('errorModal').style.display='none'">확인</button>
				</div>
			</div>
		</c:if>
	</div>

	<script>
		function showLoading(form) {
			const btn = document.getElementById("submitBtn");
			btn.disabled = true;
			btn.innerHTML = '<span class="spinner"></span>';
			return true;
		}
	</script>

</body>
</html>
