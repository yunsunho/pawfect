<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>${detail.title} - 상세정보</title>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/detail.css">
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=53058506472e68663c191f4ba75fc7b0"></script>
</head>
<body>

<div class="detail-wrapper">

  <!-- 🏷️ 제목 + 주소 -->
  <div class="place-header">
    <h1><c:choose>
	  <c:when test="${not empty detail.title}">${detail.title}</c:when>
	  <c:otherwise>제목 없음</c:otherwise>
	</c:choose></h1>

	<p><c:choose>
	  <c:when test="${not empty detail.addr1}">${detail.addr1}</c:when>
	  <c:otherwise>주소 없음</c:otherwise>
	</c:choose></p>

    <hr class="divider">
  </div>

  <!-- 📸 이미지 슬라이드 -->
  <div class="slider">
    <c:forEach var="img" items="${images}">
      <img src="${img}" alt="슬라이드 이미지">
    </c:forEach>
  </div>

  <!-- 📝 소개글 + 지도 -->
  
   <div class="overview">
     <h3>상세정보</h3>
     <p>${detail.overview}</p>
   </div>
   <div class="map-container">
     <div id="map" style="width:100%; height:300px;"></div>
   </div>

  <!-- 📋 이용 정보 -->
  <div class="extra-info">
    <h3>이용 안내</h3>
    <ul>
      <c:if test="${not empty detail.tel}">
        <li><strong>문의처:</strong> ${detail.tel}</li>
      </c:if>
      <c:if test="${not empty pet.chkpetfacility}">
        <li><strong>반려동물 시설:</strong> ${pet.chkpetfacility}</li>
      </c:if>
      <c:if test="${not empty pet.chkpetroom}">
        <li><strong>객실 동반:</strong> ${pet.chkpetroom}</li>
      </c:if>
      <c:if test="${not empty pet.chkpetrestaurant}">
        <li><strong>식당 동반:</strong> ${pet.chkpetrestaurant}</li>
      </c:if>
      <c:if test="${not empty pet.petnotic}">
        <li><strong>유의 사항:</strong> ${pet.petnotic}</li>
      </c:if>
      <c:if test="${not empty pet.petetc}">
        <li><strong>기타 안내:</strong> ${pet.petetc}</li>
      </c:if>
    </ul>
  </div>

  <!-- 💬 리뷰 영역 -->
  <div class="review-section">
    <h3>리뷰</h3>
    <textarea placeholder="로그인 후 작성 가능합니다." disabled></textarea>
    <button disabled>등록</button>
  </div>

</div>
<script>
  window.onload = function() {
    var mapX = parseFloat('${detail.mapx}');
    var mapY = parseFloat('${detail.mapy}');

    var mapContainer = document.getElementById('map');
    var mapOption = {
      center: new kakao.maps.LatLng(mapY, mapX),
      level: 3
    };

    var map = new kakao.maps.Map(mapContainer, mapOption);

    var marker = new kakao.maps.Marker({
      position: map.getCenter()
    });
    marker.setMap(map);
  };
  
</script>
</body>
</html>
