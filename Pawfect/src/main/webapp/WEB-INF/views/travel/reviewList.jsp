<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="review" items="${reviews}">
  <div class="review-card">
  
   <!-- 우측 상단 삭제 버튼 -->
  <button class="delete-btn" onclick="confirmDelete(${review.reviewId}, '${review.userId}')">삭제</button>
  
    
    <!-- 상단: 프로필 + 닉네임 + 별점 + 날짜 -->
    <div class="review-header">
      	<c:choose>
		  <c:when test="${empty review.userImage or review.userImage == '없음'}">
		    <img class="profile-img" src="/images/default_profile.jpg" alt="기본 이미지" />
		  </c:when>
		  <c:otherwise>
		    <img class="profile-img" src="${review.userImage}" alt="프로필" />
		  </c:otherwise>
		</c:choose>
      <div class="review-user-info">
        <div class="nickname">
          ${review.userNickname} (${fn:substring(review.userId, 0, 3)}***)
        </div>
        <div class="stars">
		  <c:forEach begin="1" end="5" var="i">
		    <span class="${i le review.reviewRating ? 'star filled' : 'star'}">★</span>
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




<div class="pagination-container">
  <c:if test="${currentPage > 1}">
    <button class="page-btn" onclick="goToPage(${currentPage - 1})">&lt;</button>
  </c:if>

  <c:forEach begin="1" end="${totalPages}" var="i">
    <button class="page-btn ${i == currentPage ? 'active' : ''}" onclick="goToPage(${i})">${i}</button>
  </c:forEach>

  <c:if test="${currentPage < totalPages}">
    <button class="page-btn" onclick="goToPage(${currentPage + 1})">&gt;</button>
  </c:if>
</div>

<script>
  const contentId = ${contentId};

  function goToPage(page) {
    fetch(`/travel/reviews/${contentId}?page=${page}`)
      .then(response => response.text())
      .then(html => {
        document.getElementById("review-list-container").innerHTML = html;
      })
      .catch(error => {
        console.error("리뷰 페이지 로딩 실패:", error);
      });
  }
</script>


