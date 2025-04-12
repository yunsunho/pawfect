let msg_subject = "제목을 입력하세요.";
let msg_content = "내용을 입력하세요.";
let error_modify = "글 수정에 실패했습니다.\n잠시 후 다시 시도하세요.";
let str_content_placeholder = "내용을 입력하세요";

window.addEventListener(
	"DOMContentLoaded", 
	() => {
		const form = document.forms["modifyForm"];
		
		const initialTagValue = document.getElementById("initial-tag").value;
		const radioToCheck = document.querySelector(`input[name="tag-select"][value="${initialTagValue}"`);
		if (radioToCheck) {
			radioToCheck.checked = true;
		} else {
			document.getElementById("tag-default").checked = true;
		}
		
		
		let quill = null;

		try {
			quill = new Quill('#editor-container', {
		        theme: 'snow',
		        placeholder: str_content_placeholder,
		        modules: {
		            toolbar: [['bold', 'italic', 'underline', 'strike'], ['image']]
		        }
		    });
			
			let existingContent = document.getElementById("existingContent").value;
			
			if (existingContent) {
				quill.root.innerHTML = existingContent;
			}
		} catch(e) {
			console.warn("Quill initialization failed:", e);
		}
		
		
		
		if (form) {
			form.addEventListener("submit", (e) => {
				const quillContent = quill.root.innerHTML;
				const selectedTag = document.querySelector("input[name='tag-select']:checked");
				
				form.querySelector("input[name='postContent']").value = quillContent;
				form.querySelector("input[name='postType']").value = selectedTag ? selectedTag.value : "";
				
				if (!subjectInput.value.trim()) {
					alert(msg_subject);
					subjectInput.focus();
					e.preventDefault();
					return;
				}
				if (!quillContent || quillContent === "<p><br></p>") {
					alert(msg_content);
					e.preventDefault();
				}				
			})
		}
	}
);