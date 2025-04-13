// 탭 로딩 
function loadTab(tabName) {
	fetch(`/mypage/tab/${tabName}`)
		.then((res) => res.text())
		.then((html) => {
			document.getElementById("mypage-content-area").innerHTML = html;

			// 프로필 탭이면 이벤트 바인딩
			if (tabName === "profile") {
				initProfileTabEvents();
			} else if (tabName === "info") {
				initInfoTabEvents();
			}
		})
		.catch((err) => console.error("탭 로딩 실패", err));
}

// 탭 클릭 및 디폴트 로딩
document.addEventListener("DOMContentLoaded", function() {
	const defaultTab = document.querySelector('.mypage-sidebar li[data-tab="profile"]');
	if (defaultTab) {
		defaultTab.classList.add("active");
		loadTab("profile");
	}

	document.querySelectorAll(".mypage-sidebar li").forEach((tab) => {
		tab.addEventListener("click", function() {
			document.querySelectorAll(".mypage-sidebar li").forEach((t) => t.classList.remove("active"));
			this.classList.add("active");
			const tabName = this.dataset.tab;
			loadTab(tabName);
		});
	});
});

// 프로필 탭 기능 
function initProfileTabEvents() {
	const profileImgBtn = document.getElementById("editProfileImgBtn");
	const profileImgInput = document.getElementById("profileImageInput");
	profileImgBtn?.addEventListener("click", () => profileImgInput?.click());

	profileImgInput?.addEventListener("change", function() {
		const file = this.files[0];
		if (!file) return;

		if (file.size > 5 * 1024 * 1024) {
			showModal("이미지 크기는 5MB 이하만 업로드 가능합니다.");
			this.value = "";
			return;
		}

		const formData = new FormData();
		formData.append("profileImage", file);

		fetch("/mypage/profile/image", {
			method: "POST",
			body: formData
		})
			.then(res => res.text())
			.then(result => {
				if (result === "success") {
					showModal("프로필 이미지가 변경되었습니다.");
					setTimeout(() => loadTab("profile"), 1000);
				} else {
					showModal("이미지 업로드에 실패했습니다.");
				}
			})
			.catch(err => {
				console.error("업로드 에러:", err);
				showModal("이미지 업로드 중 오류가 발생했습니다.");
			});
	});

	// 닉네임 수정
	const nicknameInput = document.getElementById("nickname");
	const nicknameBtn = document.getElementById("editNicknameBtn");

	nicknameInput.setAttribute("readonly", true);
	nicknameInput.setAttribute("tabindex", "-1");
	nicknameInput.style.pointerEvents = "none";

	let nicknameEditMode = false;
	let nicknameChanged = false;
	let originalNickname = nicknameInput.value;

	nicknameBtn?.addEventListener("click", () => {
		const canEdit = nicknameInput.dataset.canEdit === "true";
		if (!canEdit) {
			showModal("닉네임은 30일마다 변경 가능합니다.");
			return;
		}

		if (!nicknameEditMode) {
			nicknameEditMode = true;
			originalNickname = nicknameInput.value;
			nicknameInput.removeAttribute("readonly");
			nicknameInput.removeAttribute("tabindex");
			nicknameInput.style.pointerEvents = "auto";
			nicknameInput.focus();
			nicknameBtn.textContent = "확인";
		} else {
			nicknameEditMode = false;
			nicknameInput.setAttribute("readonly", true);
			nicknameInput.setAttribute("tabindex", "-1");
			nicknameInput.style.pointerEvents = "none";
			nicknameBtn.textContent = "수정";

			const newNickname = nicknameInput.value.trim();
			nicknameChanged = newNickname !== originalNickname;

			if (nicknameChanged) {
				showModal("닉네임이 수정되었습니다.");
			}
		}
	});

	// 반려동물 정보 수정
	const petBtn = document.getElementById("editPetBtn");
	const petNameInput = document.getElementById("petName");
	const petTypeSelect = document.getElementById("petType");

	// 수정 전: 완전 비활성화
	petNameInput.setAttribute("readonly", true);
	petNameInput.setAttribute("tabindex", "-1");
	petNameInput.style.pointerEvents = "none";

	petTypeSelect.setAttribute("disabled", true);
	petTypeSelect.setAttribute("tabindex", "-1");
	petTypeSelect.style.pointerEvents = "none";

	let petEditMode = false;

	petBtn?.addEventListener("click", () => {
		if (!petEditMode) {
			petEditMode = true;

			petNameInput.removeAttribute("readonly");
			petNameInput.removeAttribute("tabindex");
			petNameInput.style.pointerEvents = "auto";

			petTypeSelect.removeAttribute("disabled");
			petTypeSelect.removeAttribute("tabindex");
			petTypeSelect.style.pointerEvents = "auto";

			petNameInput.focus();
			petBtn.textContent = "확인";
		} else {
			petEditMode = false;

			petNameInput.setAttribute("readonly", true);
			petNameInput.setAttribute("tabindex", "-1");
			petNameInput.style.pointerEvents = "none";

			petTypeSelect.setAttribute("disabled", true);
			petTypeSelect.setAttribute("tabindex", "-1");
			petTypeSelect.style.pointerEvents = "none";

			petBtn.textContent = "수정";
			showModal("반려동물 정보가 수정되었습니다.");
		}
	});

	// 내 프로필 저장 버튼
	const saveBtn = document.getElementById("btnSaveProfile");
	saveBtn?.addEventListener("click", () => {
		const nickname = nicknameInput.value.trim();
		const petName = document.getElementById("petName").value.trim();
		const petType = parseInt(document.getElementById("petType").value);

		const data = {
			userNickname: nickname,
			petName: petName,
			petType: petType,
			nicknameChanged: nicknameChanged 
		};

		fetch("/mypage/profile", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(data)
		})
			.then(res => res.text())
			.then(result => {
				if (result === "success") {
					showModal("회원 정보가 성공적으로 수정되었습니다.");
					setTimeout(() => loadTab("profile"), 1000);
				} else {
					showModal("수정에 실패했습니다. 다시 시도해주세요.");
				}
			})
			.catch(err => {
				console.error("에러 발생:", err);
				showModal("오류가 발생했습니다.");
			});
	});
}

