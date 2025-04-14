<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입 결과</title>
<link rel="stylesheet" href="/css/common.css">
</head>
<body>

<!-- 공통 모달 구조 -->
<div id="commonModal" class="modal">
	<div class="modal-content">
		<p id="modalMessage"></p>
		<button id="modalConfirmBtn">확인</button>
	</div>
</div>

<script>
function showModal(message, callback) {
	const modal = document.getElementById("commonModal");
	const msgBox = document.getElementById("modalMessage");
	const confirmBtn = document.getElementById("modalConfirmBtn");

	if (modal && msgBox && confirmBtn) {
		msgBox.innerText = message;
		modal.style.display ="block"; 

		const handler = () => {
			modal.style.display = "none";
			confirmBtn.removeEventListener("click", handler);
			if (typeof callback === "function") callback();
		};

		confirmBtn.addEventListener("click", handler);
	}
}
</script>

<c:choose>
	<c:when test="${param.status == 'success'}">
		<script>
			showModal(decodeURIComponent("${param.message}"), () => {
				window.location.href = "/loginForm";
			});
		</script>
	</c:when>

	<c:when test="${param.status == 'failure'}">
		<script>
			showModal(decodeURIComponent("${param.message}"), () => {
				window.location.href = "/signup";
			});
		</script>
	</c:when>

	<c:otherwise>
		<script>
			showModal("처리 중 오류가 발생했습니다. 메인 페이지로 이동합니다.", () => {
				window.location.href = "/main";
			});
		</script>
	</c:otherwise>
</c:choose>

</body>
</html>
