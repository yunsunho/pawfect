<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원가입</title>
  <link rel="stylesheet" href="/css/signup.css">
</head>
<body>

<div class="signup-container">
  <h2>회원가입</h2>

  <!-- 📌 조건 안내 박스 -->
  <div class="condition-box">
    <p>📌 <strong>아이디</strong>: 4~12자의 영문자 또는 숫자만 사용 가능합니다.</p>
    <p>📌 <strong>비밀번호</strong>: 8자 이상이며 <strong>영문 + 숫자 + 특수문자</strong>를 포함해야 합니다.</p>
  </div>

  <form action="/user/signup" method="post" onsubmit="return validateSignupForm();">
    <!-- 아이디 -->
    <div class="form-group">
      <label for="userId"><span class="required-star">*</span> 아이디</label>
      <div class="input-with-btn">
        <input type="text" name="userId" id="userId" required>
        <button type="button" onclick="checkId()">중복확인</button>
      </div>
      <div id="check-result" class="result-message"></div>
    </div>

    <!-- 비밀번호 -->
    <div class="form-group">
      <label for="pwd"><span class="required-star">*</span> 비밀번호</label>
      <input type="password" name="pwd" id="pwd" required oninput="checkPasswordMatch()">
    </div>

    <div class="form-group">
      <label for="pwdCheck"><span class="required-star">*</span> 비밀번호 확인</label>
      <input type="password" id="pwdCheck" required oninput="checkPasswordMatch()">
      <div id="pwd-result" class="result-message"></div>
    </div>

    <!-- 이메일 인증 -->
    <div class="form-group">
      <label for="email"><span class="required-star">*</span> 이메일</label>
      <div class="input-with-btn">
        <input type="email" name="email" id="email" required>
        <button type="button" onclick="sendEmailAuth()">인증</button>
      </div>
      <div id="email-result" class="result-message"></div>
    </div>

    <div class="form-group" id="auth-code-section" style="display: none;">
      <label for="emailCode">인증 코드 입력</label>
      <input type="text" id="emailCode" placeholder="인증 코드를 입력하세요">
      <button type="button" class="check-btn" onclick="verifyEmailCode()">확인</button>
      <div id="email-verify-result" class="result-message"></div>
    </div>

    <!-- 기타 정보 -->
    <div class="form-group">
      <label for="userName"><span class="required-star">*</span> 이름</label>
      <input type="text" name="userName" id="userName" required>
    </div>

    <div class="form-group">
      <label for="userNickname"><span class="required-star">*</span> 닉네임</label>
      <input type="text" name="userNickname" id="userNickname" required>
    </div>

    <div class="form-group">
      <label for="userTel">전화번호</label>
      <input type="text" name="userTel" id="userTel">
    </div>

    <div class="form-group">
      <label for="petName">반려동물 이름</label>
      <input type="text" name="petName" id="petName">
    </div>

    <div class="form-group">
      <label for="petType">반려동물 종류</label>
      <select name="petType" id="petType">
        <option value="1">강아지</option>
        <option value="2">고양이</option>
        <option value="3">기타</option>
      </select>
    </div>

    <!-- 버튼 -->
    <div class="form-actions">
      <button type="submit" class="submit-btn" id="signupBtn" disabled>회원가입</button>
      <button type="reset" class="cancel-btn">가입취소</button>
    </div>
  </form>
</div>

<script src="/js/signup.js"></script>

</body>
</html>
