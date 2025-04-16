function goToPage(page) {
  fetch(`/travel/reviews/${contentId}?page=${page}`)
    .then(res => res.text())
    .then(html => {
      document.getElementById("review-box").innerHTML = html;
      window.scrollTo({ top: document.getElementById("review-box").offsetTop, behavior: 'smooth' });
    });
}
