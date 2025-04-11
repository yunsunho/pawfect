let emailTimer = null;
let timeLeft = 300;

// 아이디 입력 요소
const userIdInput = document.getElementById("userId");
const idCheckList = document.getElementById("id-check-list");

// 비밀번호 입력 요소
const pwdInput = document.getElementById("pwd");
const pwdCheckInput = document.getElementById("pwdCheck");
const pwdCheckList = document.getElementById("pw-check-list");

// 상태 아이콘 업데이트 함수
function updateStatusIcon(fieldId, isValid) {
	const iconEl = document.getElementById(fieldId + '-status-icon');
	iconEl.textContent = isValid ? '✔' : '';
}

// 아이디 조건 표시
userIdInput.addEventListener("focus", () => idCheckList.style.display = "block");
userIdInput.addEventListener("blur", () => setTimeout(() => idCheckList.style.display = "none", 200));

userIdInput.addEventListener("input", () => {
	const userId = userIdInput.value.trim();
	const lengthValid = userId.length >= 4 && userId.length <= 12;
	const patternValid = /^[A-Za-z0-9]{4,12}$/.test(userId);
	const isValid = lengthValid && patternValid;

	document.getElementById("id-condition-length").className = lengthValid ? "valid" : "invalid";
	document.getElementById("id-condition-length").textContent = "4~12자 길이";

	document.getElementById("id-condition-pattern").className = patternValid ? "valid" : "invalid";
	document.getElementById("id-condition-pattern").textContent = "영문 또는 숫자만 가능";

	// updateStatusIcon('id', isValid);

	// 중복확인 결과 메시지 초기화
	const result = document.getElementById("check-result");
	result.innerText = "";
});


// 아이디 중복 확인
function checkId() {
	const userId = userIdInput.value.trim();
	const result = document.getElementById("check-result");

	// 아이디 유효성 먼저 검사
	const idPattern = /^[A-Za-z0-9]{4,12}$/;
	if (!idPattern.test(userId) || /\s/.test(userId)) {
		result.innerText = "아이디 형식을 다시 확인해주세요. (4~12자, 영문/숫자, 공백 불가)";
		result.style.color = "red";
		return;
	}

	// 형식이 맞는 경우에만 서버에 중복확인 요청
	fetch("/checkId?userId=" + encodeURIComponent(userId))
		.then(response => response.text())
		.then(data => {
			result.innerText = data;
			result.style.color = data.includes("사용 가능") ? "green" : "red";
			
			updateStatusIcon("id", data.includes("사용 가능"));
		})
		.catch(() => {
			result.innerText = "오류 발생";
			result.style.color = "red";
		});
}

// 비밀번호 조건 표시
pwdInput.addEventListener("focus", () => pwdCheckList.style.display = "block");
pwdInput.addEventListener("blur", () => setTimeout(() => pwdCheckList.style.display = "none", 200));

// 비밀번호 확인 칸 focus 시에도 조건 리스트 표시
pwdCheckInput.addEventListener("focus", () => pwdCheckList.style.display = "block");
pwdCheckInput.addEventListener("blur", () => setTimeout(() => pwdCheckList.style.display = "none", 200));

// 비밀번호 입력 조건 (길이, 조합만 처리)
pwdInput.addEventListener("input", () => {
	const pwd = pwdInput.value.trim();
	const lengthValid = pwd.length >= 8;
	const patternValid = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])/.test(pwd);
	const isValid = lengthValid && patternValid;

	document.getElementById("pwd-condition-length").className = lengthValid ? "valid" : "invalid";
	document.getElementById("pwd-condition-length").textContent = "8자 이상";

	document.getElementById("pwd-condition-pattern").className = patternValid ? "valid" : "invalid";
	document.getElementById("pwd-condition-pattern").textContent = "영문, 숫자, 특수문자 포함";

	updateStatusIcon('pwd', isValid);
});

// 비밀번호 확인 입력에서만 일치 검사
pwdCheckInput.addEventListener("focus", () => {
	document.getElementById("pw-match-check-list").style.display = "block";
});
pwdCheckInput.addEventListener("blur", () => {
	setTimeout(() => {
		document.getElementById("pw-match-check-list").style.display = "none";
	}, 200);
});

pwdCheckInput.addEventListener("input", () => {
	const pwd = pwdInput.value.trim();
	const pwdCheck = pwdCheckInput.value.trim();
	const isValid = pwd && pwd === pwdCheck;

	const matchCondition = document.getElementById("pwd-match-condition");
	matchCondition.className = isValid ? "valid" : "invalid";
	matchCondition.textContent = "비밀번호 일치";

	updateStatusIcon('pwdCheck', isValid);
});

