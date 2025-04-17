function goToPage(page) {
  fetch(`/travel/reviews/${contentId}?page=${page}`)
    .then(res => res.text())
    .then(html => {
      document.getElementById("review-box").innerHTML = html;
      window.scrollTo({ top: document.getElementById("review-box").offsetTop, behavior: 'smooth' });
    });
}

function confirmDelete(reviewId, reviewOwnerId) {
  const isLoggedIn = document.body.dataset.loggedIn === "true";
  const loginUserId = document.body.dataset.userId;

  if (!isLoggedIn) {
      const currentUrl = location.href;

      showConfirmModal("로그인이 필요한 서비스입니다.\n로그인 하시겠습니까?", () => {
          fetch("/setRedirectUrl", {
              method: "POST",
              headers: {
                  "Content-Type": "application/json"
              },
              body: JSON.stringify({ url: currentUrl })
          }).then(() => {
              location.href = "/loginForm";
          });
      });

      return;
  }


  if (loginUserId !== reviewOwnerId) {
	showModal("작성자만 삭제할 수 있습니다.");
	closeModal;
    return;
  }

  showConfirmModal("정말 삭제하시겠습니까?", () => {
      fetch(`/travel/review/delete/${reviewId}`, {
        method: 'DELETE'
      })
        .then(res => {
          if (res.ok) {
            showModal("삭제되었습니다.");
            location.reload();
          } else {
            showModal("삭제 실패");
          }
        })
        .catch(error => {
          console.error("삭제 중 오류:", error);
        });
    });
}

