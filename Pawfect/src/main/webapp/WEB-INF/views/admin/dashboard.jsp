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
        <h2></h2>
        <ul>
        	<li>
        		<i class="fa-solid fa-chart-simple" style="color: #1E1E2C;"></i>
        		&nbsp;${sidebar_manage_site}
        	</li>
        	<li>
        		<i class="fa-solid fa-user" style="color: #1E1E2C;"></i>
        		&nbsp;<a href="#">${sidebar_manage_users }</a>
        	</li>
            <li>
            	<i class="fa-solid fa-list" style="color: #1E1E2C;"></i>
            	&nbsp;<a href="#">${sidebar_manage_posts }</a>
            </li>
            <li>
            	<i class="fa-solid fa-comments" style="color: #1E1E2C;"></i>
            	&nbsp;<a href="#">${sidebar_manage_comments }</a>
            </li>
            <li>
            	<i class="fa-solid fa-database" style="color: #1E1E2C;"></i>
            	&nbsp;<a href="#">${sidebar_manage_reviews }</a>
            </li>
            <li>
            	<i class="fa-solid fa-list-check" style="color: #1E1E2C;"></i>
            	&nbsp;<a href="/admin/inquiry">${sidebar_manage_qna }</a>
            </li>
        </ul>
    </div>
    
    

    <div class="main-content" id="main-content">
        <h1>
        	<span class="welcome-text">${str_welcome},</span>&nbsp;
        	<span class="admin-name">${adminName}님</span>
        </h1>
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

</body>
