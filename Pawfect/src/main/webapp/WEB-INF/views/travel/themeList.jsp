<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<!-- 정렬 셀렉트박스 -->
<div class="sort-box">
  <select>
    <option selected value="O">가나다순</option>
    <option value="">리뷰순</option>
    <option value="">별점순</option>
    <option value="">북마크순</option>
  </select>
</div>

<!-- 여기서 JS가 카드들을 추가함 -->
<div class="theme-container"></div>

<div id="pagination" class="pagination"></div>

<!-- 북마크 배열 넘기기 -->
<c:if test="${not empty myBookmarks}">
  <script>
    const bookmarked = [<c:forEach var="id" items="${myBookmarks}" varStatus="status">
      ${id}<c:if test="${!status.last}">,</c:if>
    </c:forEach>];
  </script>
</c:if>
<c:if test="${empty myBookmarks}">
  <script>
    const bookmarked = [];
  </script>
</c:if>

<div id="confirmModal" class="modal">
  <div class="modal-content">
    <p id="confirmModalMessage"></p>
    <button id="btnConfirmYes">예</button>
    <button id="btnConfirmNo">아니요</button>
  </div>
</div>
<script src="/js/theme.js"></script>
</body>
</html>
