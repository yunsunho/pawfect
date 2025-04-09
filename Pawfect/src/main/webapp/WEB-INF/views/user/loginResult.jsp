<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인 결과</title>
</head>
<body>

	<c:choose>
		<c:when test="${status == 'success'}">
			<script>
				const ref = document.referrer;
				if (ref && !ref.includes('/login')) {
					location.href = ref;
				} else {
					location.href = "/main";
				}
			</script>
		</c:when>

		<c:when test="${status == 'failure'}">
			<input type="hidden" name="msg" value="${message}" />
			<script>
				const msg = document.querySelector("input[name='msg']").value
						.trim();
				alert(msg);
				window.location.href = "/loginForm";
			</script>
		</c:when>
	</c:choose>

</body>
</html>
