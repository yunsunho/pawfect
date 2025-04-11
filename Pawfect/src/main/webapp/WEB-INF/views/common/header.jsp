<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>

<!-- 상단 메뉴 -->
<header class="header">
  <div class="logo">Paw<span>fect</span> Tour</div>
  <nav class="nav">
    <a href="/main" class="${currentPage eq 'main' ? 'active' : ''}">홈</a>
    <a href="/themeList" class="${currentPage eq 'theme' ? 'active' : ''}">테마</a>
    <a href="/areaList" class="${currentPage eq 'area' ? 'active' : ''}">지역</a>
    <a href="#" class="${currentPage eq 'community' ? 'active' : ''}">커뮤니티</a>
  </nav>
  <div class="icons">
     <a href="/map"> <span class="icon">📍</span> </a>
    <span class="icon notification">🔔</span>
    <a href="/mypage" class="icon user">👤</a> <!-- 👈 마이페이지 이동 -->
  </div>
</header>

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
