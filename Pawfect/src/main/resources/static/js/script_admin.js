function openConfirmModal() {
    document.getElementById("confirmModal").style.display = "block";
}

function closeConfirmModal() {
    document.getElementById("confirmModal").style.display = "none";
}

document.addEventListener("DOMContentLoaded", function () {
    const confirmButton = document.getElementById("confirmSubmit");
    confirmButton.addEventListener("click", function () {
        // Submit the form when "확인" is clicked
        document.querySelector("form").submit();
    });
});
