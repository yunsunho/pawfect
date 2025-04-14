// 탭 로딩 
function loadTab(tabName, page = 1) {
	fetch(`/mypage/tab/${tabName}?page=${page}`)
		.then((res) => res.text())
		.then((html) => {
			document.getElementById("mypage-content-area").innerHTML = html;

			if (tabName === "profile") {
				initProfileTabEvents();
			} else if (tabName === "info") {
				initInfoTabEvents();
			} else if (tabName === "password") {
				initPasswordTabEvents();
			} else if (tabName === "inquiry") {
				initInquiryTabEvents();
			} else if (tabName === "bookmark") {
				initBookmarkTabEvents();
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
			if (tabName) {
				loadTab(tabName);
			}
		});
	});
});

let tempImageFormData = null; // 저장 버튼 클릭 시 서버에 전송할 이미지
let imageChanged = false;     // 이미지가 실제로 변경되었는지 여부
// 프로필 탭 기능 
function initProfileTabEvents() {
	const profileImgBtn = document.getElementById("editProfileImgBtn");
	const profileImgInput = document.getElementById("profileImageInput");
	const deleteImgBtn = document.getElementById("deleteProfileImgBtn");
	const profileImgEl = document.querySelector(".profile-img");

	// 이미지 수정 버튼 클릭 -> 파일 선택 창
	profileImgBtn?.addEventListener("click", () => profileImgInput?.click());

	// 이미지 파일 선택 시: 서버 업로드 안함, 화면에 미리보기만
	profileImgInput?.addEventListener("change", function() {
		const file = this.files[0];
		if (!file) return;

		if (file.size > 5 * 1024 * 1024) {
			showModal("이미지 크기는 5MB 이하만 업로드 가능합니다.");
			this.value = "";
			return;
		}

		// 미리보기 처리 (로컬 blob URL)
		const previewURL = URL.createObjectURL(file);
		if (profileImgEl) {
			profileImgEl.setAttribute("src", previewURL);
		}

		// 저장을 위한 FormData 저장
		tempImageFormData = new FormData();
		tempImageFormData.append("profileImage", file);
		imageChanged = true; // 저장할 때 전송해야 함
		showModal("프로필 이미지가 수정되었습니다.");
	});

	// 이미지 삭제
	deleteImgBtn?.addEventListener("click", () => {
		showConfirmModal("정말 프로필 이미지를 삭제하시겠습니까?", () => {
			// 이미지 바로 기본 이미지로 변경 (화면만)
			if (profileImgEl) {
				profileImgEl.setAttribute("src", "/images/default_profile.jpg");
			}
			tempImageFormData = "delete";
			imageChanged = true;
			showModal("프로필 이미지가 삭제되었습니다.");
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
		const nickname = document.getElementById("nickname").value.trim();
		const petName = document.getElementById("petName").value.trim();
		const petType = parseInt(document.getElementById("petType").value);

		const profileData = {
			userNickname: nickname,
			petName: petName,
			petType: petType,
			nicknameChanged: nicknameChanged,
		};

		// 프로필 저장
		fetch("/mypage/profile", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(profileData),
		})
			.then((res) => res.text())
			.then((result) => {
				if (result === "success") {
					// 이미지 변경이 있었다면 처리
					if (imageChanged) {
						if (tempImageFormData === "delete") {
							// 이미지 삭제
							fetch("/mypage/profile/image/delete", { method: "POST" })
								.then((res) => res.text())
								.then(() => {
									showModalWithCallback("회원 프로필이 성공적으로 수정되었습니다.", () => {
										loadTab("profile");
									});
								});
						} else if (tempImageFormData instanceof FormData) {
							// 이미지 업로드 후 경로 받아서 DB에 저장
							fetch("/mypage/profile/image", {
								method: "POST",
								body: tempImageFormData,
							})
								.then((res) => res.json())
								.then((data) => {
									if (data.result === "success") {
										fetch("/mypage/profile/image/save", {
											method: "POST",
											headers: { "Content-Type": "application/json" },
											body: JSON.stringify({
												imagePath: data.imagePath,
												originalFilename: data.originalFilename,
											}),
										})
											.then((res) => res.text())
											.then(() => {
												showModalWithCallback("회원 프로필이 성공적으로 수정되었습니다.", () => {
													loadTab("profile");
												});
											});
									} else {
										showModal("이미지 업로드에 실패했습니다.");
									}
								});
						}
					} else {
						// 이미지 변경 없을 경우
						showModalWithCallback("회원 프로필이 성공적으로 수정되었습니다.", () => {
							loadTab("profile");
						});
					}
				} else {
					showModal("수정에 실패했습니다. 다시 시도해주세요.");
				}
			})
			.catch((err) => {
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

		const emailPattern = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
		if (!emailPattern.test(email)) {
			emailStatus.textContent = "올바른 이메일 형식을 입력해주세요.";
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

// 비밀번호 변경 탭 기능
function initPasswordTabEvents() {
	const currentPwdInput = document.getElementById("currentPwd");
	const newPwdInput = document.getElementById("newPwd");
	const newPwdCheckInput = document.getElementById("newPwdCheck");
	const saveBtn = document.getElementById("btnSavePwd");

	const pwdCheckList = document.getElementById("pwd-check-list");
	const pwdMatchCheckList = document.getElementById("pwd-match-check-list");

	// 포커스 시 조건 보여주기
	newPwdInput.addEventListener("focus", () => {
		pwdCheckList.style.display = "block";
	});

	// 비밀번호 입력 시 공백 제거
	[newPwdInput, newPwdCheckInput].forEach(input => {
		input.addEventListener("input", () => {
			input.value = input.value.replace(/\s/g, ""); // 공백 제거

			validatePwdConditions();
			validatePwdMatch();
		});
	});

	// 조건: 길이 8자 이상 + 영문/숫자/특수문자 포함
	function validatePwdConditions() {
		const pwd = newPwdInput.value.trim();
		const lengthValid = pwd.length >= 8;
		const patternValid = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])/.test(pwd);

		document.getElementById("pwd-condition-length").className = lengthValid ? "valid" : "invalid";
		document.getElementById("pwd-condition-pattern").className = patternValid ? "valid" : "invalid";

		pwdCheckList.style.display = "block";
		return lengthValid && patternValid;
	}

	// 새 비밀번호 == 새 비밀번호 확인
	function validatePwdMatch() {
		const pwd = newPwdInput.value.trim();
		const pwdCheck = newPwdCheckInput.value.trim();
		const isMatch = pwd && pwd === pwdCheck;

		document.getElementById("pwd-match-condition").className = isMatch ? "valid" : "invalid";
		pwdMatchCheckList.style.display = "block";

		return isMatch;
	}

	// 저장 버튼 클릭
	saveBtn?.addEventListener("click", () => {
		const currentPwd = currentPwdInput.value.trim();
		const newPwd = newPwdInput.value.trim();
		const newPwdCheck = newPwdCheckInput.value.trim();

		if (!currentPwd || !newPwd || !newPwdCheck) {
			showModal("모든 비밀번호를 입력해주세요.");
			return;
		}
		if (!validatePwdConditions()) {
			showModal("새 비밀번호가 조건에 부합하지 않습니다.");
			return;
		}
		if (!validatePwdMatch()) {
			showModal("비밀번호가 일치하지 않습니다.");
			return;
		}

		// 서버로 전송
		fetch("/mypage/password/update", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({ currentPwd, newPwd, confirmPwd: newPwdCheck })
		})
			.then(res => res.text())
			.then(result => {
				if (result === "success") {
					showModal("비밀번호가 성공적으로 변경되었습니다.");
					currentPwdInput.value = "";
					newPwdInput.value = "";
					newPwdCheckInput.value = "";
				} else if (result === "wrong") {
					showModal("현재 비밀번호가 일치하지 않습니다.");
				} else if (result === "mismatch") {
					showModal("새 비밀번호가 일치하지 않습니다.");
				} else {
					showModal("비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
				}
			})
			.catch(() => {
				showModal("오류가 발생했습니다.");
			});
	});
}

// 북마크 탭 기능
function initBookmarkTabEvents() {
	document.querySelectorAll(".page-btn").forEach(btn => {
		btn.addEventListener("click", function() {
			const page = this.dataset.page;
			loadTab("bookmark", page);
		});
	});
}

// 문의 탭 기능
function initInquiryTabEvents() {
	// 제목 클릭 시 상세 보기 토글
	document.querySelectorAll(".inquiry-summary").forEach(row => {
		row.addEventListener("click", function() {
			const detailRow = this.nextElementSibling;
			if (detailRow && detailRow.classList.contains("inquiry-detail")) {
				const isOpen = detailRow.style.display === "table-row";

				document.querySelectorAll(".inquiry-detail").forEach(d => d.style.display = "none");
				document.querySelectorAll(".inquiry-summary").forEach(s => s.classList.remove("open"));

				if (!isOpen) {
					detailRow.style.display = "table-row";
					this.classList.add("open");
				}
			}
		});
	});

	// 삭제 버튼 처리
	document.querySelectorAll(".deleteBtn").forEach(btn => {
		btn.addEventListener("click", function(e) {
			e.stopPropagation();
			const id = this.dataset.id;

			showConfirmModal("정말 삭제하시겠습니까?", () => {
				fetch("/mypage/inquiry/delete", {
					method: "POST",
					headers: { "Content-Type": "application/x-www-form-urlencoded" },
					body: new URLSearchParams({ inquiryId: id })
				})
					.then(res => res.text())
					.then(result => {
						if (result === "success") {
							showModalWithCallback("문의글이 삭제되었습니다.", () => {
								loadTab("inquiry");
							});
						} else {
							showModal("문의글 삭제에 실패했습니다.");
						}
					})
					.catch(() => showModal("서버 오류가 발생했습니다."));
			});
		});
	});

	// 모달 열기
	const writeBtn = document.getElementById("btnWriteInquiry");
	writeBtn?.addEventListener("click", () => {
		document.getElementById("inquiryModal").style.display = "block";
	});

	// 모달 닫기
	window.closeInquiryModal = function() {
		document.getElementById("inquiryModal").style.display = "none";
	};

	// 작성 처리
	const submitBtn = document.getElementById("btnSubmitInquiry");
	submitBtn?.addEventListener("click", () => {
		const title = document.getElementById("inquiryTitle").value.trim();
		const content = document.getElementById("inquiryContent").value.trim();

		if (!title || !content) {
			showModal("제목과 내용을 모두 입력해주세요.");
			return;
		}

		fetch("/mypage/inquiry/write", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({ inquiryTitle: title, inquiryContent: content })
		})
			.then(res => res.text())
			.then(result => {
				if (result === "success") {
					showModalWithCallback("문의가 등록되었습니다.", () => {
						window.closeInquiryModal();
						loadTab("inquiry");
					});
				} else {
					showModal("등록에 실패했습니다.");
				}
			})
			.catch(() => showModal("서버 오류가 발생했습니다."));
	});

	// 페이징 버튼 이벤트
	document.querySelectorAll(".page-btn").forEach(btn => {
		btn.addEventListener("click", function() {
			const page = this.dataset.page;
			loadInquiryPage(page);
		});
	});
}

function loadInquiryPage(page) {
	fetch(`/mypage/tab/inquiry?page=${page}`, {
		headers: {
			'X-Requested-With': 'XMLHttpRequest'
		}
	})
		.then(res => {
			if (!res.ok) throw new Error("페이지 로딩 실패");
			return res.text();
		})
		.then(html => {
			document.getElementById("mypage-content-area").innerHTML = html;
			initInquiryTabEvents();
		})
		.catch(err => {
			console.error("문의 탭 로딩 실패:", err);
		});
}

function closeInquiryModal() {
	document.getElementById("inquiryModal").style.display = "none";
}

document.addEventListener("DOMContentLoaded", () => {
	const writeBtn = document.getElementById("btnWriteInquiry");
	const submitBtn = document.getElementById("btnSubmitInquiry");
	// 작성 버튼 클릭 시 모달 열기
	if (writeBtn) {
		writeBtn.addEventListener("click", () => {
			document.getElementById("inquiryModal").style.display = "block";
		});
	}
	// 작성 요청
	if (submitBtn) {
		submitBtn.addEventListener("click", () => {
			const title = document.getElementById("inquiryTitle").value.trim();
			const content = document.getElementById("inquiryContent").value.trim();

			if (!title || !content) {
				showModal("제목과 내용을 모두 입력해주세요.");
				return;
			}

			fetch("/mypage/inquiry/write", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify({ inquiryTitle: title, inquiryContent: content })
			})
				.then(res => res.text())
				.then(result => {
					if (result === "success") {
						showModalWithCallback("문의가 등록되었습니다.", () => {
							closeInquiryModal();
							loadTab("inquiry");
						});
					} else {
						showModal("문의 등록에 실패했습니다.");
					}
				})
				.catch(() => {
					showModal("서버 오류가 발생했습니다.");
				});
		});
	}
});

// 로그아웃
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
			if (tabName) loadTab(tabName);
		});
	});

	// 로그아웃 버튼
	const logoutBtn = document.getElementById("logoutBtn");
	if (logoutBtn) {
		logoutBtn.addEventListener("click", () => {
			showConfirmModal("정말 로그아웃하시겠습니까?", () => {
				location.href = "/logout";
			});
		});
	}
});

// 모달
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

function showConfirmModal(message, onConfirm) {
	const modal = document.getElementById("confirmModal");
	const msgBox = document.getElementById("confirmModalMessage");
	const confirmBtn = document.getElementById("btnConfirmYes");
	const cancelBtn = document.getElementById("btnConfirmNo");

	if (modal && msgBox) {
		msgBox.innerText = message;
		modal.style.display = "block";
		// 확인
		confirmBtn.onclick = () => {
			modal.style.display = "none";
			onConfirm();
		};
		// 취소
		cancelBtn.onclick = () => {
			modal.style.display = "none";
		};
	}
}

function showModalWithCallback(message, callback) {
	const modal = document.getElementById("commonModal");
	const msgBox = document.getElementById("modalMessage");
	const confirmBtn = modal.querySelector("button");

	if (modal && msgBox && confirmBtn) {
		msgBox.innerText = message;
		modal.style.display = "block";

		const handler = () => {
			modal.style.display = "none";
			confirmBtn.removeEventListener("click", handler);
			if (typeof callback === "function") {
				callback();
			}
		};
		confirmBtn.addEventListener("click", handler);
	}
}
