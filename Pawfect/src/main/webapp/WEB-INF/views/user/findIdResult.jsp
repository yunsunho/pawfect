<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>아이디 찾기 결과</title>
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/findId.css" />
</head>
<body>

	<div class="find-container">
		<h2 class="find-title">아이디 찾기 결과</h2>

		<div class="form-group" style="text-align: center;">
			<p>
				입력하신 이메일 <strong>${email}</strong>로
			</p>
			<p>회원님의 아이디를 전송하였습니다.</p>
			<p class="caution">※ 타인에게 노출되지 않도록 주의해주세요.</p>

			<div style="margin-top: 20px;">
				<a href="/loginForm" class="find-btn">로그인 하러 가기</a>
			</div>
		</div>
	</div>

</body>
</html>
