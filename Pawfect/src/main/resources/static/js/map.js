document.addEventListener("DOMContentLoaded", function () {
  const mapContainer = document.getElementById("map");
  const placeList = document.getElementById("place-list");
  const searchBtn = document.getElementById("searchByMapBtn");
  const sidebar = document.getElementById('sidebar');
  const closeSidebarBtn = document.getElementById('closeSidebarBtn');
    
  let selectedRadius = 5000;
  let selectedContentTypeId = '';
  let lat = 37.5665;
  let lon = 126.9780;
  const mapOption = {
    center: new kakao.maps.LatLng(lat, lon),
    level: 7,
  };
  const map = new kakao.maps.Map(mapContainer, mapOption);
  const clusterer = new kakao.maps.MarkerClusterer({
    map: map,
    averageCenter: true,
    minLevel: 5,
  });

  let currentLat = lat;
  let currentLon = lon;

  kakao.maps.event.addListener(map, "center_changed", function () {
    const center = map.getCenter();
    currentLat = center.getLat();
    currentLon = center.getLng();
  });

  const renderPlaces = (places) => {
    placeList.innerHTML = "";
    let newMarkers = [];

    places.forEach(place => {
      // ⛳ 마커 아이콘 경로 설정
      let iconPath = "";
      switch (place.contenttypeid) {
        case "12": iconPath = "/images/marker/marker-tour.png"; break;
        case "14": iconPath = "/images/marker/marker-culture.png"; break;
        case "15": iconPath = "/images/marker/marker-event.png"; break;
        case "28": iconPath = "/images/marker/marker-leports.png"; break;
        case "32": iconPath = "/images/marker/marker-hotel.png"; break;
        case "38": iconPath = "/images/marker/marker-shopping.png"; break;
        case "39": iconPath = "/images/marker/marker-food.png"; break;
        default: iconPath = "/images/marker/marker-default.png"; break;
      }

      const imageSize = new kakao.maps.Size(40, 40);
      const markerImage = new kakao.maps.MarkerImage(iconPath, imageSize);

      const position = new kakao.maps.LatLng(place.mapy, place.mapx);
      const marker = new kakao.maps.Marker({
        position,
        image: markerImage
      });
      newMarkers.push(marker);

      // 📝 리스트 카드
      const card = document.createElement('div');
      card.className = 'place-card';
      card.setAttribute('data-contentid', place.contentid);
      card.setAttribute('data-contenttypeid', place.contenttypeid);
      card.innerHTML = `
        <img src="${place.firstimage || '/images/no-image.png'}" alt="썸네일">
        <div class="place-info">
          <h4>${place.title}</h4>
          <p>${place.addr1}</p>
        </div>
      `;
      card.addEventListener('click', () => {
        window.location.href = `/detail/${place.contentid}/${place.contenttypeid}`;
      });
      placeList.appendChild(card);

      // 🧭 마커 클릭 시 인포윈도우
	  kakao.maps.event.addListener(marker, 'click', () => {
	    map.setCenter(position);

	    const iwContent = `
	      <div class="info-window" style="width: 230px; font-family: sans-serif;">
	        <img src="${place.firstimage || '/images/no-image.png'}" alt="썸네일" style="width: 100%; height: 120px; object-fit: cover; cursor: pointer;" onclick="window.location.href='/detail/${place.contentid}/${place.contenttypeid}'"/>
	        <h4 style="margin: 8px 0 4px; font-size: 16px; cursor: pointer;" onclick="window.location.href='/detail/${place.contentid}/${place.contenttypeid}'">${place.title}</h4>
	        <p style="margin: 0; font-size: 14px; color: #555;">${place.addr1}</p>
	        <div style="margin-top: 8px; text-align: right;">
	          <span class="bookmark" onclick="toggleBookmark('${place.contentid}')" style="cursor: pointer;">🔖</span>
	        </div>
	      </div>
	    `;

	    const infowindow = new kakao.maps.InfoWindow({
	      content: iwContent,
	      removable: true
	    });

	    infowindow.open(map, marker);
	  });
    });

    clusterer.clear();
    clusterer.addMarkers(newMarkers);
  };


  const fetchPlaces = (x, y) => {
    fetch(`/api/mapData?mapX=${x}&mapY=${y}&radius=${selectedRadius}&arrange=S&contentTypeId=${selectedContentTypeId}`)
      .then((res) => res.json())
      .then((data) => {
        const places = data.list;
        if (places.length > 0) renderPlaces(places);
        else {
			clusterer.clear();
			placeList.innerHTML = "<p>주변 장소가 없습니다.</p>";
		}
      })
      .catch((err) => console.error(err));
  };


  document.getElementById("searchByMapBtn").addEventListener("click", () => {
	fetchPlaces(currentLon, currentLat);
  });

  // 페이지 최초 실행 시 현재 위치 기준
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((position) => {
      lat = position.coords.latitude;
      lon = position.coords.longitude;
      const loc = new kakao.maps.LatLng(lat, lon);
      map.setCenter(loc);
      fetchPlaces(lon, lat);
    });
  } else {
    fetchPlaces(lon, lat); // 기본 위치 (서울)
  }
  
  
  closeSidebarBtn.addEventListener('click', () => {
    sidebar.classList.add('hidden'); // 닫기 시 숨김
  });

  document.getElementById('searchByMapBtn').addEventListener('click', () => {
    sidebar.classList.remove('hidden'); // 검색 시 다시 보임
  });
  
  const radiusInput = document.getElementById("radiusInput");
  const radiusValue = document.getElementById("radiusValue");

  radiusInput.addEventListener("input", () => {
    radiusValue.textContent = radiusInput.value;
  });

  // 설정 버튼 누르면 패널 토글
  document.getElementById("mapSettingsBtn").addEventListener("click", () => {
    const panel = document.getElementById("mapSettingsPanel");
    panel.classList.toggle("hidden");
  });

  // 설정 적용 버튼
  document.getElementById("applySettingsBtn").addEventListener("click", () => {
    selectedRadius = parseInt(document.getElementById("radiusInput").value);
    selectedContentTypeId = document.getElementById("contentTypeSelect").value;

	fetchPlaces(currentLon, currentLat);
	document.getElementById("mapSettingsPanel").classList.add("hidden"); // 설정창 닫기
  });


});
