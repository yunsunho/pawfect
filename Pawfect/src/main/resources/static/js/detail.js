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

    function changeMainSlide(n) {
        mainSlideIndex += n;

        if (mainSlideIndex >= slides.length) mainSlideIndex = 0;
        if (mainSlideIndex < 0) mainSlideIndex = slides.length - 1;

        slider.style.transform = `translateX(-${mainSlideIndex * 100}%)`;
    }

    changeMainSlide(0);  // 초기화

    // 객실 슬라이드 초기화
    let slideIndices = [];

    function plusSlide(n, roomIndex) {
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

    fileButton.addEventListener("click", function () {
        fileInput.click();  // 파일 선택 창 열기
    });

    // 파일 선택 후 미리보기
    fileInput.addEventListener("change", function (event) {
        const files = event.target.files;
        const previewContainer = document.getElementById("preview-container");

        // 기존 미리보기 이미지 초기화 하지 않고 새로운 이미지를 추가
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();

            reader.onload = function (e) {
                const img = document.createElement("img");
                img.src = e.target.result;
                img.classList.add("preview-image");
                previewContainer.appendChild(img);
            };

            reader.readAsDataURL(file); // 파일을 Data URL 형식으로 읽어서 미리보기
        }

        // 선택된 파일 갯수 표시 (선택된 파일이 있을 경우에만 갱신)
        const fileCount = files.length;
        const fileLabel = document.querySelector("#fileButton + span");
        fileLabel.textContent = `${fileCount}개의 사진 선택됨`;
    });
});
