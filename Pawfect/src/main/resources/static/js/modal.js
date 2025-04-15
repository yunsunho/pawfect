// 모달 (확인)
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

// 컨펌 모달 (확인/취소)
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