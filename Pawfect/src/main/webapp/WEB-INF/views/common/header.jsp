<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>

<!-- 상단 메뉴 -->
<header class="header">
  <div class="logo"><span>Paw</span>fect Tour</div>
  <nav class="nav">
    <a href="/main" class="${currentPage eq 'main' ? 'active' : ''}">홈</a>
    <a href="/themeList" class="${currentPage eq 'theme' ? 'active' : ''}">테마</a>
    <a href="/area" class="${currentPage eq 'area' ? 'active' : ''}">지역</a>
    <a href="#" class="${currentPage eq 'community' ? 'active' : ''}">커뮤니티</a>
  </nav>
  <div class="icons">
    <span class="icon">📍</span>
    <span class="icon notification">🔔</span>
    <a href="/mypage" class="icon user">👤</a> 
  </div>
</header>

<c:if test="${showSubmenu eq true}">
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