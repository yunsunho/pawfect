<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentPage" value="main" />
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Pawfect Tour </title>
  <link rel="stylesheet" href="/css/common.css">
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <main class="main">
  <section class="hero">
    <div class="tagline">Pet-Friendly Travel Website</div>
    <h1>
      반려동물과 함께하는 특별한 여행<br>
      <span class="highlight">새로운 장소를 쉽고 빠르게!</span>
    </h1>
    <div class="search">
      <input type="text" placeholder="어디로, 어떤 여행을 떠날 예정인가요?">
      <button>
      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-search-icon lucide-search"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
      </button>
    </div>
  </section>
</body>
</html>
