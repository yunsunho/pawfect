let msg_subject = "제목을 입력하세요.";
let msg_content = "내용을 입력하세요.";
let error_modify = "글 수정에 실패했습니다.\n잠시 후 다시 시도하세요.";
let str_content_placeholder = "내용을 입력하세요";
let quill = null;
let targetForm = null;

window.addEventListener("DOMContentLoaded", () => {
    const form = document.forms["modifyForm"];

    // Set the initial tag selection
    const initialTagValue = document.getElementById("initial-tag").value;
    const radioToCheck = document.querySelector(`input[name="tag-select"][value="${initialTagValue}"]`);
    if (radioToCheck) {
        radioToCheck.checked = true;
    } else {
        document.getElementById("tag-default").checked = true;
    }

    // Initialize Quill editor
    try {
        quill = new Quill('#editor-container', {
            theme: 'snow',
            placeholder: str_content_placeholder,
            modules: {
                toolbar: [['bold', 'italic', 'underline', 'strike'], ['image']]
            }
        });

        // Set existing content into the Quill editor
        let existingContent = document.getElementById("existingContent").value;
        if (existingContent) {
            quill.root.innerHTML = existingContent;
        }
    } catch (e) {
        console.warn("Quill initialization failed:", e);
    }

    // Confirm modal button logic
    const confirmButton = document.getElementById("confirmSubmit");
    confirmButton.addEventListener("click", function () {
        if (targetForm) {
            const quillContent = quill.root.innerHTML;
            const selectedTag = document.querySelector("input[name='tag-select']:checked");
            const subjectInput = document.querySelector("input[name='postTitle']");

            if (!subjectInput.value.trim()) {
                alert(msg_subject);
                subjectInput.focus();
                return;
            }

            if (!quillContent || quillContent === "<p><br></p>") {
                alert(msg_content);
                return;
            }

            targetForm.querySelector("input[name='postContent']").value = quillContent;
            targetForm.querySelector("input[name='postType']").value = selectedTag ? selectedTag.value : "";

            targetForm.submit();
        }
    });
});

// Show confirmation modal
function openConfirmModal(buttonElement) {
    targetForm = buttonElement.closest("form");
    document.getElementById("confirmModal").style.display = "block";
}

// Close confirmation modal
function closeConfirmModal() {
    document.getElementById("confirmModal").style.display = "none";
}
