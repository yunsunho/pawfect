let msg_subject = "제목을 입력하세요.";
let msg_content = "내용을 입력하세요.";
let error_write = "글 작성에 실패했습니다.\n잠시 후 다시 시도하세요.";

let quill;

window.addEventListener("DOMContentLoaded", () => {
    // Initialize Quill editor with image upload support
    quill = new Quill('#editor-container', {
        theme: 'snow',
        placeholder: '내용을 입력하세요',
        modules: {
            toolbar: {
                container: [
                    ['bold', 'italic', 'underline', 'strike'],
                    ['blockquote', 'code-block'],
                    [{ 'header': 1 }, { 'header': 2 }],
                    [{ 'list': 'ordered' }, { 'list': 'bullet' }],
                    [{ 'script': 'sub' }, { 'script': 'super' }],
                    [{ 'indent': '-1' }, { 'indent': '+1' }],
                    [{ 'direction': 'rtl' }],
                    [{ 'size': ['small', false, 'large', 'huge'] }],
                    [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                    [{ 'color': [] }, { 'background': [] }],
                    [{ 'font': [] }],
                    [{ 'align': [] }],
                    ['clean'],
                    ['image', 'link']
                ],
                handlers: {
                    image: imageHandler
                }
            }
        }
    });

    // 이미지 업로드 핸들러
    function imageHandler() {
        const input = document.createElement('input');
        input.setAttribute('type', 'file');
        input.setAttribute('accept', 'image/*');
        input.click();

        input.onchange = async () => {
            const file = input.files[0];
            if (file) {
                const formData = new FormData();
                formData.append('image', file);

                try {
                    const response = await fetch('/upload/image', {
                        method: 'POST',
                        body: formData
                    });

                    const result = await response.json();
                    const range = quill.getSelection();
                    quill.insertEmbed(range.index, 'image', result.imageUrl); // 서버에서 이미지 URL 반환
                } catch (err) {
                    alert('이미지 업로드에 실패했습니다.');
                }
            }
        };
    }

    // 글쓰기
    let writeform = document.querySelector("form[name='writeform']");
    if (writeform) {
        writeform.addEventListener("submit", (event) => {
            let subject = document.querySelector("input[name='subject']");
            let tagHidden = document.querySelector("input[name='postType']");
            let tag = document.querySelector(".tag.active")?.textContent || "";
            let content = quill.root.innerHTML.trim();

            if (!subject.value.trim()) {
                alert(msg_subject);
                event.preventDefault();
                subject.focus();
                return;
            }
            if (!content || content === "<p><br></p>") {
                alert(msg_content);
                event.preventDefault();
                return;
            }

            // Set values in hidden form fields
            tagHidden.value = tag;
            document.querySelector("input[name='content']").value = content;
        });
    }

    // 태그 선택
    document.querySelectorAll('.tag').forEach(tag => {
        tag.addEventListener("click", function () {
            document.querySelectorAll('.tag').forEach(t => t.classList.remove('active'));
            this.classList.add('active');
        });
    });
});
