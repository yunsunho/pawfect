<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>Pawfect 지도</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=53058506472e68663c191f4ba75fc7b0"></script>
  <style>
    html, body {
      margin: 0;
      padding: 0;
      height: 100%;
    }
    #map {
      width: 100%;
      height: 100%;
    }    
  </style>
  <link rel="stylesheet" href="/css/common.css">	
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<button id="searchByMapBtn" class="map-button">현재 지도에서 검색</button>

<div id="map"></div>

<style>
  /* 버튼 스타일 */
  #searchByMapBtn {
    position: absolute;
    top: 100px; /* 화면 상단에서 20px */
    left: 50%; /* 화면 중앙 */
    transform: translateX(-50%); /* 정확히 중앙으로 맞추기 */
    background-color: #f4f4f4;
    padding: 10px 20px;
    border-radius: 10px;
    border: none;
    font-size: 16px;
    cursor: pointer;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
    z-index: 10; /* 지도를 가리지 않도록 버튼의 레이어 우선 순위 설정 */
  }

  /* 지도 스타일 */
  #map {
    width: 100%;
    height: 100%;
  }
</style>


<script>
document.addEventListener("DOMContentLoaded", function () {
    // 지도 기본 설정
    var mapContainer = document.getElementById('map');
    var lat = 37.5665; // 기본: 서울 중심
    var lon = 126.9780;
    var mapOption = {
        center: new kakao.maps.LatLng(lat, lon),
        level: 7
    };

    var map = new kakao.maps.Map(mapContainer, mapOption);
    var currentLat = lat;  // 현재 지도 중심 위도
    var currentLon = lon;  // 현재 지도 중심 경도
    var markers = []; // 마커들을 담을 배열

    // 지도 중심이 이동할 때마다 현재 좌표값 업데이트
    kakao.maps.event.addListener(map, 'center_changed', function () {
        var center = map.getCenter(); // 지도 중심 좌표 가져오기
        currentLat = center.getLat(); // 위도
        currentLon = center.getLng(); // 경도
    });

    // "현재 위치로 검색" 버튼 클릭 시
    document.getElementById('searchByMapBtn').addEventListener('click', function () {
        console.log("현재 지도 중심 좌표: ", currentLat, currentLon);  // 확인용

        // 이전 마커들 모두 제거
        markers.forEach(marker => {
            marker.setMap(null);
        });
        markers = []; // 배열 비우기

        // 지도 중심을 기준으로 API 호출
        fetch('/api/mapData?mapX=' + currentLon + '&mapY=' + currentLat + '&radius=5000&arrange=S&contentTypeId=')
            .then(response => response.json())
            .then(data => {
                console.log(data);  // 데이터 확인용

                const places = data.list;  // 리스트로 응답 데이터 사용
                if (places && places.length > 0) {
                    places.forEach(place => {
                        const position = new kakao.maps.LatLng(place.mapy, place.mapx);
                        const marker = new kakao.maps.Marker({
                            position: position,
                        });
                        marker.setMap(map); // 지도에 마커 표시
                        markers.push(marker); // 마커 배열에 추가

                        // 마커 클릭 이벤트
                        kakao.maps.event.addListener(marker, 'click', function () {
                            alert(`마커 클릭: ${place.title}\n주소: ${place.addr1}`);
                        });
                    });
                } else {
                    console.log("No places found.");
                }
            })
            .catch(error => console.error('Error fetching data:', error));
    });

    // 페이지 로드 시 바로 현재 위치 기준으로 마커를 표시
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            lat = position.coords.latitude;
            lon = position.coords.longitude;

            var locPosition = new kakao.maps.LatLng(lat, lon);
            map.setCenter(locPosition); // 👉 중심만 이동

            // 지도 API에서 데이터 가져오기
            fetch('/api/mapData?mapX=' + lon + '&mapY=' + lat + '&radius=5000&arrange=S&contentTypeId=')
                .then(response => response.json())
                .then(data => {
                    console.log(data);  // 데이터 확인용

                    const places = data.list;  // 리스트로 응답 데이터 사용
                    if (places && places.length > 0) {
                        places.forEach(place => {
                            const position = new kakao.maps.LatLng(place.mapy, place.mapx);
                            const marker = new kakao.maps.Marker({
                                position: position,
                            });
                            marker.setMap(map); // 지도에 마커 표시
                            markers.push(marker); // 마커 배열에 추가

                            // 마커 클릭 이벤트
                            kakao.maps.event.addListener(marker, 'click', function () {
                                alert(`마커 클릭: ${place.title}\n주소: ${place.addr1}`);
                            });
                        });
                    } else {
                        console.log("No places found.");
                    }
                })
                .catch(error => console.error('Error fetching data:', error));
        });
    } else {
        alert('브라우저가 위치 정보를 지원하지 않습니다.');
    }
});
</script>



</body>
</html>
