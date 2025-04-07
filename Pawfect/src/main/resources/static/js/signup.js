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

  updateStatusIcon('id', isValid);
});

// 아이디 중복 확인
function checkId() {
    const userId = userIdInput.value.trim();
    const result = document.getElementById("check-result");

    if (!userId) {
        result.innerText = "아이디를 입력해주세요.";
        result.style.color = "red";
        return;
    }

    fetch("/checkId?userId=" + encodeURIComponent(userId))
        .then(response => response.text())
        .then(data => {
            result.innerText = data;
            result.style.color = data.includes("사용 가능") ? "green" : "red";
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
  updateStatusIcon('email', isValid);
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

// 이메일 인증 보내기
function sendEmailAuth() {
  const email = document.getElementById("email").value.trim();
  const result = document.getElementById("email-result");
  const authSection = document.getElementById("auth-code-section");
  const emailBtn = document.getElementById("emailAuthBtn");
  const spinner = document.getElementById("email-spinner");

  if (!email) {
    result.innerText = "이메일을 입력해주세요.";
    result.style.color = "red";
    return;
  }

  emailBtn.disabled = true;
  spinner.style.display = "inline-block";

  fetch("/sendAuthEmail?email=" + encodeURIComponent(email))
    .then(response => response.text())
    .then(data => {
      result.innerText = data;
      result.style.color = "green";
      authSection.style.display = "block";
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
}

// 이메일 타이머
function startEmailTimer() {
  const timerEl = document.getElementById("email-timer");
  timeLeft = 300;
  clearInterval(emailTimer);

  emailTimer = setInterval(() => {
    if (timeLeft <= 0) {
      clearInterval(emailTimer);
      timerEl.innerText = "인증 시간이 만료되었습니다.";
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

  // 필수 입력값들
  const userId = document.getElementById("userId").value.trim();
  const pwd = document.getElementById("pwd").value.trim();
  const pwdCheck = document.getElementById("pwdCheck").value.trim();
  const email = document.getElementById("email").value.trim();
  const userName = document.getElementById("userName").value.trim();
  const userNickname = document.getElementById("userNickname").value.trim();

  // 유효성 검사 실패 시 return false
  if (!userId || !pwd || !pwdCheck || !email || !userName || !userNickname) {
    alert("모든 필수 항목을 입력해주세요.");
    return false;
  }
  
  // 아이디 중복확인 여부
  const idCheckResult = document.getElementById("check-result").innerText;
  if (!idCheckResult.includes("사용 가능")) {
    alert("아이디 중복 확인을 해주세요.");
    return false;
  }
  
  // 비밀번호 일치 확인
  if (pwd !== pwdCheck) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }

  // 이메일 인증 여부
  const emailVerifyResult = document.getElementById("email-verify-result").innerText;
  if (!emailVerifyResult.includes("인증 완료")) {
    alert("이메일 인증을 완료해주세요.");
    return false;
  }

  // 폼 자체의 HTML5 유효성 검사 (input required 등)
  if (!form.checkValidity()) {
    form.reportValidity();
    return false;
  }

  return true;
}
