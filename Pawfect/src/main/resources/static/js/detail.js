document.addEventListener("DOMContentLoaded", function () {
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
});

let mainSlideIndex = 0;

function changeMainSlide(n) {
  const slider = document.querySelector(".main-slider");
  const slides = document.querySelectorAll(".main-slide");

  mainSlideIndex += n;

  if (mainSlideIndex >= slides.length) mainSlideIndex = 0;
  if (mainSlideIndex < 0) mainSlideIndex = slides.length - 1;

  slider.style.transform = `translateX(-${mainSlideIndex * 100}%)`;
}

// 초기화
document.addEventListener("DOMContentLoaded", () => {
  changeMainSlide(0);
});



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

// 초기화
document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll('.room-slider').forEach((_, i) => {
    slideIndices[i] = 0;
    showSlide(0, i);
  });
});