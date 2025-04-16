let msg_subject = "제목을 입력하세요.";
let msg_content = "내용을 입력하세요.";
let error_write = "글 작성에 실패했습니다.\n잠시 후 다시 시도하세요.";
let str_content_placeholder = "내용을 입력하세요";

window.addEventListener("DOMContentLoaded", () => {
	const form = document.forms["writeForm"];
	const subjectInput = document.getElementById("subject");
	const modal = document.getElementById("confirmModal");
	const confirmBtn = document.getElementById("confirmSubmit");
	const cancelBtn = modal?.querySelector("button[onclick]");
	let quill = null;

	if (form) {
		try {
			quill = new Quill("#editor-container", {
				theme: "snow",
				placeholder: str_content_placeholder,
				modules: {
					toolbar: [["bold", "italic", "underline", "strike"], ["image"]],
				},
			});
		} catch (e) {
			console.warn("Quill initialization failed:", e);
		}

		const submitBtn = document.querySelector(".submit-btn");
		if (submitBtn) {
			submitBtn.addEventListener("click", () => {
				const quillContent = quill.root.innerHTML;
				const selectedTag = document.querySelector("input[name='tag-select']:checked");

				// 제목 유효성 검사
				if (!subjectInput.value.trim()) {
					showModal(msg_subject); 
					subjectInput.focus();
					return;
				}

				// 내용 유효성 검사
				if (!quillContent || quillContent === "<p><br></p>") {
					showModal(msg_content);
					return;
				}

				// hidden input 채우기
				form.querySelector("input[name='postContent']").value = quillContent;
				form.querySelector("input[name='postType']").value = selectedTag ? selectedTag.value : "";

				openConfirmModal(); 
			});
		}

		if (confirmBtn) {
			confirmBtn.addEventListener("click", () => {
				closeConfirmModal();
				form.submit(); // 확인 후 제출
			});
		}

		if (cancelBtn) {
			cancelBtn.addEventListener("click", closeConfirmModal);
		}
	}

	function openConfirmModal() {
		if (modal) {
			modal.style.display = "block";
		}
	}

	function closeConfirmModal() {
		if (modal) {
			modal.style.display = "none";
		}
	}
});

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
