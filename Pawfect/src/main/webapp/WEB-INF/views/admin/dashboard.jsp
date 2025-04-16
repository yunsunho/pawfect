<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="setting.jsp" %>

<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/css/common.css">
	<link rel="stylesheet" type="text/css" href="/css/admin_dashboard.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
	<script src="/js/script_admin.js"></script>
</head>
<body>
    <div class="sidebar">
        <h2>
        	<a href="/admin" style="color: white;">
        		&nbsp;${sidebar_manage_site}
        	</a>
        </h2>
        <ul>
        	<li><a href="/admin/user">
        		<i class="fa-solid fa-user"></i>
        		&nbsp;${sidebar_manage_users }
        	</a></li>
            <li><a href="/admin/post">
            	<i class="fa-solid fa-list"></i>
            	&nbsp;${sidebar_manage_posts }
            </a></li>
            <li><a href="/admin/comment">
            	<i class="fa-solid fa-comments"></i>
            	&nbsp;${sidebar_manage_comments }
            </a></li>
            <li><a href="/admin/review">
            	<i class="fa-solid fa-database"></i>
            	&nbsp;${sidebar_manage_reviews }
            </a></li>
            <li><a href="/admin/inquiry">
            	<i class="fa-solid fa-list-check"></i>
            	&nbsp;${sidebar_manage_qna }
            </a></li>
            <li id="adminLogoutBtn">
	<i class="fa-solid fa-right-from-bracket"></i>
	&nbsp;<span style="cursor: pointer;">로그아웃</span>
</li>
        </ul>
    </div>
    
    <div class="main-content" id="main-content">
        <h1>
        	<span class="welcome-text">${str_welcome},&nbsp;${str_admin}&nbsp;</span>
        	<span class="admin-name">${adminName}님</span>
        </h1>
        <h2>${header_user_stats}</h2>
        <div class="dashboard-stats">
		    <div class="stat-card active">
		        <h3>${str_active_user}</h3>
		        <p id="activeCount">
		        	<i class="fa-solid fa-arrow-up" style="color:#3B8FF3;"></i>
		        	${activeUserCount}
		        </p>
		    </div>
		    <div class="stat-card withdrawn">
		        <h3>${str_withdrawn_user}</h3>
		        <p id="withdrawnCount">
		        	<i class="fa-solid fa-arrow-down" style="color: #e32400;"></i>
		        	${withdrawnUserCount}
		        </p>
		    </div>
		    <div class="stat-card banned">
		        <h3>${str_banned_user}</h3>
		        <p id="bannedCount">${bannedUserCount}</p>
		    </div>
		</div>
		<br>
		<h2>${header_content_stats}</h2>
		<div class="dashboard-stats">
			<div class="stat-card">
		        <h3>${str_review_count}</h3>
		        <p id="reviewCount">${totalReviewCount}</p>
		    </div>
		    <div class="stat-card">
		        <h3>${str_post_count }</h3>
		        <p id="postCount">${totalPostCount}</p>
		    </div>
		    <div class="stat-card">
		        <h3>${str_comment_count}</h3>
		        <p id="commentCount">${totalCommentCount}</p>
		    </div>
		</div>
    </div>
<div id="confirmModal" class="modal">
  <div class="modal-content">
    <p id="confirmModalMessage">정말 로그아웃하시겠습니까?</p>
    <div class="modal-buttons">
      <button id="btnConfirmYes">확인</button>
      <button id="btnConfirmNo">취소</button>
    </div>
  </div>
</div>
</body>
