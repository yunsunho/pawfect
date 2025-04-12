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
					<span class="date">${postDto.postRegdate}</span>
				</div>
			</div>
			
			<!-- content -->
			<hr class="divider">
			<div class="post-content">
				<p>${postDto.postContent}</p>
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