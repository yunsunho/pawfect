// 1. 아이디 중복 확인 + 조건 검사
function checkId() {
  const userId = document.getElementById("userId").value.trim();
  const result = document.getElementById("check-result");
  const idPattern = /^[A-Za-z0-9]{4,12}$/;

  if (!userId) {
    result.innerText = "아이디를 입력해주세요.";
    result.style.color = "red";
    return;
  }

  if (!idPattern.test(userId)) {
    result.innerText = "아이디는 영문자와 숫자만 포함하며, 4~12자여야 합니다.";
    result.style.color = "red";
    return;
  }

  fetch("/user/checkId?userId=" + encodeURIComponent(userId))
    .then(response => response.text())
    .then(data => {
      result.innerText = data;
      result.style.color = data.includes("사용 가능") ? "#4caf50" : "red";
    })
    .catch(error => {
      result.innerText = "오류가 발생했습니다.";
      result.style.color = "red";
    });
}

// 2. 이메일 인증 요청
function sendEmailAuth() {
  const email = document.getElementById("email").value.trim();
  const result = document.getElementById("email-result");

  if (!email) {
    result.innerText = "이메일을 입력해주세요.";
    result.style.color = "red";
    return;
  }

  fetch("/user/sendAuthEmail?email=" + encodeURIComponent(email))
    .then(response => response.text())
    .then(data => {
      result.innerText = data;
      result.style.color = "#f4c542";
      document.getElementById("auth-code-section").style.display = "block";
    })
    .catch(error => {
      result.innerText = "오류가 발생했습니다. 다시 시도해주세요.";
      result.style.color = "red";
    });
}

// 3. 인증코드 확인 + 성공 시 가입 버튼 활성화
function verifyEmailCode() {
  const email = document.getElementById("email").value.trim();
  const code = document.getElementById("emailCode").value.trim();
  const result = document.getElementById("email-verify-result");
  const signupBtn = document.getElementById("signupBtn");

  if (!code) return alert("인증코드를 입력해주세요!");

  fetch("/user/verifyCode", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ email: email, code: code })
  })
    .then(res => res.text())
    .then(msg => {
      if (msg === "success") {
        result.textContent = "✅ 인증 완료되었습니다!";
        result.style.color = "green";
        signupBtn.disabled = false;
      } else {
        result.textContent = "❌ " + msg;
        result.style.color = "red";
        signupBtn.disabled = true;
      }
    });
}

// 4. 비밀번호 일치 + 복잡도 확인
function checkPasswordMatch() {
  const pwd = document.getElementById("pwd").value.trim();
  const pwdCheck = document.getElementById("pwdCheck").value.trim();
  const result = document.getElementById("pwd-result");

  const pwdPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]).{8,}$/;

  if (!pwd || !pwdCheck) {
    result.innerText = "";
    return;
  }

  if (!pwdPattern.test(pwd)) {
    result.innerText = "❌ 비밀번호는 영문, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.";
    result.style.color = "red";
    return;
  }

  if (pwd === pwdCheck) {
    result.innerText = "✅ 비밀번호가 일치합니다.";
    result.style.color = "#4caf50";
  } else {
    result.innerText = "❌ 비밀번호가 일치하지 않습니다.";
    result.style.color = "red";
  }
}
