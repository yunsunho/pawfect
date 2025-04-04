<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 - Pawfect</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/style.css">
</head>
<body>
    <div class="login-container">
        <h2>로그인</h2>
        <form action="<%= request.getContextPath() %>/user/login" method="post">
            <label for="userId">아이디</label>
            <input type="text" id="userId" name="userId" required>

            <label for="pwd">비밀번호</label>
            <input type="password" id="pwd" name="pwd" required>

            <button type="submit">로그인</button>
        </form>

        <div class="links">
            <a href="<%= request.getContextPath() %>/user/findIdForm.jsp">아이디 찾기</a> |
            <a href="<%= request.getContextPath() %>/user/findPwForm.jsp">비밀번호 찾기</a>
        </div>

        <div class="signup">
            <p>아직 회원이 아니신가요?</p>
            <a href="<%= request.getContextPath() %>/user/signup.jsp" class="signup-btn">회원가입</a>
        </div>
    </div>
</body>
</html>
