// theme.js (추가/수정)

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

  // 초기 contentTypeId는 서버에서 전달한 값 사용
  let selectedContentTypeId = parseInt(new URLSearchParams(location.search).get("contentTypeId")) || 12;
  let selectedArrange = 'O';
  let currentPage = 1;
  
  document.addEventListener("click", function(e) {
    if (e.target.classList.contains("bookmark")) {
      const btn = e.target;

      // 데이터 추출
      const dto = {
        contentId: btn.dataset.contentid,
        contentTypeId: btn.dataset.contenttypeid,
        title: btn.dataset.title,
        firstimage: btn.dataset.firstimage,
        mapX: btn.dataset.mapx,
        mapY: btn.dataset.mapy,
        addr1: btn.dataset.addr1
      };

      // AJAX 요청
      fetch("/travel/bookmark/toggle", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(dto)
      })
      .then(res => {
        if (res.redirected) {
          location.href = res.url; // 로그인 페이지로 이동
          return;
        }
        return res.text();
      })
      .then(result => {
        if (result === "saved") {
          alert("북마크 추가됨");
          btn.textContent = "✅"; // 예: 추가됨 표시
        } else if (result === "deleted") {
          alert("북마크 삭제됨");
          btn.textContent = "🔖"; // 기본 아이콘으로 복귀
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
	  const bookmarkArray = bookmarked ? bookmarked.toString().split(",").map(Number) : [];

	  container.innerHTML = '';
	  
	  data.forEach(item => {
	    const isBookmarked = bookmarkArray.includes(Number(item.contentid)); // ✅ 숫자로 변환해서 비교

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

    const maxVisible = 5; // 보여줄 최대 페이지 수
    let startPage = Math.max(1, currentPage - Math.floor(maxVisible / 2));
    let endPage = startPage + maxVisible - 1;

    if (endPage > totalPages) {
      endPage = totalPages;
      startPage = Math.max(1, endPage - maxVisible + 1);
    }

    // 이전 버튼
    if (currentPage > 1) {
      const prev = document.createElement('button');
      prev.textContent = '<';
      prev.addEventListener('click', () => {
        currentPage--;
        fetchAndRender();
      });
      pagination.appendChild(prev);
    }

    // 페이지 숫자 버튼
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

    // 다음 버튼
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

  // 초기 로드
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

  // 진입 시 관광지 + 제목순으로 시작
  fetchAndRender();
});
