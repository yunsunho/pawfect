window.addEventListener(
	"DOMContentLoaded", 
	() => {
		// sideboard style tag button when clicked
		const urlParams = new URLSearchParams(window.location.search);
		const postType = urlParams.get('postType');
		if (postType != null) {
			const button = document.querySelector(`.tag-filter a[href*="postType=${postType}"]`);
			
			if (button) {
				button.classList.add('active');
			}
		}
	}
);

// content.jsp
// dropdown menu
function dropdown() {
	const menu = document.getElementById("menu-view");
	menu.style.display = (menu.style.display === 'block' ? 'none' : 'block');
}

function toggleReplyBox(button) {
	const commentId = button.getAttribute("data-comment-id");
	const replyForm = document.getElementById("reply-form-" + commentId);
	
	if (replyForm.style.display === "none") {
		replyForm.style.display = "block";
	} else {
		replyForm.style.display = "none";
	}
}

function cancelReplyBox(button) {
	const replyForm = button.closest('.reply-form');
	if (replyForm) {
		replyForm.style.display = "none";
		
		const form = replyForm.querySelector('form');
		if (form) {
			form.reset();
		}
	}
}


// delete modal
let deletePostId = null;

function confirmDelete(postId) {
	deletePostId = postId;
	document.getElementById('deleteModal').style.display = 'block';
}

function closeModal() {
	document.getElementById('deleteModal').style.display = 'none';
}

document.addEventListener(
	"DOMContentLoaded",
	function() {
		const confirmBtn = document.getElementById('confirmDeleteBtn');
		if (confirmBtn) {
			confirmBtn.addEventListener('click', function() {
				if (deletePostId != null) {
					window.location.href = `/board/delete?num=${deletePostId}`;
				}
			});
		}
		window.onclick = function (event) {
			const modal = document.getElementById('deleteModal');
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}
	}
);