<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="review" items="${reviews}">
  <div class="review-card">
  
    <!-- 리뷰 상단: 프로필, 닉네임(ID 일부 마스킹), 별점, 날짜 -->
    <div class="review-header">
      <img class="profile" src="/profile-img/${review.userImage}" alt="프로필" />
      
      <span class="nickname">
        ${review.userNickname} (${fn:substring(review.userId, 0, 3)}***)
      </span>

      <span class="stars">
        <c:forEach begin="1" end="5" var="i">
          <c:choose>
            <c:when test="${i <= review.reviewRating}">★</c:when>
            <c:otherwise>☆</c:otherwise>
          </c:choose>
        </c:forEach>
      </span>

      <span class="date">
        <fmt:formatDate value="${review.reviewRegdate}" pattern="yyyy.MM.dd" />
      </span>
    </div>

    <!-- 이미지 목록 -->
    <c:if test="${not empty review.reviewImages}">
      <div class="review-images">
        <c:forEach var="img" items="${review.reviewImages}">
          <img class="thumbnail" src="${img}" alt="리뷰 이미지" />
        </c:forEach>
      </div>
    </c:if>

    <!-- 리뷰 본문 내용 -->
    <div class="review-text">
      ${review.reviewContent}
    </div>
  </div>
</c:forEach>
