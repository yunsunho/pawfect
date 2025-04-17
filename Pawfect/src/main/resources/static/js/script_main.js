document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("keywordInput");
    const resultsBox = document.getElementById("searchResults");

    let timeout = null;

    input.addEventListener("input", function () {
        const keyword = input.value.trim();

        clearTimeout(timeout);

        if (keyword.length === 0) {
            resultsBox.innerHTML = "";
            return;
        }

        timeout = setTimeout(() => {
            fetch(`/main?keyword=${encodeURIComponent(keyword)}`)
                .then(response => response.text())
                .then(html => {
                    const tempDiv = document.createElement("div");
                    tempDiv.innerHTML = html;
                    const resultFragment = tempDiv.querySelector("#ajaxResults");
                    resultsBox.innerHTML = resultFragment ? resultFragment.innerHTML : "<p>결과가 없습니다.</p>";
                })
                .catch(error => {
                    resultsBox.innerHTML = `<p>에러 발생: ${error}</p>`;
                });
        }, 300); // wait 300ms after typing
    });
});
