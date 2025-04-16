document.addEventListener("DOMContentLoaded", function () {
    // 지도 초기화
    const mapContainer = document.getElementById('map');
    const mapX = parseFloat(mapContainer.dataset.mapx);
    const mapY = parseFloat(mapContainer.dataset.mapy);

    const mapOption = {
        center: new kakao.maps.LatLng(mapY, mapX),
        level: 3
    };

    const map = new kakao.maps.Map(mapContainer, mapOption);

    const marker = new kakao.maps.Marker({
        position: map.getCenter()
    });
    marker.setMap(map);

    // 메인 슬라이드 초기화
	let mainSlideIndex = 0;
	const slider = document.querySelector(".main-slider");
	const slides = document.querySelectorAll(".main-slide");

	window.changeMainSlide = function (n) {
	    mainSlideIndex += n;
	    if (mainSlideIndex >= slides.length) mainSlideIndex = 0;
	    if (mainSlideIndex < 0) mainSlideIndex = slides.length - 1;

	    console.log(`Slider moved to: ${mainSlideIndex}`); // 디버깅용 로그

	    slider.style.transform = `translateX(-${mainSlideIndex * 100}%)`;
	}
	
    changeMainSlide(0);  // 초기화

    // 객실 슬라이드 초기화
    let slideIndices = [];

    window.plusSlide = function (n, roomIndex) {
        showSlide(slideIndices[roomIndex] += n, roomIndex);
    }

    function showSlide(n, roomIndex) {
        const sliders = document.querySelectorAll('.room-slider');
        const slides = sliders[roomIndex].querySelectorAll('.slide');

        if (!slideIndices[roomIndex]) slideIndices[roomIndex] = 0;

        if (n >= slides.length) slideIndices[roomIndex] = 0;
        if (n < 0) slideIndices[roomIndex] = slides.length - 1;

        sliders[roomIndex].style.transform = `translateX(-${slideIndices[roomIndex] * 100}%)`;
    }

    // 객실 슬라이드 초기화
    document.querySelectorAll('.room-slider').forEach((_, i) => {
        slideIndices[i] = 0;
        showSlide(0, i);
    });

	// 파일 첨부 미리보기
    const fileButton = document.getElementById("fileButton");
    const fileInput = document.getElementById("reviewImages");
	let allFiles = [];
	
    fileButton.addEventListener("click", function () {
        fileInput.click();  // 파일 선택 창 열기
    });

    // 파일 선택 후 미리보기
	fileInput.addEventListener("change", function (event) {
	    const newFiles = Array.from(event.target.files);
	    const previewContainer = document.getElementById("preview-container");

	    const currentCount = allFiles.length;
		const MAX_IMAGES = 5;

	    if (currentCount + newFiles.length > MAX_IMAGES) {
	        showModal(`이미지는 최대 ${MAX_IMAGES}장까지 첨부할 수 있습니다.`);
	        return;
	    }
		
		const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
		    const invalidFile = newFiles.find(file => !allowedTypes.includes(file.type));
		    if (invalidFile) {
		        showModal(`이미지 파일만 첨부 가능합니다. (${invalidFile.name})`);
		        return;
		    }

	    newFiles.forEach((file, index) => {
	        allFiles.push(file); // ✅ allFiles에 추가

	        const reader = new FileReader();
	        reader.onload = function (e) {
	            const wrapper = document.createElement("div");
	            wrapper.className = "preview-wrapper";

	            const img = document.createElement("img");
	            img.src = e.target.result;
	            img.classList.add("preview-image");

	            const deleteBtn = document.createElement("button");
	            deleteBtn.innerText = "×";
	            deleteBtn.classList.add("delete-btn");
				deleteBtn.style.width = "20px";
				deleteBtn.style.height = "20px";
				deleteBtn.style.padding = "0";
				deleteBtn.style.lineHeight = "20px";
				deleteBtn.style.fontSize = "12px";
				deleteBtn.style.fontWeight = "bold";
				deleteBtn.style.textAlign = "center";
				deleteBtn.style.border = "none";
				deleteBtn.style.background = "#bababa";
				deleteBtn.style.color = "white";
				deleteBtn.style.borderRadius = "30%";
				deleteBtn.style.cursor = "pointer";
				deleteBtn.style.position = "absolute";
				deleteBtn.style.top = "-8px";
				deleteBtn.style.right = "-8px";
				deleteBtn.style.zIndex = "1000";

	            // 파일 삭제 버튼 클릭 시
	            deleteBtn.addEventListener("click", function () {
	                const idx = Array.from(previewContainer.children).indexOf(wrapper);
	                allFiles.splice(idx, 1); // ✅ 실제 배열에서도 제거
	                wrapper.remove();
	                resetFileInput(); // ✅ input 갱신
	            });

	            wrapper.appendChild(img);
	            wrapper.appendChild(deleteBtn);
	            previewContainer.appendChild(wrapper);
	        };

	        reader.readAsDataURL(file);
	    });
		console.log("제출 직전 파일 목록:", fileInput.files);
		for (const file of fileInput.files) {
		  console.log("파일:", file.name, file.size);
		}

	    resetFileInput(); // ✅ input 갱신
	    //fileInput.value = "";
	});

    // 선택된 파일 갯수 표시 (선택된 파일이 있을 경우에만 갱신)
	function resetFileInput() {
        const dataTransfer = new DataTransfer();
        allFiles.forEach(file => dataTransfer.items.add(file));
        fileInput.files = dataTransfer.files;
    }
	
	// 리뷰 폼 제출 시 유효성 검사 + 로그인 체크
	document.querySelector("form").addEventListener("submit", function (event) {
		const isLoggedIn = document.body.dataset.loggedIn === "true";
		
	    // 로그인 확인
		if (!isLoggedIn) {
	        event.preventDefault(); // 제출 막기
	        showConfirmModal("로그인이 필요한 서비스입니다.\n로그인 하시겠습니까?", () => {
	            location.href = "/loginForm";
	        });
	        return;
	    }

	    if (fileInput.files.length === 0) {
	        event.preventDefault();
	        showModal("이미지를 최소 1장 이상 첨부해주세요.");
			closeModal;
	        return;
	    }
	});
	const bookmarkBtn = document.getElementById("bookmarkBtn");
	const bookmarkCountSpan = document.getElementById("bookmarkCount");

	if (bookmarkBtn) {
	    bookmarkBtn.addEventListener("click", function () {
	        const isLoggedIn = document.body.dataset.loggedIn === "true";
	        if (!isLoggedIn) {
	            showConfirmModal("로그인이 필요한 서비스입니다.\n로그인 하시겠습니까?", () => {
	                location.href = "/loginForm";
	            });
	            return;
	        }

	        const dto = {
	            contentId: parseInt(this.dataset.contentid),
	            contentTypeId: parseInt(this.dataset.contenttypeid),
	            title: this.dataset.title,
	            firstimage: this.dataset.firstimage,
	            mapX: parseFloat(this.dataset.mapx),
	            mapY: parseFloat(this.dataset.mapy),
	            addr1: this.dataset.addr1
	        };

	        fetch("/travel/bookmark/toggle", {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify(dto)
	        })
	        .then(res => res.text())
	        .then(result => {
	            if (result === "saved") {
					showModal("북마크 추가");
				  	closeModal;
	                this.classList.add("bookmarked");
	                this.textContent = "✅";
	                updateBookmarkCount(1);
	            } else if (result === "deleted") {
					showModal("북마크 삭제");
				  	closeModal;
	                this.classList.remove("bookmarked");
	                this.textContent = "🔖";
	                updateBookmarkCount(-1);
	            }
	        });
	    });
	}

	// 북마크 수 실시간 증가/감소
	function updateBookmarkCount(delta) {
	    if (!bookmarkCountSpan) return;
	    const current = parseInt(bookmarkCountSpan.textContent);
	    bookmarkCountSpan.textContent = current + delta;
	}

});

