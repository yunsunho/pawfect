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
});

