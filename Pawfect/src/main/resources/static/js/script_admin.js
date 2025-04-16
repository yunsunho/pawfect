let targetForm = null;

function openConfirmModal(buttonElement) {
    // Save the form reference globally
    targetForm = buttonElement.closest("form");
    document.getElementById("confirmModal").style.display = "block";
}

function closeConfirmModal() {
    document.getElementById("confirmModal").style.display = "none";
}

document.addEventListener("DOMContentLoaded", function () {
    const confirmButton = document.getElementById("confirmSubmit");
    confirmButton.addEventListener("click", function () {
        if (targetForm) {
            targetForm.submit();
        }
    });
});


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
window.addEventListener(
	"DOMContentLoaded", 
	() => {
		const sidebarLinks = document.querySelectorAll('.sidebar li a');
		
		sidebarLinks.forEach(link => {
			const currentPath = window.location.pathname;
			if (currentPath.startsWith(link.getAttribute('href'))) {
				link.parentElement.classList.add('active');
			}
		});
		
		const logoutBtn = document.getElementById("adminLogoutBtn");
		if (logoutBtn) {
			logoutBtn.addEventListener("click", () => {
				showConfirmModal("정말 로그아웃하시겠습니까?", () => {
					location.href = "/logout";
				});
			});
		}
});

