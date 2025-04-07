<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>${detail.title} - 상세정보</title>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/detail.css">
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3051d3b5aa6c1ba7db848552c5e541e6"></script>
</head>
<body>

<div class="detail-wrapper">
  <!-- 📸 이미지 슬라이드 -->
  <div class="slider">
    <c:forEach var="img" items="${images}">
	  <img src="${img.originimgurl}" alt="슬라이드 이미지">
	</c:forEach>
  </div>

  <!-- 📝 상세 정보 -->
  <div class="info">
    <h2>${detail.title}</h2>
    <p>${detail.addr1}</p>
    <p>${detail.overview}</p>
  </div>

  <!-- 🗺 지도 -->
  <div id="map" style="width:100%; height:300px;"></div>
  <script>
	  var mapX = parseFloat('${detail.mapx}');
	  var mapY = parseFloat('${detail.mapy}');
	  var mapOption = {
	      center: new kakao.maps.LatLng(mapY, mapX),
	      level: 3
	  };
	  var map = new kakao.maps.Map(mapContainer, mapOption);
  </script>

  <!-- 💬 리뷰 영역 -->
  <div class="review-section">
    <h3>리뷰</h3>
    <textarea placeholder="로그인 후 작성 가능합니다." disabled></textarea>
    <button disabled>등록</button>
  </div>
</div>

</body>
</html>
