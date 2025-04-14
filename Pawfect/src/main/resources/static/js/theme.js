document.addEventListener('DOMContentLoaded', () => {
  const container = document.querySelector('.theme-container');
  const sortSelect = document.querySelector('.sort-box select');
  const tabs = document.querySelectorAll('.theme-tab');

  const contentTypeMap = {
    '관광지': 12,
    '문화시설': 14,
    '행사/공연/축제': 15,
    '레포츠': 28,
    '숙박': 32,
    '쇼핑': 38,
    '음식점': 39
  };

  const arrangeMap = {
    '제목순': 'O',
    '리뷰순': '',
    '별점순': '',
    '북마크순': ''
  };
  
  let selectedContentTypeId = parseInt(new URLSearchParams(location.search).get("contentTypeId")) || 12;
  let selectedArrange = 'O';
  let currentPage = 1;
  let bookmarkArray = Array.isArray(bookmarked) ? bookmarked : [];
  
  const pending = sessionStorage.getItem("pendingBookmark");
   if (pending) {
     const dto = JSON.parse(pending);

     fetch("/travel/bookmark/toggle", {
       method: "POST",
       headers: {
         "Content-Type": "application/json"
       },
       body: JSON.stringify(dto)
     })
     .then(res => res.text())
	 .then(result => {
	     const contentId = Number(dto.contentId);
	     const index = bookmarkArray.indexOf(contentId);

	     if (result === "saved") {
	       alert("북마크 추가됨 (자동 실행)");
	       if (index === -1) bookmarkArray.push(contentId);
	     } else if (result === "deleted") {
	       alert("북마크 삭제됨 (자동 실행)");
	       if (index > -1) bookmarkArray.splice(index, 1);
	     }
	     sessionStorage.removeItem("pendingBookmark");
	     fetchAndRender(); // 마커 갱신
	   });
   }

  document.addEventListener("click", function(e) {
    if (e.target.classList.contains("bookmark")) {
      const btn = e.target;

      const dto = {
        contentId: btn.dataset.contentid,
        contentTypeId: btn.dataset.contenttypeid,
        title: btn.dataset.title,
        firstimage: btn.dataset.firstimage,
        mapX: btn.dataset.mapx,
        mapY: btn.dataset.mapy,
        addr1: btn.dataset.addr1
      };

	  // 북마크 버튼 눌렀을 때
	  fetch("/travel/bookmark/toggle", {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/json"
	    },
	    body: JSON.stringify(dto)
	  })
	  .then(res => {
		if (res.redirected) {
		  const currentUrl = location.pathname + location.search;

		  // ✅ 북마크 정보 저장
		  sessionStorage.setItem("afterLoginRedirect", currentUrl);
		  sessionStorage.setItem("pendingBookmark", JSON.stringify(dto)); // 👈 이거 추가

		  location.href = res.url; // 로그인 폼으로 이동
		  return;
		}
	    return res.text();
	  })

	  .then(result => {
	    if (!result) return;
	    const contentId = Number(dto.contentId);
	    const index = bookmarkArray.indexOf(contentId);

	    if (result === "saved") {
	      alert("북마크 추가됨");
	      if (index === -1) bookmarkArray.push(contentId); // 직접 배열 수정
	      btn.textContent = "✅";
	    } else if (result === "deleted") {
	      alert("북마크 삭제됨");
	      if (index > -1) bookmarkArray.splice(index, 1); // 배열에서 제거
	      btn.textContent = "🔖";
	    }
	  });

    }
  });

  const fetchAndRender = async () => {
    try {
      const response = await fetch(`/api/themeData?contentTypeId=${selectedContentTypeId}&arrange=${selectedArrange}&pageNo=${currentPage}`);
      const result = await response.json();
      const data = result.list;
      const totalPages = result.totalPages;

      container.innerHTML = '';

      data.forEach(item => {
        const isBookmarked = bookmarkArray.includes(Number(item.contentid));

        const card = document.createElement("div");
        card.className = "theme-card";
        card.innerHTML = `
          <a href="/detail/${item.contentid}/${item.contenttypeid}" class="theme-link">
            <img src="${item.firstimage || '/images/no-image.png'}" alt="이미지 없음">
            <div class="theme-info">
              <h3>${item.title}</h3>
              <p>${item.addr1}</p>
            </div>
          </a>
          <div class="bookmark"
               data-contentid="${item.contentid}"
               data-contenttypeid="${item.contenttypeid}"
               data-title="${item.title}"
               data-firstimage="${item.firstimage}"
               data-mapx="${item.mapx}"
               data-mapy="${item.mapy}"
               data-addr1="${item.addr1}">
            ${isBookmarked ? "✅" : "🔖"}
          </div>
        `;
        container.appendChild(card);
      });

      renderPagination(totalPages);
    } catch (err) {
      console.error('데이터 불러오기 실패:', err);
    }
  };

  const renderPagination = (totalPages) => {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';

    const maxVisible = 5;
    let startPage = Math.max(1, currentPage - Math.floor(maxVisible / 2));
    let endPage = startPage + maxVisible - 1;

    if (endPage > totalPages) {
      endPage = totalPages;
      startPage = Math.max(1, endPage - maxVisible + 1);
    }

    if (currentPage > 1) {
      const prev = document.createElement('button');
      prev.textContent = '<';
      prev.addEventListener('click', () => {
        currentPage--;
        fetchAndRender();
      });
      pagination.appendChild(prev);
    }

    for (let i = startPage; i <= endPage; i++) {
      const btn = document.createElement('button');
      btn.textContent = i;
      if (i === currentPage) btn.classList.add('active');
      btn.addEventListener('click', () => {
        currentPage = i;
        fetchAndRender();
      });
      pagination.appendChild(btn);
    }

    if (currentPage < totalPages) {
      const next = document.createElement('button');
      next.textContent = '>';
      next.addEventListener('click', () => {
        currentPage++;
        fetchAndRender();
      });
      pagination.appendChild(next);
    }
  };

  fetchAndRender();

  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      tabs.forEach(t => t.classList.remove('active'));
      tab.classList.add('active');
      selectedContentTypeId = contentTypeMap[tab.textContent.trim()];
      currentPage = 1;
      fetchAndRender();
    });
  });

  sortSelect.addEventListener('change', () => {
    const selectedText = sortSelect.options[sortSelect.selectedIndex].text;
    selectedArrange = arrangeMap[selectedText] || 'O';
    currentPage = 1;
    fetchAndRender();
  });
});
