document.addEventListener("DOMContentLoaded", function() {
	const loginForm = document.querySelector("form");
	const userIdInput = document.getElementById("userId");
	const pwdInput = document.getElementById("pwd");

	// 폼 제출 전 유효성 검사
	loginForm.addEventListener("submit", function(event) {
		const userId = userIdInput.value.trim();
		const pwd = pwdInput.value.trim();

		// 유효성 검사
		if (!userId || !pwd) {
			alert("아이디와 비밀번호를 입력해 주세요.");
			event.preventDefault(); // 폼 제출을 막음
		}
	});

	// 공백 자동 제거 기능
	userIdInput.addEventListener("input", () => {
		userIdInput.value = userIdInput.value.replace(/\s/g, '');
	});
	pwdInput.addEventListener("input", () => {
		pwdInput.value = pwdInput.value.replace(/\s/g, '');
	});

	// 포커스 시 에러 메시지 숨기기
	userIdInput.addEventListener("focus", () => clearError());
	pwdInput.addEventListener("focus", () => clearError());

	function clearError() {
		const errorMessages = document.querySelectorAll(".error-message");
		errorMessages.forEach((el) => {
			el.style.display = "none";
		});
	}
});
