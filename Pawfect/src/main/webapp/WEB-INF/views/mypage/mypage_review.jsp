<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="review-tab">
	<h2 class="review-Btitle">내가 쓴 여행지 리뷰</h2>

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
				<p class="review-content">${fn:substring(review.reviewContent, 0, 130)}...</p>
				<p class="review-date">${fn:substring(review.reviewRegdate, 0, 10)}</p>
				<a href="/detail/${review.contentId}/${review.contentTypeId}" class="review-link">→</a>
			</div>
		</div>
	</c:forEach>

	<%-- 페이징 --%>
	<%
	int currentPageVal = (request.getAttribute("currentPage") != null) ? (Integer) request.getAttribute("currentPage") : 1;
	int totalPagesVal = (request.getAttribute("totalPages") != null) ? (Integer) request.getAttribute("totalPages") : 1;

	int pageBlockSize = 3;
	int startPage = ((currentPageVal - 1) / pageBlockSize) * pageBlockSize + 1;
	int endPage = Math.min(startPage + pageBlockSize - 1, totalPagesVal);
	%>

	<c:if test="${totalPages > 1}">
		<div class="pagination" style="text-align: center; margin-top: 30px;">
			<c:if test="${currentPage > 1}">
				<button class="page-btn" data-page="1">«</button>
				<button class="page-btn" data-page="${currentPage - 1}">‹</button>
			</c:if>

			<c:forEach begin="<%=startPage%>" end="<%=endPage%>" var="i">
				<c:set var="iVal" value="${i}" />
				<button class="page-btn ${iVal == currentPage ? 'active' : ''}"
					data-page="${iVal}">${iVal}</button>
			</c:forEach>

			<c:if test="${currentPage < totalPages}">
				<button class="page-btn" data-page="${currentPage + 1}">›</button>
				<button class="page-btn" data-page="${totalPages}">»</button>
			</c:if>
		</div>
	</c:if>

</div>
