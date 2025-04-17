<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>

<!-- 상단 메뉴 -->
<header class="header">
  <link rel="stylesheet" href="/css/notification.css">
  <link rel="icon" type="image/png" href="/my-favicon/favicon-96x96.png" sizes="96x96" />
<link rel="icon" type="image/svg+xml" href="/my-favicon/favicon.svg" />
<link rel="shortcut icon" href="/my-favicon/favicon.ico" />
<link rel="apple-touch-icon" sizes="180x180" href="/my-favicon/apple-touch-icon.png" />
<link rel="manifest" href="/my-favicon/site.webmanifest" />
  

  <div class="logo"><span>Paw</span>fect Tour</div>
  <nav class="nav">
    <a href="/main" class="${currentPage eq 'main' ? 'active' : ''}">홈</a>
    <a href="/themeList" class="${currentPage eq 'theme' ? 'active' : ''}">테마</a>
    <a href="/areaList" class="${currentPage eq 'area' ? 'active' : ''}">지역</a>
    <a href="/board" class="${menuPage eq 'community' ? 'active' : ''}">커뮤니티</a>
  </nav>
  <div class="icons">
     <a href="/map"><span class="icon">
	<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-map-pinned-icon lucide-map-pinned"><path d="M18 8c0 3.613-3.869 7.429-5.393 8.795a1 1 0 0 1-1.214 0C9.87 15.429 6 11.613 6 8a6 6 0 0 1 12 0"/><circle cx="12" cy="8" r="2"/><path d="M8.714 14h-3.71a1 1 0 0 0-.948.683l-2.004 6A1 1 0 0 0 3 22h18a1 1 0 0 0 .948-1.316l-2-6a1 1 0 0 0-.949-.684h-3.712"/></svg></span></a>
    
    <span class="icon notification" id="notiIcon">
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-bell-icon lucide-bell"><path d="M10.268 21a2 2 0 0 0 3.464 0"/><path d="M3.262 15.326A1 1 0 0 0 4 17h16a1 1 0 0 0 .74-1.673C19.41 13.956 18 12.499 18 8A6 6 0 0 0 6 8c0 4.499-1.411 5.956-2.738 7.326"/></svg>
    </span>
    
    <a href="/mypage" class="icon user">
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-circle-user-round-icon lucide-circle-user-round"><path d="M18 20a6 6 0 0 0-12 0"/><circle cx="12" cy="10" r="4"/><circle cx="12" cy="12" r="10"/></svg>
    </a> 
  </div>
  
</header>

<jsp:include page="/WEB-INF/views/user/notification.jsp"/>
<script src="/js/notification.js"></script>

<c:if test="${currentPage eq 'theme'}">
  <div class="theme-submenu">
    <a class="theme-tab active">관광지</a>
    <a class="theme-tab">문화시설</a>
    <a class="theme-tab">행사/공연/축제</a>
    <a class="theme-tab">레포츠</a>
    <a class="theme-tab">숙박</a>
    <a class="theme-tab">쇼핑</a>
    <a class="theme-tab">음식점</a>
  </div>
</c:if>

<c:if test="${currentPage eq 'area'}">
  <!-- 시도 메뉴 -->
  <div class="area-submenu">
    <a class="area-tab active" data-area="">전국</a>
	<a class="area-tab" data-area="1">서울</a>
	<a class="area-tab" data-area="2">인천</a>
	<a class="area-tab" data-area="3">대전</a>
	<a class="area-tab" data-area="4">대구</a>
	<a class="area-tab" data-area="5">광주</a>
	<a class="area-tab" data-area="6">부산</a>
	<a class="area-tab" data-area="7">울산</a>
	<a class="area-tab" data-area="8">세종</a>
	<a class="area-tab" data-area="31">경기</a>
	<a class="area-tab" data-area="32">강원</a>
	<a class="area-tab" data-area="33">충북</a>
	<a class="area-tab" data-area="34">충남</a>
	<a class="area-tab" data-area="35">경북</a>
	<a class="area-tab" data-area="36">경남</a>
	<a class="area-tab" data-area="37">전북</a>
	<a class="area-tab" data-area="38">전남</a>
	<a class="area-tab" data-area="39">제주</a>
  </div>

  <!-- 시군구 메뉴 (동적으로 채워짐) -->
  <div class="sigungu-submenu" style="display: none;">
    <!-- 여기에 JS로 동적으로 시군구 목록을 렌더링 -->
  </div>
</c:if>
