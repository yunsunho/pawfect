<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<c:set var="currentPage" value="theme" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Pawfect Tour - 리스트</title>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/theme.css">
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="sort-box">
  <select>
    <option selected value="O">가나다순</option>
    <option value="">리뷰순</option>
    <option value="">별점순</option>
    <option value="">북마크순</option>
  </select>
</div>

<div class="theme-container">
  <c:forEach var="theme" items="${themeList}">
    <div class="theme-card">
      <a href="/detail/${item.contentid}?contentTypeId=${item.contenttypeid}" class="theme-link">
        <img src="${theme.firstimage}" alt="이미지 없음">
        <div class="theme-info">
          <h3>${theme.title}</h3>
          <p>${theme.addr1}</p>
        </div>
      </a>
      <div class="bookmark">🔖</div>
    </div>
  </c:forEach>
</div>



<div id="pagination" class="pagination"></div>

<script src="/js/theme.js"></script>	
</body>
</html>
