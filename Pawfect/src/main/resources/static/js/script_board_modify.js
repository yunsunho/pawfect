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

            targetForm.querySelector("input[name='postContent']").value = quillContent;
            targetForm.querySelector("input[name='postType']").value = selectedTag ? selectedTag.value : "";

            closeConfirmModal();
            targetForm.submit();
        }
    });
});

// Show confirmation modal
function openConfirmModal(buttonElement) {
    targetForm = buttonElement.closest("form");
    const subjectInput = targetForm.querySelector("input[name='postTitle']");
    const quillContent = quill.root.innerHTML;

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

    // 유효성 통과 → 확인 모달 열기
    document.getElementById("confirmModal").style.display = "block";
}

// Close confirmation modal
function closeConfirmModal() {
    document.getElementById("confirmModal").style.display = "none";
}

// Show general alert modal
function showModal(message) {
    const modal = document.getElementById("commonModal");
    const msgBox = document.getElementById("modalMessage");
    if (modal && msgBox) {
        msgBox.innerText = message;
        modal.style.display = "block";
    }
}

// Close general alert modal
function closeModal() {
    const modal = document.getElementById("commonModal");
    if (modal) modal.style.display = "none";
}
