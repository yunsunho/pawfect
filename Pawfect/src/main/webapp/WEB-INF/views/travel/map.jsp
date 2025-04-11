<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Pawfect 지도</title>
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
  <div id="map"></div>

  <script>
  var mapContainer = document.getElementById('map');
  var mapOption = {
    center: new kakao.maps.LatLng(37.5665, 126.9780), // 기본: 서울 중심
    level: 5
  };

  var map = new kakao.maps.Map(mapContainer, mapOption);

  // 내 위치로 지도 중심 이동
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var lat = position.coords.latitude;
      var lon = position.coords.longitude;

      var locPosition = new kakao.maps.LatLng(lat, lon);
      map.setCenter(locPosition); // 👉 중심만 이동
    });
  } else {
    alert('브라우저가 위치 정보를 지원하지 않습니다.');
  }
</script>

</body>
</html>
