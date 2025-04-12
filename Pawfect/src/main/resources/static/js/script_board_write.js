let msg_subject = "제목을 입력하세요.";
let msg_content = "내용을 입력하세요.";
let error_write = "글 작성에 실패했습니다.\n잠시 후 다시 시도하세요.";
let str_content_placeholder = "내용을 입력하세요";

window.addEventListener(
	"DOMContentLoaded", 
	() => {
		// writeForm.jsp (Quill initialization)
		const form = document.forms["writeForm"];
		const subjectInput = document.getElementById("subject");
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
			
			form.addEventListener("submit", (e) => {
				const quillContent = quill.root.innerHTML;
				const selectedTag = document.querySelector("input[name='tag-select']:checked");
				
				// Set values into hidden fields
				form.querySelector("input[name='postTitle']").value = subjectInput.value;
				form.querySelector("input[name='postContent']").value = quillContent;
				form.querySelector("input[name='postType']").value = selectedTag ? selectedTag.value : "";
				
				// Validation
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
			});
		}
		
	}
);