// 이메일 조건
document.getElementById("email").addEventListener("input", () => {
	const email = document.getElementById("email").value.trim();
	const isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
	// updateStatusIcon('email', isValid);
});

// 이름 조건 (한글 1~6자)
const userNameInput = document.getElementById("userName");
const nameCheckList = document.getElementById("name-check-list");

userNameInput.addEventListener("focus", () => nameCheckList.style.display = "block");
userNameInput.addEventListener("blur", () => setTimeout(() => nameCheckList.style.display = "none", 200));

userNameInput.addEventListener("input", () => {
	const name = userNameInput.value.trim();
	const isValid = /^[가-힣]{1,6}$/.test(name);

	const condition = document.getElementById("name-condition-korean");
	condition.className = isValid ? "valid" : "invalid";
	condition.textContent = "한글 1~6자";

	updateStatusIcon("userName", isValid);
});

function sendEmailAuth() {
	const emailInput = document.getElementById("email");
	const result = document.getElementById("email-result");
	const email = emailInput.value.trim();
	const authSection = document.getElementById("auth-code-section");
	const emailBtn = document.getElementById("emailAuthBtn");
	const spinner = document.getElementById("email-spinner");

	// 이메일 형식 검사
	const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
	if (!emailValid) {
		result.innerText = "이메일 형식을 다시 확인해주세요.";
		result.style.color = "red";
		return;
	}

	// 이메일 중복 확인 먼저
	fetch("/checkEmail?email=" + encodeURIComponent(email))
		.then(response => response.text())
		.then(data => {
			result.innerText = data;
			result.style.color = data.includes("사용 가능") ? "green" : "red";
			
			updateStatusIcon("email", data.includes("사용 가능"));
			
			// 중복된 이메일일 경우 -> 인증 중단
			if (data.includes("이미 사용 중")) {
				alert("이미 사용 중인 이메일입니다. 다른 이메일을 입력해주세요.");
				return;
			}

			// 중복 없으면 인증 코드 전송 시작
			emailBtn.disabled = true;
			spinner.style.display = "inline-block";

			fetch("/sendAuthEmail?email=" + encodeURIComponent(email))
				.then(response => response.text())
				.then(data => {
					result.innerText = data;
					result.style.color = "green";
					authSection.style.display = "block";

					document.getElementById("emailCode").value = "";
					document.getElementById("email-verify-result").textContent = "";
					document.getElementById("email-timer").textContent = "";

					startEmailTimer();
				})
				.catch(() => {
					result.innerText = "오류 발생. 다시 시도해주세요.";
					result.style.color = "red";
				})
				.finally(() => {
					emailBtn.disabled = false;
					spinner.style.display = "none";
				});
		})
		.catch(() => {
			result.innerText = "이메일 중복 확인 중 오류 발생";
			result.style.color = "red";
		});
}

// 이메일 타이머
function startEmailTimer() {
	const timerEl = document.getElementById("email-timer");
	timeLeft = 300;
	clearInterval(emailTimer);

	emailTimer = setInterval(() => {
		if (timeLeft <= 0) {
			clearInterval(emailTimer);
			timerEl.innerText = "⏰ 인증 시간이 만료되었습니다.";
			timerEl.style.color = "red";
			return;
		}
		const min = String(Math.floor(timeLeft / 60)).padStart(2, '0');
		const sec = String(timeLeft % 60).padStart(2, '0');
		timerEl.innerText = `남은 시간: ${min}:${sec}`;
		timeLeft--;
	}, 1000);
}

// 이메일 인증 코드 검증
function verifyEmailCode() {
	const email = document.getElementById("email").value.trim();
	const code = document.getElementById("emailCode").value.trim();
	const result = document.getElementById("email-verify-result");
	const signupBtn = document.getElementById("signupBtn");

	if (!code) {
		alert("인증코드를 입력해주세요!");
		return;
	}

	fetch("/verifyCode", {
		method: "POST",
		headers: { "Content-Type": "application/x-www-form-urlencoded" },
		body: new URLSearchParams({ email, code })
	})
		.then(res => res.text())
		.then(msg => {
			if (msg === "success") {
				result.textContent = "인증 완료되었습니다!";
				result.style.color = "green";
				signupBtn.disabled = false;
				clearInterval(emailTimer);
				document.getElementById("email-timer").innerText = "";
			} else {
				result.textContent = msg;
				result.style.color = "red";
				signupBtn.disabled = true;
			}
		});
}

