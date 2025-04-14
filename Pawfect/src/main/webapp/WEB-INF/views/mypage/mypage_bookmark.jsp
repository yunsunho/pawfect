<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="bookmark-tab">
	<h2 class="bookmark-title">북마크한 여행지</h2>

	<c:choose>
		<c:when test="${empty bookmarks}">
			<p style="text-align: center; margin-top: 30px;">북마크한 여행지가 없습니다.</p>
		</c:when>
		<c:otherwise>
			<div class="bookmark-list">
				<c:forEach var="bookmark" items="${bookmarks}">
					<div class="bookmark-card">
						<a href="/detail/${bookmark.contentId}/${bookmark.contentTypeId}"
							class="bookmark-link"> <c:choose>
								<c:when test="${not empty bookmark.firstimage}">
									<img src="${bookmark.firstimage}" alt="이미지" />
								</c:when>
								<c:otherwise>
									<img src="/images/no-image.png" alt="이미지 없음" />
								</c:otherwise>
							</c:choose>
						</a>
						<div class="bookmark-info">
							<h3>${bookmark.title}</h3>
							<p>${bookmark.addr1}</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
</div>
