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