// 전화번호 입력 (숫자만 허용 & 합치기)
const telInputs = [
	document.getElementById("userTel1"),
	document.getElementById("userTel2"),
	document.getElementById("userTel3")
];

telInputs.forEach(input => {
	input.addEventListener("input", () => {
		// 숫자만 입력되도록 필터링
		input.value = input.value.replace(/[^0-9]/g, "");
		updateFullTel();
	});
});

function updateFullTel() {
	const tel1 = telInputs[0].value.trim();
	const tel2 = telInputs[1].value.trim();
	const tel3 = telInputs[2].value.trim();

	document.getElementById("userTel").value = `${tel1}-${tel2}-${tel3}`;
}

// 닉네임 입력 시 유효성 검사
const userNicknameInput = document.getElementById("userNickname");

userNicknameInput.addEventListener("input", () => {
	const nickname = userNicknameInput.value.trim();
	const isValid = /^[가-힣a-zA-Z0-9]{2,10}$/.test(nickname); // 조건: 한글/영문/숫자 2~10자

	updateStatusIcon("userNickname", isValid);
});

// 닉네임 경고문 focus 시 표시, blur 시 숨김
const nicknameWarning = document.querySelector(".nickname-warning");

userNicknameInput.addEventListener("focus", () => {
	nicknameWarning.style.display = "block";
});

userNicknameInput.addEventListener("blur", () => {
	setTimeout(() => {
		nicknameWarning.style.display = "none";
	}, 200);
});

function validateSignupForm() {
	const form = document.querySelector("form");

	const userId = document.getElementById("userId").value;
	const pwd = document.getElementById("pwd").value;
	const pwdCheck = document.getElementById("pwdCheck").value;
	const email = document.getElementById("email").value;
	const userName = document.getElementById("userName").value;
	const userNickname = document.getElementById("userNickname").value;

	// 공백 포함 여부 검사 함수
	const hasWhitespace = str => /\s/.test(str);

	// 1. 빈값 + 공백 체크
	if (
		!userId || hasWhitespace(userId) ||
		!pwd || hasWhitespace(pwd) ||
		!pwdCheck || hasWhitespace(pwdCheck) ||
		!email || hasWhitespace(email) ||
		!userName || hasWhitespace(userName) ||
		!userNickname || hasWhitespace(userNickname)
	) {
		alert("모든 필수 항목을 정확히 입력해주세요. (공백 금지)");
		return false;
	}

	// 2. 아이디 유효성 검사
	const idValid = /^[A-Za-z0-9]{4,12}$/.test(userId);
	if (!idValid) {
		alert("아이디는 4~12자의 영문 또는 숫자만 사용할 수 있습니다. (공백 불가)");
		return false;
	}

	// 3. 아이디 중복확인 여부
	const idCheckResult = document.getElementById("check-result").innerText;
	if (!idCheckResult.includes("사용 가능")) {
		alert("아이디 중복 확인을 해주세요.");
		return false;
	}

	// 4. 비밀번호 유효성 검사
	const pwdValid = pwd.length >= 8 &&
		/[A-Za-z]/.test(pwd) &&
		/[0-9]/.test(pwd) &&
		/[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]/.test(pwd);
	if (!pwdValid) {
		alert("비밀번호는 8자 이상, 영문/숫자/특수문자를 모두 포함해야 합니다. (공백 불가)");
		return false;
	}

	// 5. 비밀번호 일치 확인
	if (pwd !== pwdCheck) {
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}

	// 6. 이메일 형식 검사
	const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
	if (!emailValid) {
		alert("올바른 이메일 형식을 입력해주세요.");
		return false;
	}

	// 7. 이메일 인증 여부
	const emailVerifyResult = document.getElementById("email-verify-result").innerText;
	if (!emailVerifyResult.includes("인증 완료")) {
		alert("이메일 인증을 완료해주세요.");
		return false;
	}

	// 8. 이름 유효성 검사
	const nameValid = /^[가-힣]{1,6}$/.test(userName);
	if (!nameValid) {
		alert("이름은 한글 1~6자여야 합니다. (공백 불가)");
		return false;
	}

	// 9. 닉네임 유효성 검사
	const nicknameValid = /^[가-힣a-zA-Z0-9]{2,10}$/.test(userNickname);
	if (!nicknameValid) {
		alert("닉네임은 한글, 영문, 숫자 조합의 2~10자여야 합니다. (공백 불가)");
		return false;
	}

	// 10. HTML 기본 유효성 검사
	if (!form.checkValidity()) {
		form.reportValidity();
		return false;
	}

	return true;
}
