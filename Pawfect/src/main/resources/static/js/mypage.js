// 탭 로딩 
function loadTab(tabName) {
	fetch(`/mypage/tab/${tabName}`)
		.then((res) => res.text())
		.then((html) => {
			document.getElementById("mypage-content-area").innerHTML = html;

			// 프로필 탭이면 이벤트 바인딩
			if (tabName === "profile") {
				initProfileTabEvents();
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

// 프로필 탭 기능 바인딩
function initProfileTabEvents() {
	// 프로필 이미지
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
					setTimeout(() => location.reload(), 1000);
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

	nicknameBtn?.addEventListener("click", () => {
		const canEdit = nicknameInput.dataset.canEdit === "true";
		if (canEdit || typeof canEdit === "undefined") {
			nicknameInput.removeAttribute("readonly");
			nicknameInput.focus();
		} else {
			showModal("닉네임은 30일마다만 변경할 수 있습니다.");
		}
	});

	// 반려동물 정보 수정
	const petBtn = document.getElementById("editPetBtn");
	const petNameInput = document.getElementById("petName");
	const petTypeSelect = document.getElementById("petType");

	petBtn?.addEventListener("click", () => {
		petNameInput?.removeAttribute("readonly");
		petTypeSelect?.removeAttribute("disabled");
		petNameInput?.focus();
	});

	// 저장 버튼 - AJAX 전송
	const saveBtn = document.getElementById("btnSaveProfile");
	saveBtn?.addEventListener("click", () => {
		const data = {
			userNickname: nicknameInput.value,
			petName: petNameInput.value,
			petType: parseInt(petTypeSelect.value)
		};

		fetch("/mypage/profile", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(data)
		})
			.then(res => res.text())
			.then(result => {
				if (result === "success") {
					showModal("프로필이 성공적으로 수정되었습니다.");
					setTimeout(() => location.reload(), 1000);
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

// 모달 표시
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
	if (modal) {
		modal.style.display = "none";
	}
}
