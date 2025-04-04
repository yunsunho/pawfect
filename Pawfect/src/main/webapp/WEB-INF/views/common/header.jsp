<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<header class="header">
  <div class="logo">Paw<span>fect</span> Tour</div>
  <nav class="nav">
    <a href="/main" class="${currentPage eq 'main' ? 'active' : ''}">홈</a>
    <a href="/themeList" class="${currentPage eq 'theme' ? 'active' : ''}">테마</a>
    <a href="#" class="${currentPage eq 'area' ? 'active' : ''}">지역</a>
    <a href="#" class="${currentPage eq 'community' ? 'active' : ''}">커뮤니티</a>
  </nav>
  <div class="icons">
    <span class="icon">📍</span>
    <span class="icon notification">🔔</span>
    <span class="icon user">👤</span>
  </div>
</header>
