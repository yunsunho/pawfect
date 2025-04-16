<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="currentPage" value="main" />
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Pawfect Tour</title>
<link rel="stylesheet" href="/css/common.css">
<link rel="stylesheet" href="/css/main.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>

	<main class="main">
		<section class="hero">
			<div class="tagline">Pet-Friendly Travel Website</div>
			<h1>
				반려동물과 함께하는 특별한 여행<br> <span class="highlight">새로운 장소를 쉽고
					빠르게!</span>
			</h1>
			<div class="search">
				<input type="text" placeholder="어디로, 어떤 여행을 떠날 예정인가요?">
				<button>
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
						viewBox="0 0 24 24" fill="none" stroke="currentColor"
						stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
						class="lucide lucide-search-icon lucide-search">
						<circle cx="11" cy="11" r="8" />
						<path d="m21 21-4.3-4.3" /></svg>
				</button>
			</div>
		</section>

		<!-- HOT 게시글 영역 -->
		<section class="hot-posts-section">
			<div class="hot-header-row">
				<h2 class="hot-title">COMMUNITY</h2>
				<span class="hot-subtitle">실시간 인기글</span>
			</div>

			<!-- 조회수 상위 -->
			<div class="hot-category">
				<h3>조회수 TOP  <i class="fa-solid fa-fire"></i></h3>
				<div class="hot-posts-scroll">
					<c:forEach var="post" items="${topViewedPosts}">
						<div class="hot-post-card">
							<p>${post.displayName}</p>
							<h4>${post.postTitle}</h4>
							<p class="post-preview">
							  <c:choose>
							    <c:when test="${fn:length(post.postContent) > 30}">
							      ${fn:substring(post.postContent, 0, 30)}...
							    </c:when>
							    <c:otherwise>
							      ${post.postContent}
							    </c:otherwise>
							  </c:choose>
							</p>
							<div class="post-stats">
  								<i class="fas fa-eye"></i> <span>${post.postViewCount}</span>
 							    <i class="fas fa-heart"></i> <span>${post.likeCount}</span>
  								<i class="far fa-comment"></i> <span>${post.commentCount}</span>
							</div><a href="/board/content?num=${post.postId}" class="view-btn">→</a>
						</div>
					</c:forEach>
				</div>
			</div>

			<!-- 좋아요 상위 -->
			<div class="hot-category">
				<h3>좋아요 TOP  <i class="fa-solid fa-star"></i></h3>
				<div class="hot-posts-scroll">
					<c:forEach var="post" items="${topLikedPosts}">
						<div class="hot-post-card">
							<p>${post.displayName}</p>
							<h4>${post.postTitle}</h4>
							<p class="post-preview">
							  <c:choose>
							    <c:when test="${fn:length(post.postContent) > 30}">
							      ${fn:substring(post.postContent, 0, 30)}...
							    </c:when>
							    <c:otherwise>
							      ${post.postContent}
							    </c:otherwise>
							  </c:choose>
							</p>
							<div class="post-stats">
  								<i class="fas fa-eye"></i> <span>${post.postViewCount}</span>
  								<i class="fas fa-heart"></i> <span>${post.likeCount}</span>
  								<i class="far fa-comment"></i> <span>${post.commentCount}</span>
							</div><a href="/board/content?num=${post.postId}" class="view-btn">→</a>
						</div>
					</c:forEach>
				</div>
			</div>

			<!-- 댓글 수 상위 -->
			<div class="hot-category">
				<h3>댓글 수 TOP  <i class="fa-solid fa-comments"></i></h3>
				<div class="hot-posts-scroll">
					<c:forEach var="post" items="${topCommentedPosts}">
						<div class="hot-post-card">
							<p>${post.displayName}</p>
							<h4>${post.postTitle}</h4>
							<p class="post-preview">
							  <c:choose>
							    <c:when test="${fn:length(post.postContent) > 30}">
							      ${fn:substring(post.postContent, 0, 30)}...
							    </c:when>
							    <c:otherwise>
							      ${post.postContent}
							    </c:otherwise>
							  </c:choose>
							</p>
							<div class="post-stats">
  								<i class="fas fa-eye"></i> <span>${post.postViewCount}</span>
  								<i class="fas fa-heart"></i> <span>${post.likeCount}</span>
  								<i class="far fa-comment"></i> <span>${post.commentCount}</span>
							</div><a href="/board/content?num=${post.postId}" class="view-btn">→</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</section>
</body>
</html>
