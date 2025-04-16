<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="review" items="${reviews}">
  <div class="review-card">
    
    <!-- 상단: 프로필 + 닉네임 + 별점 + 날짜 -->
    <div class="review-header">
      <img class="profile-img" src="${review.userImage}" alt="프로필" />
      <div class="review-user-info">
        <div class="nickname">
          ${review.userNickname} (${fn:substring(review.userId, 0, 3)}***)
        </div>
        <div class="stars">
          <c:forEach begin="1" end="5" var="i">
            <span class="${i <= review.reviewRating ? 'star filled' : 'star'}">★</span>
          </c:forEach>
        </div>
        <div class="review-date">
          <fmt:formatDate value="${review.reviewRegdate}" pattern="yyyy.MM.dd" />
        </div>
      </div>
    </div>

    <!-- 중단: 이미지 썸네일 목록 -->
    <c:if test="${not empty review.reviewImages}">
      <div class="review-images">
	    <c:forEach var="img" items="${review.reviewImages}">
	      <img class="thumbnail" src="${img}" alt="리뷰 이미지" />
	    </c:forEach>
	  </div>
    </c:if>

    <!-- 하단: 텍스트 리뷰 내용 -->
    <div class="review-content">
      ${review.reviewContent}
    </div>
    
  </div>
</c:forEach>

