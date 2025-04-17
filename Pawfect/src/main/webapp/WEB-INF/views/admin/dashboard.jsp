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
        	<li><a href="/main">
        		<i id="home-symbol" class="fa-solid fa-house"></i>
				&nbsp;${sidebar_home}
        	</a></li>
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
        </ul>
        <div id="adminLogoutBtn">
	        <i id="logout-symbol" class="fa-solid fa-right-from-bracket"></i>
			&nbsp;
			<span id="logout-text">${sidebar_logout}</span>
		</div>
		
    </div>
    
    <div class="main-content" id="main-content">
        <h1>
        	<span class="welcome-text">${str_welcome},&nbsp;${str_admin}&nbsp;</span>
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
			<div class="stat-card" style="width: 400px; height: 250px;">
				<h3 class="chart-title">${header_user_growth }</h3>
				<canvas id="userChart" width="600" height="300"></canvas>
			</div>
			<!-- 
			<div class="stat-card" style="width: 400px; height: 250px;">
				<h3 class="chart-title">콘텐츠 수</h3>
				<canvas id="postReviewChart" width="600" height="300"></canvas>
			</div>
			-->
		</div>
	</div>
    
    
	<div id="confirmLogoutModal" class="modal">
		<div class="modal-content">
	    	<p id="confirmLogoutModalMessage">${modal_confirm_logout}</p>
		    <div class="modal-buttons">
		      <button id="btnLogoutConfirmYes">${str_confirm}</button>
		      <button id="btnLogoutConfirmNo">${str_cancel}</button>
		    </div>
		</div>
	</div>
</body>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const labels = [
        <c:forEach var="entry" items="${userRegistrationData}" varStatus="status">
            "${entry.key}"<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];

    const data = [
        <c:forEach var="entry" items="${userRegistrationData}" varStatus="status">
            ${entry.value}<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];

    const ctx = document.getElementById('userChart').getContext('2d');
    const userChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
            	label: '신규 가입자 수',
                data: data,
                borderColor: 'rgba(59, 143, 243, 1)',
                backgroundColor: 'rgba(59, 143, 243, 0.1)',
                tension: 0.3,
                fill: true
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: { title: { display: true, text: '날짜' }},
                y: { title: { display: true, text: '가입자 수' }, beginAtZero: true }
            },
            plugins: {
            	legend: {
                    display: false
                }
            }
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
	const ctx = document.getElementById('postReviewChart').getContext('2d');
	const postReviewChart = new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: labels,
	        datasets: [
	            {
	                label: 'Posts',
	                data: postData,
	                borderColor: 'rgba(54, 162, 235, 1)',
	                backgroundColor: 'rgba(54, 162, 235, 0.1)',
	                tension: 0.3
	            },
	            {
	                label: 'Reviews',
	                data: reviewData,
	                borderColor: 'rgba(255, 99, 132, 1)',
	                backgroundColor: 'rgba(255, 99, 132, 0.1)',
	                tension: 0.3
	            }
	        ]
	    },
	    options: {
	        responsive: true,
	        plugins: {
	            title: {
	                display: true,
	                text: 'Posts and Reviews Over Time',
	                font: {
	                    size: 18
	                }
	            },
	            legend: {
	                display: true, // Or false if you want to hide
	                position: 'bottom'
	            },
	            tooltip: {
	                enabled: true // Optional: disable if needed
	            }
	        },
	        scales: {
	            x: {
	                title: {
	                    display: true,
	                    text: 'Date'
	                }
	            },
	            y: {
	                beginAtZero: true,
	                title: {
	                    display: true,
	                    text: 'Count'
	                }
	            }
	        }
	    }
	});
</script>