let emailTimer = null;
let timeLeft = 300;

// 내 정보 탭 기능
function initInfoTabEvents() {
	const emailInput = document.getElementById("email");
	emailInput.dataset.originalEmail = emailInput.value;

	const btnSendCode = document.getElementById("btnSendCode");
	const btnVerifyCode = document.getElementById("btnVerifyCode");
	const emailCodeBox = document.getElementById("emailCodeBox");
	const emailCodeInput = document.getElementById("emailCode");
	const emailStatus = document.getElementById("emailStatus");
	const emailResult = document.getElementById("emailResult");
	const emailSpinner = document.getElementById("emailSpinner");
	const emailTimerEl = document.getElementById("emailTimer");
	const emailEditBtn = document.getElementById("editEmailBtn");

	let emailVerified = false;

	// 이메일 수정 전: 비활성화
	emailInput.setAttribute("readonly", true);
	emailInput.setAttribute("tabindex", "-1");
	emailInput.style.pointerEvents = "none";

	btnSendCode.style.display = "none"; // 인증 버튼은 처음에 숨김

	emailEditBtn?.addEventListener("click", () => {
		emailInput.removeAttribute("readonly");
		emailInput.removeAttribute("tabindex");
		emailInput.style.pointerEvents = "auto";
		emailInput.focus();
		emailEditBtn.style.display = "none";
		btnSendCode.style.display = "inline-block";
		emailVerified = false;
	});

	// 전화번호 관련
	const tel1 = document.getElementById("userTel1");
	const tel2 = document.getElementById("userTel2");
	const tel3 = document.getElementById("userTel3");
	const userTelHidden = document.getElementById("userTel");
	const telEditBtn = document.getElementById("editTelBtn");

	// 전화번호 분리 초기화
	if (userTelHidden.value) {
		const parts = userTelHidden.value.split("-");
		if (parts.length === 3) {
			tel1.value = parts[0];
			tel2.value = parts[1];
			tel3.value = parts[2];
		}
	}

	[tel1, tel2, tel3].forEach(el => {
		el.setAttribute("readonly", true);
		el.setAttribute("tabindex", "-1");
		el.style.pointerEvents = "none";
	});

	let telEditMode = false;
	telEditBtn?.addEventListener("click", () => {
		if (!telEditMode) {
			telEditMode = true;
			[tel1, tel2, tel3].forEach(el => {
				el.removeAttribute("readonly");
				el.removeAttribute("tabindex");
				el.style.pointerEvents = "auto";
			});
			tel1.focus();
			telEditBtn.textContent = "확인";
		} else {
			telEditMode = false;
			[tel1, tel2, tel3].forEach(el => {
				el.setAttribute("readonly", true);
				el.setAttribute("tabindex", "-1");
				el.style.pointerEvents = "none";
			});
			telEditBtn.textContent = "수정";
			updateFullTel();
			showModal("전화번호가 수정되었습니다.");
		}
	});

	[tel1, tel2, tel3].forEach(input => {
		input.addEventListener("input", () => {
			input.value = input.value.replace(/[^0-9]/g, "");
			updateFullTel();
		});
	});

	function updateFullTel() {
		const v1 = tel1.value.trim();
		const v2 = tel2.value.trim();
		const v3 = tel3.value.trim();
		const full = v1 && v2 && v3 ? `${v1}-${v2}-${v3}` : "";
		userTelHidden.value = full;
	}

	// 이메일 인증 전송
	btnSendCode?.addEventListener("click", () => {
		const email = emailInput.value.trim();
		emailStatus.textContent = "";
		emailResult.textContent = "";

		if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
			emailStatus.textContent = "이메일 형식을 다시 확인해주세요.";
			emailStatus.style.color = "red";
			return;
		}

		fetch("/checkEmail?email=" + encodeURIComponent(email))
			.then(res => res.text())
			.then(data => {
				if (data.includes("이미 사용 중인 이메일입니다.")) {
					emailStatus.textContent = data;
					emailStatus.style.color = "red";
					return;
				}

				btnSendCode.disabled = true;
				emailSpinner.style.display = "inline-block";

				fetch("/email-verification/send", {
					method: "POST",
					headers: { "Content-Type": "application/x-www-form-urlencoded" },
					body: new URLSearchParams({ email })
				})
					.then(res => res.text())
					.then(msg => {
						emailStatus.textContent = "📩 인증 이메일이 전송되었습니다. 메일함을 확인해주세요!";
						emailStatus.style.color = "green";
						emailCodeBox.style.display = "flex";
						emailCodeInput.value = "";
						startEmailTimer();
					})
					.catch(() => {
						emailStatus.textContent = "오류 발생";
						emailStatus.style.color = "red";
					})
					.finally(() => {
						btnSendCode.disabled = false;
						emailSpinner.style.display = "none";
					});
			})
			.catch(() => {
				emailStatus.textContent = "오류 발생";
				emailStatus.style.color = "red";
			});
	});

	// 이메일 인증 코드 검증
	btnVerifyCode?.addEventListener("click", () => {
		const email = emailInput.value.trim();
		const code = emailCodeInput.value.trim();

		emailResult.textContent = "";

		if (!code) {
			emailResult.textContent = "인증 코드를 입력해주세요.";
			emailResult.style.color = "red";
			return;
		}

		fetch("/email-verification/verify", {
			method: "POST",
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
			body: new URLSearchParams({ email, code })
		})
			.then(res => res.text())
			.then(msg => {
				if (msg.includes("완료")) {
					clearInterval(emailTimer);
					emailTimerEl.innerText = "";
					emailResult.textContent = "인증 완료되었습니다!";
					emailResult.style.color = "green";
					emailVerified = true;
					showModal("이메일이 수정되었습니다.");
				} else {
					emailResult.textContent = "인증 코드가 일치하지 않습니다.";
					emailResult.style.color = "red";
					emailVerified = false;
				}
			});
	});

	// 내 정보 저장 버튼
	const saveBtn = document.getElementById("btnSaveInfo");

	saveBtn?.addEventListener("click", () => {
		const email = emailInput.value.trim();
		const userTel = userTelHidden.value.trim();

		// 필수: 이메일이 비어있으면 막기
		if (email === "") {
			showModal("이메일은 필수 입력 항목입니다.");
			return;
		}

		// 이메일이 실제로 수정됐는지 비교
		const originalEmail = emailInput.dataset.originalEmail;
		const isEmailEdited = email !== originalEmail;

		// 수정은 했는데 인증 안 된 경우 막기
		if (isEmailEdited && !emailVerified) {
			showModal("이메일 인증을 완료해주세요.");
			return;
		}

		const data = {
			email: email,
			userTel: userTel
		};

		fetch("/mypage/info/update", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(data)
		})
			.then(res => res.text())
			.then(result => {
				if (result === "success") {
					showModal("회원 정보가 성공적으로 수정되었습니다.");
					// 수정 성공하면 원래 이메일값도 갱신
					emailInput.dataset.originalEmail = email;
				} else {
					showModal("수정에 실패했습니다. 다시 시도해주세요.");
				}
			})
			.catch(err => {
				console.error("에러 발생:", err);
				showModal("오류가 발생했습니다.");
			});
	});
}

// 타이머 시작
function startEmailTimer() {
	let timeLeft = 300;
	const timerEl = document.getElementById("emailTimer");
	clearInterval(emailTimer);

	emailTimer = setInterval(() => {
		if (timeLeft <= 0) {
			clearInterval(emailTimer);
			timerEl.textContent = "⏰ 인증 시간이 만료되었습니다.";
			timerEl.style.color = "red";
			return;
		}
		const min = String(Math.floor(timeLeft / 60)).padStart(2, '0');
		const sec = String(timeLeft % 60).padStart(2, '0');
		timerEl.textContent = `남은 시간: ${min}:${sec}`;
		timeLeft--;
	}, 1000);
}

function showModal(message) {
	const modal = document.getElementById("commonModal");
	const msgBox = document.getElementById("modalMessage");
	if (modal && msgBox) {
		msgBox.innerText = message;
		modal.style.display = "block";
	}
}
function closeModal() {
	const modal = document.getElementById("commonModal");
	if (modal) modal.style.display = "none";
} 
