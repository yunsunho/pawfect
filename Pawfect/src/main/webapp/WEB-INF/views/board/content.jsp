<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="setting.jsp" %>
<c:set var="currentPage" value="content" />

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/css/common.css">
	<link rel="stylesheet" type="text/css" href="/css/style_board.css">
	<link rel="stylesheet" type="text/css" href="/css/style_board_sidebar.css">
	<link rel="stylesheet" type="text/css" href="/css/style_board_content.css">
	<link rel="stylesheet" type="text/css" href="/css/style_board_modal.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
	<script type="text/javascript" src="/js/script_board.js"></script>
</head>

<body>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<div class="board-container">
		<aside class="sidebar">
			<%@ include file="/WEB-INF/views/board/sidebar.jsp" %>
		</aside>
		<div class="post-container">
			<!-- top meta info -->
			<hr class="divider">
			<div class="post-meta">
				<div class="icons-left">
					<i class="fas fa-eye"></i><span>${postDto.postViewCount}</span>
					<i class="fas fa-heart"></i><span>${postDto.likeCount}</span>
					<i class="far fa-comment"></i><span>${postDto.commentCount}</span>
				</div>
				<div class="icons-right">
					<!-- dropdown menu (modify / delete post) -->
					<c:if test="${isUserPost}">
						<div class="dropdown">
							<button id="menu-btn" class="dropbtn" type="button" onclick="dropdown()"><i class="fa-solid fa-ellipsis-vertical"></i></button>
							<div id="menu-view" class="dropdown-content">
								<a href="/board/modify?num=${postDto.postId}">${str_modify_post}</a>
								<a href="javascript:void(0)" onclick="confirmDelete(${postDto.postId})">${str_delete_post}</a>
							</div>
						</div>
					</c:if>
					
				</div>
			</div>
			<!-- title -->
			<div class="post-header">
				<h2>
					<span class="hashtag"> <!-- #말머리 -->
						<c:choose>
							<c:when test="${postDto.postType eq 1}">
								${tag_review}
							</c:when>
							<c:when test="${postDto.postType eq 2}">
								${tag_recommendation}
							</c:when>
							<c:when test="${postDto.postType eq 3}">
								${tag_info}
							</c:when>
							<c:when test="${postDto.postType eq 4}">
								${tag_other}
							</c:when>
							<c:when test="${postDto.postType eq 5}">
								${tag_qna}
							</c:when>
							<c:otherwise>
								${tag_default}
							</c:otherwise>
						</c:choose>
					</span>
					&nbsp;
					<strong>${postDto.postTitle}</strong>
				</h2>
				<div class="post-sub">
					<span class="author">${postDto.displayName}</span>
					<span class="date">${postDto.formattedDate}</span>
				</div>
			</div>
			
			<!-- content -->
			<hr id="divider-bottom">
			<div class="post-content">
				<p>${postDto.postContent}</p>
			</div>
			
			
			<!-- like post button -->
			<div class="like-form">
				<form action="like" method="post" name="like-form">
					<input type="hidden" name="num" value="${num}"> 
					<button id="like-btn" type="submit">
						<!-- if liked then solid, otherwise regular-->
						<c:if test="${userLiked eq 1}">
							<i class="fa-solid fa-heart" style="color: #ed719e;"></i>
						</c:if>
						<c:if test="${userLiked eq 0}">
							<i class="fa-regular fa-heart" style="color: #ed719e;"></i>
						</c:if>
						${btn_like}
					</button>
				</form>
			</div>
			<hr id="divider-bottom">
			
			<!-- comment section -->
			<div class="comment-section">
				<h3>${str_comment}&nbsp;(${postDto.commentCount})</h3>
				<form class="comment-form" action="comment" method="post">
					<input type="hidden" name="postId" value="${postDto.postId}">
					
					<textarea name="comContent" id="comment-textarea" placeholder="${placeholder_comment}" required></textarea>
					<div class="comment-form-buttons">
						<div class="button-inner-container">
							<button class="comment-submit-btn" type="submit">${btn_submit}</button>
							<button class="comment-reset-btn" type="reset">${btn_cancel}</button>
						</div>
					</div>
				</form>
				
				<!--  comment list -->
				<div class="comment-list">
					<c:forEach var="commentDto" items="${commentDtos}">
						<div class="comment" style="margin-left: ${commentDto.com_re_level * 20}px;">
							<c:if test="${empty commentDto.userImage}">
								<img src="/images/upload/profile/8221a67b-287b-4a08-bb38-5c3d5d214cfd_cat.jpeg" class="comment-avatar"/>
							</c:if>
							<c:if test="${not empty commentDto.userImage}">
								<img src="${commentDto.userImage}" class="comment-avatar">
							</c:if>
							
							<div class="comment-body">
								<div class="comment-header">
									<span class="comment-writer">${commentDto.displayName}</span>
									<span class="comment-time">${commentDto.formattedDate}</span>
								</div>
								<div class="comment-content">${commentDto.comContent}</div>
								<div class="comment-actions">
									<button class="reply-btn" data-comment-id="${commentDto.commentId}" onclick="toggleReplyBox(this)">
										${btn_reply}
									</button>
								</div>
								<div class="reply-form" id="reply-form-${commentDto.commentId}" style="display: none; margin-top: 10px;">
									<form method="post" action="/board/reply">
										<input type="hidden" name="postId" value="${commentDto.postId}">
										<input type="hidden" name="com_ref" value="${commentDto.com_ref}">
										<input type="hidden" name="parentCommentId" value="${commentDto.commentId}">
										<textarea name="comContent" placeholder="${placeholder_reply}" required style="width: 100%; height: 60%;"></textarea>
										<button type="submit" class="reply-submit-btn">${btn_submit}</button>
										<button type="button" class="reply-cancel-btn" onclick="cancelReplyBox(this)">${btn_cancel}</button>
									</form>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		
	</div>
	
	<!-- delete confirmation modal -->
	<div id="deleteModal" class="modal">
		<div class="modal-content">
			<p>${str_confirm_delete}</p>
			<div class="modal-buttons">
				<button id="confirmDeleteBtn">${btn_delete}</button>
				<button onclick="closeModal()">${btn_cancel}</button>
			</div>
		</div>
	</div>
</body>