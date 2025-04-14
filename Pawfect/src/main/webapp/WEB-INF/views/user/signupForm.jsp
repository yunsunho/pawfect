<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/signup.css">
<style>
.signup-2col {
	display: flex;
	gap: 40px;
	flex-wrap: wrap;
}

.signup-left, .signup-right {
	flex: 1;
	min-width: 300px;
}

@media ( max-width : 768px) {
	.signup-2col {
		flex-direction: column;
	}
}
</style>
</head>
<body>

	<div class="signup-container">
		<h2>회원가입</h2>

		<form action="/signup" method="post"
			onsubmit="return validateSignupForm();">
			<div class="signup-2col">
				<!-- 왼쪽 컬럼 -->
				<div class="signup-left">
					<!-- 아이디 -->
					<div class="form-group">
						<label for="userId"> <span class="required-star">*</span>
							아이디 <span id="id-status-icon" class="status-icon"></span>
						</label>
						<div class="input-with-btn">
							<input type="text" name="userId" id="userId" required
								autocomplete="off">
							<button type="button" onclick="checkId()">중복확인</button>
						</div>
						<ul class="check-list" id="id-check-list" style="display: none;">
							<li id="id-condition-length" class="invalid">4~12자 길이</li>
							<li id="id-condition-pattern" class="invalid">영문 또는 숫자만 가능</li>
						</ul>
						<div id="check-result" class="result-message"></div>
					</div>

					<!-- 비밀번호 -->
					<div class="form-group">
						<label for="pwd"> <span class="required-star">*</span>
							비밀번호 <span id="pwd-status-icon" class="status-icon"></span>
						</label> <input type="password" name="pwd" id="pwd" required
							autocomplete="off">
						<ul class="check-list" id="pw-check-list" style="display: none;">
							<li id="pwd-condition-length" class="invalid">8자 이상</li>
							<li id="pwd-condition-pattern" class="invalid">영문, 숫자, 특수문자
								포함</li>
						</ul>
					</div>

					<!-- 비밀번호 확인 -->
					<div class="form-group">
						<label for="pwdCheck"> <span class="required-star">*</span>
							비밀번호 확인 <span id="pwdCheck-status-icon" class="status-icon"></span>
						</label> <input type="password" id="pwdCheck" required autocomplete="off">
						<ul class="check-list" id="pw-match-check-list"
							style="display: none;">
							<li id="pwd-match-condition" class="invalid">비밀번호 일치</li>
						</ul>
					</div>

					<!-- 이메일 -->
					<div class="form-group">
						<label for="email"> <span class="required-star">*</span>
							이메일 <span id="email-status-icon" class="status-icon"></span>
						</label>
						<div class="input-with-btn">
							<input type="email" name="email" id="email" required
								autocomplete="off">
							<button type="button" id="emailAuthBtn" onclick="sendEmailAuth()">인증</button>
							<div id="email-spinner" class="spinner" style="display: none;"></div>
						</div>
						<div id="email-result" class="result-message"></div>
					</div>

					<!-- 이메일 인증 코드 -->
					<div class="form-group" id="auth-code-section"
						style="display: none;">
						<label for="emailCode"><span class="required-star">*</span>
							인증 코드 입력</label>
						<div class="input-with-btn">
							<input type="text" id="emailCode" placeholder="인증 코드를 입력하세요"
								autocomplete="off">
							<button type="button" class="check-btn"
								onclick="verifyEmailCode()">확인</button>
							<div id="email-timer" class="timer-message"></div>
						</div>
						<div id="email-verify-result" class="result-message"></div>
					</div>
				</div>

				<!-- 오른쪽 컬럼 -->
				<div class="signup-right">
					<!-- 이름 -->
					<div class="form-group">
						<label for="userName"> <span class="required-star">*</span>
							이름 <span id="userName-status-icon" class="status-icon"></span>
						</label> <input type="text" name="userName" id="userName" required
							autocomplete="off">
						<ul class="check-list" id="name-check-list" style="display: none;">
							<li id="name-condition-korean" class="invalid">한글 1~6자</li>
						</ul>
					</div>

					<!-- 닉네임 -->
					<div class="form-group">
						<label for="userNickname"> <span class="required-star">*</span>
							닉네임 <span id="userNickname-status-icon" class="status-icon"></span>
						</label> <input type="text" name="userNickname" id="userNickname" required
							autocomplete="off" maxlength="10">
						<div class="nickname-warning" style="display: none;">
							<span class="text-red">*</span> 부적절한 단어나 욕설이 포함될 경우, 운영자에 의해 이용이
							제한될 수 있습니다.
						</div>
					</div>

					<!-- 전화번호 -->
					<div class="form-group tel-group">
						<label>전화번호</label>
						<div class="tel-input">
							<input type="text" id="userTel1" maxlength="3"> - <input
								type="text" id="userTel2" maxlength="4"> - <input
								type="text" id="userTel3" maxlength="4">
						</div>
						<input type="hidden" name="userTel" id="userTel">
					</div>

					<!-- 반려동물 정보 -->
					<div class="form-group">
						<label for="petName">반려동물 이름</label> <input type="text"
							name="petName" id="petName" maxlength="10">
					</div>

					<div class="form-group">
						<label for="petType">반려동물 종류</label> <select name="petType"
							id="petType">
							<option value="" disabled selected>선택</option>
							<option value="1">강아지</option>
							<option value="2">고양이</option>
							<option value="3">기타</option>
						</select>
					</div>
				</div>
			</div>

			<div class="form-actions">
				<button type="button" class="cancel-btn"
					onclick="location.href='/main'">가입취소</button>
				<button type="submit" id="signupBtn" class="submit-btn">회원가입</button>
			</div>
		</form>
	</div>

	<!-- 공통 모달 -->
	<div id="commonModal" class="modal">
		<div class="modal-content">
			<p id="modalMessage"></p>
			<div class="modal-buttons">
				<button onclick="closeModal()">확인</button>
			</div>
		</div>
	</div>

	<script src="/js/signup.js"></script>

	<button id="homeBtn" class="home-btn" onclick="location.href='/main'"
		title="홈으로">
		<span class="home-icon">🏠</span>
	</button>
</body>
</html>