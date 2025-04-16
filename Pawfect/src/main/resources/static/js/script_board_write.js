let msg_subject = "제목을 입력하세요.";
let msg_content = "내용을 입력하세요.";
let error_write = "글 작성에 실패했습니다.\n잠시 후 다시 시도하세요.";
let str_content_placeholder = "내용을 입력하세요";

window.addEventListener(
	"DOMContentLoaded", 
	() => {
		const form = document.forms["writeForm"];
		const subjectInput = document.getElementById("subject");
		const modal = document.getElementById("confirmModal");
	    const confirmBtn = document.getElementById("confirmSubmit");
	    const cancelBtn = modal?.querySelector("button[onclick]");
		let quill = null;
		
	    
		if (form) {
			try {
				quill = new Quill('#editor-container', {
			        theme: 'snow',
			        placeholder: str_content_placeholder,
			        modules: {
			            toolbar: [['bold', 'italic', 'underline', 'strike'], ['image']]
			        }
			    });
			} catch(e) {
				console.warn("Quill initialization failed:", e);
			}
			// Replace direct submit with modal logic
	        const submitBtn = document.querySelector(".submit-btn");
	        if (submitBtn) {
	            submitBtn.addEventListener("click", () => {
	                const quillContent = quill.root.innerHTML;
	                const selectedTag = document.querySelector("input[name='tag-select']:checked");

	                if (!subjectInput.value.trim()) {
	                    alert(msg_subject);
	                    subjectInput.focus();
	                    return;
	                }
	                if (!quillContent || quillContent === "<p><br></p>") {
	                    alert(msg_content);
	                    return;
	                }

	                // Store data in hidden fields for modal confirm
	                form.querySelector("input[name='postContent']").value = quillContent;
	                form.querySelector("input[name='postType']").value = selectedTag ? selectedTag.value : "";

	                openConfirmModal();
	            });
	        }

	        if (confirmBtn) {
	            confirmBtn.addEventListener("click", () => {
	                closeConfirmModal();
	                form.submit(); // Only submit when confirmed
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
		
	}
);
