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
<div id="confirmModal" class="modal">
    <div class="modal-content">
        <p>${modal_confirm_delete_post}</p>
        <button id="confirmSubmit">${str_confirm }</button>
        <button onclick="closeConfirmModal()">${str_cancel }</button>
    </div>
</div>
<body>
	<c:set var="currentPage">${currentPage}</c:set>
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
    	<div class="board-container">
    		<h1><span class="admin-name">${page_post}</span></h1>
			<main class="board-main">
				<form action="post" method="get" class="board-filter-form">
					<label for="startDate">${label_start_date}</label>
					<input type="date" name="startDate" value="${param.startDate}"/>
					
					<label for="endDate">${label_end_date}</label>
					<input type="date" name="endDate" value="${param.endDate}"/>
					<input id="searchbox" type="text" name="keyword" placeholder="${placeholder_searchbar}" value="${param.keyword}"/>
					<button type="submit">${btn_search}</button>
					
				</form>
				
				<table class="board-table">
					<tr>
						<th class="label" style="width:7%">${label_postId}</th>
						<th class="label" style="width:15%">${label_post_userId}</th>
						<th class="label">${label_postTitle}</th>
						<th class="label" style="width:16%">${label_postRegdate}</th>
						<th class="label" style="width:15%">${label_action}</th>
					</tr>
					
			
					<!-- 게시판에 글이 없는 경우 -->
					<c:if test="${count==0}">
						<tr>
							<td colspan="5" style="text-align: center;">
								${msg_no_post}
							</td>
						</tr>
					</c:if>
					
					<c:if test="${count!=0}">
						<c:forEach var="dto" items="${dtos}">
							<tr>
								<td>${dto.postId}</td>
								<td>${dto.userId}</td>
								<td>
									<a href="/board/content?num=${dto.postId}">
										${dto.postTitle}
									</a>
								</td>
								<td>${dto.formattedDate}</td>
								<td>
									<form action="deletePost" method="post">
							        	<input type="hidden" name="postId" value="${dto.postId}">
							        	<input type="hidden" name="pageNum" value="${pageNum}">
							        	<button type="button" class="submit-btn" onclick="openConfirmModal(this)">${btn_delete_post}</button>
							        </form>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
				
				<div class="pagination">
					<c:if test="${count gt 0}">
						<c:if test="${startPage gt pageBlock}">
							<a href="/admin/post?pageNum=${startPage - pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
								&laquo;
							</a>
						</c:if>
						<c:forEach var="i" begin="${startPage}" end="${endPage}">
							<c:if test="${i eq currentPage}">
							  <a class="hover current-page" href="#">${i}</a>
							</c:if>
							<c:if test="${i ne currentPage}">
							  <a class="hover" href="/admin/post?pageNum=${i}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
							    ${i}
							  </a>
							</c:if>
						</c:forEach>
						<c:if test="${pageCount gt endPage}">
							<a  href="/admin/post?pageNum=${startPage + pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
								&raquo;
							</a>
						</c:if>
					</c:if>
				</div>
			</main>
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