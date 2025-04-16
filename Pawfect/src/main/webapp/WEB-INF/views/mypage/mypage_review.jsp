<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="review-tab">
	<h3>내가 쓴 여행지 리뷰</h3>

	<c:if test="${empty myReviews}">
		<p>작성한 리뷰가 없습니다.</p>
	</c:if>

	<c:forEach var="review" items="${myReviews}">
		<div class="review-card">
			<img class="review-thumb" src="${review.imgpath}" alt="리뷰 대표 이미지" />
			<div class="review-info">
				<h4 class="review-title">${review.title}</h4>
				<div class="review-rating">
					<c:forEach begin="1" end="5" var="i">
						<span class="${i <= review.reviewRating ? 'star filled' : 'star'}">★</span>
					</c:forEach>
				</div>
				<p class="review-content">${fn:substring(review.reviewContent, 0, 80)}...</p>
				<p class="review-date">${review.reviewRegdate}</p>
				<a href="/travel/detail?contentId=${review.contentId}"
					class="review-link">→</a>
			</div>
		</div>
	</c:forEach>

	<c:if test="${totalPages > 1}">
		<div class="pagination">
			<c:forEach var="i" begin="1" end="${totalPages}">
				<button class="page-btn ${i == currentPage ? 'active' : ''}"
					data-page="${i}">${i}</button>
			</c:forEach>
		</div>
	</c:if>

</div>
