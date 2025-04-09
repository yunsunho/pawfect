<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입 결과</title>
</head>
<body>

	<c:choose>
		<%-- 회원가입 성공 --%>
		<c:when test="${param.status == 'success'}">
			<script type="text/javascript">
				alert(decodeURIComponent("${param.message}"));
				window.location.href = "/loginForm"; // 로그인 페이지로 이동
			</script>
		</c:when>

		<%-- 회원가입 실패 --%>
		<c:when test="${param.status == 'failure'}">
			<script type="text/javascript">
				alert(decodeURIComponent("${param.message}"));
				window.location.href = "/signup"; // 회원가입 페이지로 이동
			</script>
		</c:when>

		<%-- 예외 처리 (status 값이 없을 경우) --%>
		<c:otherwise>
			<script type="text/javascript">
				alert("처리 중 오류가 발생했습니다. 메인 페이지로 이동합니다.");
				window.location.href = "/main";
			</script>
		</c:otherwise>
	</c:choose>

</body>
</html>
