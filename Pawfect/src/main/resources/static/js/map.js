let map;
let bookmarkMarkers = [];
let placeMarkers = []; 
let clusterer=[];
let activeInfoWindow = null;

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
  map = new kakao.maps.Map(mapContainer, mapOption);
  clusterer = new kakao.maps.MarkerClusterer({
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
	// 북마크 마커 제거
	  bookmarkMarkers.forEach(marker => marker.setMap(null));
	  bookmarkMarkers = [];

	  // 기존 마커 제거
	  placeMarkers.forEach(marker => marker.setMap(null));
	  placeMarkers = [];
	
    placeList.innerHTML = "";
    let newMarkers = [];

    places.forEach(place => {
      // 마커 아이콘 경로 설정
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

      // 리스트 카드
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

      // 마커 클릭 시 인포윈도우
	  kakao.maps.event.addListener(marker, 'click', () => {
		if (activeInfoWindow) activeInfoWindow.close();
	    map.setCenter(position);

	    const iwContent = `
	      <div class="info-window" style="width: 230px; font-family: sans-serif;">
	        <img src="${place.firstimage || '/images/no-image.png'}" alt="썸네일" style="width: 100%; height: 120px; object-fit: cover; cursor: pointer;" onclick="window.location.href='/detail/${place.contentid}/${place.contenttypeid}'"/>
	        <h4 style="margin: 8px 0 4px; font-size: 16px; cursor: pointer;" onclick="window.location.href='/detail/${place.contentid}/${place.contenttypeid}'">${place.title}</h4>
	        <p style="margin: 0; font-size: 14px; color: #555;">${place.addr1}</p>
	        <div style="margin-top: 8px; text-align: right;">
	          <span class="bookmark" onclick="toggleBookmark('${place.contentid}')" style="cursor: pointer;"></span>
	        </div>
	      </div>
	    `;

	    const infowindow = new kakao.maps.InfoWindow({
	      content: iwContent,
	      removable: true,
		  zIndex: 100	
	    });

	    infowindow.open(map, marker);
		activeInfoWindow = infowindow;
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

	    if (places && places.length > 0) {
	      renderPlaces(places);
	    } else {
	      clusterer.clear();
	      placeList.innerHTML = "<p>주변 장소가 없습니다.</p>";
	    }
	  })
      .catch((err) => console.error(err));
  };


  document.getElementById("searchByMapBtn").addEventListener("click", () => {
	if (activeInfoWindow) {
	    activeInfoWindow.close();
	    activeInfoWindow = null;
	  }
	  
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
	  radiusValue.textContent = radiusInput.value / 1000; // 초기 표시값 설정
   });

  // 설정 버튼 누르면 패널 토글
  document.getElementById("mapSettingsBtn").addEventListener("click", () => {
	if (activeInfoWindow) {
		    activeInfoWindow.close();
		    activeInfoWindow = null;
		  }
		  
    const panel = document.getElementById("mapSettingsPanel");
    panel.classList.toggle("hidden");
  });

  // 설정 적용 버튼
  document.getElementById("applySettingsBtn").addEventListener("click", () => {
	if (activeInfoWindow) {
		    activeInfoWindow.close();
		    activeInfoWindow = null;
		  }
		  
    selectedRadius = parseInt(document.getElementById("radiusInput").value);
    selectedContentTypeId = document.getElementById("contentTypeSelect").value;

	fetchPlaces(currentLon, currentLat);
	document.getElementById("mapSettingsPanel").classList.add("hidden"); // 설정창 닫기
  });

  document.getElementById("showBookmarkBtn").addEventListener("click", () => {
    fetch("/user/bookmarks", {
      method: "GET",
      credentials: "include"
    })
      .then(res => {
        if (res.status === 401) {
          showConfirmModal("로그인이 필요한 서비스입니다.\n로그인 하시겠습니까?", () => {
            location.href = "/loginForm";
          });
          // 👇 이후 then으로 넘어가지 않게 처리
          throw new Error("UNAUTHORIZED");
        }

        if (!res.ok) throw new Error("FAILED");

        return res.json();
      })
      .then(data => {
        if (Array.isArray(data)) {
          renderUserBookmarks(data);
        } else {
          alert("불러올 북마크가 없습니다.");
        }
      })
      .catch(err => {
        if (err.message === "UNAUTHORIZED") return;
        alert("북마크 정보를 불러오지 못했습니다.");
        console.error(err);
      });
  });
});  

function renderUserBookmarks(bookmarks) {
	if (activeInfoWindow) {
	    activeInfoWindow.close();
	    activeInfoWindow = null;
	  }
	  
  clusterer.clear();

  placeMarkers.forEach(marker => marker.setMap(null));
  placeMarkers = [];

  bookmarkMarkers.forEach(marker => marker.setMap(null));
  bookmarkMarkers = [];

  // ✅ 사이드바 초기화
  const placeList = document.getElementById("place-list");
  placeList.innerHTML = "";

  bookmarks.forEach(bookmark => {
    const x = Number(bookmark.mapX);
    const y = Number(bookmark.mapY);

    if (isNaN(x) || isNaN(y) || x === 0 || y === 0) {
      console.warn("잘못된 좌표, 건너뜀:", bookmark);
      return;
    }

    try {
      const position = new kakao.maps.LatLng(y, x);

	  let iconPath = "";
	  switch (bookmark.contentTypeId?.toString()) {
	    case "12": iconPath = "/images/marker/marker-tour.png"; break;
	    case "14": iconPath = "/images/marker/marker-culture.png"; break;
	    case "15": iconPath = "/images/marker/marker-event.png"; break;
	    case "28": iconPath = "/images/marker/marker-leports.png"; break;
	    case "32": iconPath = "/images/marker/marker-hotel.png"; break;
	    case "38": iconPath = "/images/marker/marker-shopping.png"; break;
	    case "39": iconPath = "/images/marker/marker-food.png"; break;
	    default: iconPath = "/images/marker/marker-default.png"; break;
	  }

	  const markerImage = new kakao.maps.MarkerImage(
	    iconPath,
        new kakao.maps.Size(36, 36)
      );

      const marker = new kakao.maps.Marker({
        position,
        image: markerImage,
        zIndex: 5 
      });

      kakao.maps.event.addListener(marker, 'click', () => {
		
		if (activeInfoWindow) {
			    activeInfoWindow.close();
			    activeInfoWindow = null;
			  }
		
        const iwContent = `
		<div class="info-window" style="width: 230px; font-family: sans-serif;">
	        <img src="${bookmark.firstimage || '/images/no-image.png'}" alt="썸네일" style="width: 100%; height: 120px; object-fit: cover; cursor: pointer;" onclick="window.location.href='/detail/${bookmark.contentId}/${bookmark.contentTypeId}'"/>
	        <h4 style="margin: 8px 0 4px; font-size: 16px; cursor: pointer;" onclick="window.location.href='/detail/${bookmark.contentId}/${bookmark.contentTypeId}'">${bookmark.title}</h4>
	        <p style="margin: 0; font-size: 14px; color: #555;">${bookmark.addr1}</p>
	      </div>
        `;
		
        const infowindow = new kakao.maps.InfoWindow({
          content: iwContent,
          removable: true,
		  zIndex: 100, 
        });
		
        infowindow.open(map, marker);
		activeInfoWindow = infowindow;
      });

      marker.setMap(map);
      bookmarkMarkers.push(marker);

      // ✅ 사이드바에 카드 추가
      const card = document.createElement('div');
      card.className = 'place-card';
      card.setAttribute('data-contentid', bookmark.contentId);
      card.setAttribute('data-contenttypeid', bookmark.contentTypeId);
      card.innerHTML = `
        <img src="${bookmark.firstimage || '/images/no-image.png'}" alt="썸네일">
        <div class="place-info">
          <h4>${bookmark.title}</h4>
          <p>${bookmark.addr1}</p>
        </div>
      `;
      card.addEventListener('click', () => {
        window.location.href = `/detail/${bookmark.contentId}/${bookmark.contentTypeId}`;
      });
      placeList.appendChild(card);

    } catch (e) {
      console.error("마커 생성 중 오류 발생:", e, bookmark);
    }
  });
}




