// theme.js (추가/수정)

document.addEventListener('DOMContentLoaded', () => {
  const container = document.querySelector('.theme-container');
  const sortSelect = document.querySelector('.sort-box select');
  const areaTabs  = document.querySelectorAll('.area-tab');

  const areaMap = {
    '서울':1,
    '인천':2,
	'대전':3,
	'대구':4,
	'광주':5,
	'부산':6,
	'울산':7,
	'세종':8,
	'경기':31,
	'강원':32,
	'충북':33,
	'충남':34,
	'경북':35,
	'경남':36,
	'전북':37,
	'전남':38,
	'제주':39
  };

  const arrangeMap = {
    '제목순': 'O',
    '리뷰순': '',
    '별점순': '',
    '북마크순': ''
  };
  
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


  // 초기 contentTypeId는 서버에서 전달한 값 사용
  let selectedAreaCode = parseInt(new URLSearchParams(location.search).get("areaCode")) || "";
  let selectedSigunguCode = "";
  let selectedArrange = 'O';
  let currentPage = 1;

  const fetchAndRender = async () => {
    try {
      const response = await fetch(`/api/areaData?areaCode=${selectedAreaCode}&sigunguCode=${selectedSigunguCode || ""}&arrange=${selectedArrange}&pageNo=${currentPage}`);
      const result = await response.json();
      const data = result.list;
      const totalPages = result.totalPages;

	  container.innerHTML = '';
	  data.forEach(item => {
	    const card = document.createElement('div');
	    card.className = 'theme-card';

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
	        🔖
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
  
  let sigunguData = {};

  fetch("/data/sigunguData.json")
    .then(res => res.json())
    .then(data => {
      sigunguData = data;
      console.log("시군구 데이터 불러옴", sigunguData);

      // 💡 시군구 데이터 로딩 완료 후에만 탭 클릭 이벤트 연결
      areaTabs.forEach(tab => {
        tab.addEventListener('click', () => {
          areaTabs.forEach(t => t.classList.remove('active'));
          tab.classList.add('active');
          selectedAreaCode = tab.dataset.area;
          selectedSigunguCode = "";
          currentPage = 1;
          renderSigunguSubmenu(selectedAreaCode);
          fetchAndRender();
        });
      });

    })
    .catch(err => console.error("시군구 데이터 로딩 실패:", err));


  function renderSigunguSubmenu(areaCode) {
    const sigunguMenu = document.querySelector(".sigungu-submenu");
    sigunguMenu.innerHTML = ""; // 초기화

    const sigunguList = sigunguData[areaCode];
    if (!sigunguList) {
      sigunguMenu.style.display = "none";
      return;
    }

	sigunguList.forEach((item) => {
	  const btn = document.createElement("a");
	  btn.className = "sigungu-tab";
	  btn.textContent = item.name;
	  btn.dataset.sigungu = item.code;

	  btn.addEventListener("click", () => {
	    document.querySelectorAll(".sigungu-tab").forEach(t => t.classList.remove("active"));
	    btn.classList.add("active");
	    selectedSigunguCode = btn.dataset.sigungu;
	    currentPage = 1;
	    fetchAndRender();
	  });

	  sigunguMenu.appendChild(btn);
	});

    sigunguMenu.style.display = "flex"; // 혹은 block
  }


  sortSelect.addEventListener('change', () => {
    const selectedText = sortSelect.options[sortSelect.selectedIndex].text;
    selectedArrange = arrangeMap[selectedText] || 'O';
	currentPage = 1;
    fetchAndRender();
  });

  // 진입 시 관광지 + 제목순으로 시작
  // fetchAndRender();
});
