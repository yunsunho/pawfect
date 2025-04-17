<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>Pawfect 지도</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=53058506472e68663c191f4ba75fc7b0&libraries=clusterer"></script>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/map.css">
  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<!-- 현재 지도에서 검색 버튼 -->
<button id="searchByMapBtn">현재 지도에서 검색 <i class="fa-solid fa-magnifying-glass"></i></button>
<div class="map-top-buttons">
  <button id="showBookmarkBtn" title="내 북마크 보기">💖</button>
  <button id="mapSettingsBtn" class="map-settings-button"><i class="fa-solid fa-sliders"></i></button>
</div>
<!-- 지도 + 사이드바 전체 wrapper -->
<div class="map-wrapper">
  <div id="sidebar" class="visible">
    <div class="sidebar-header">
      <button id="closeSidebarBtn" class="close-btn"><i class="fa-solid fa-xmark"></i></button>
    </div>
    <div id="place-list"></div>
  </div>
  <div id="map"></div>
</div>
<div id="mapSettingsPanel" class="settings-panel hidden">
  <h4 style="text-align:center;">환경 설정</h4>
  <label>콘텐츠 타입:</label>
  <select id="contentTypeSelect">
    <option value="">전체</option>
    <option value="12">관광지</option>
    <option value="14">문화시설</option>
    <option value="15">행사/공연/축제</option>
    <option value="28">레포츠</option>
    <option value="32">숙박</option>
    <option value="38">쇼핑</option>
    <option value="39">음식점</option>
  </select>

  <label for="radiusInput">반경: <span id="radiusValue">5</span>km</label>
  <input type="range" id="radiusInput" min="0" max="20000" step="5000" value="5000">

  <button id="applySettingsBtn">적용</button>
</div>


<div id="confirmModal" class="modal">
  <div class="modal-content">
    <p id="confirmModalMessage"></p>
    <button id="btnConfirmYes">예</button>
    <button id="btnConfirmNo">아니요</button>
  </div>
</div>
<script src="/js/map.js"></script>
<script src="/js/modal.js"></script>
</body>
</html>
