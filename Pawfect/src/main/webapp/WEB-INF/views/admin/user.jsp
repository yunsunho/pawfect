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
        <p>${modal_confirm_ban}</p>
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
    		<h1><span class="admin-name">${page_user}</span></h1>
			<main class="board-main">
				<form action="user" method="get" class="board-filter-form">
					<label for="startDate">${label_start_date}</label>
					<input type="date" name="startDate" value="${param.startDate}"/>
					
					<label for="endDate">${label_end_date}</label>
					<input type="date" name="endDate" value="${param.endDate}"/>
					<!-- ACTIVE', 'BANNED', 'WITHDRAWN -->
					<select name="sortBy">
						<option value="default" ${param.sortBy == 'default' ? 'selected' : ''}>${label_default_all}</option>
						<option value="active" ${param.sortBy == 'active' ? 'selected' : ''}>${str_active_user}</option>
						<option value="banned" ${param.sortBy == 'banned' ? 'selected' : ''}>${str_banned_user}</option>
						<option value="withdrawn" ${param.sortBy == 'withdrawn' ? 'selected' : ''}>${str_withdrawn_user}</option>
					</select>
					
					<input id="searchbox" type="text" name="keyword" placeholder="${placeholder_searchbar_id}" value="${param.keyword}"/>
					<button type="submit">${btn_search}</button>
					
				</form>
				
				<table class="board-table">
					<tr>
						<th class="label" style="width:7%">${label_userId}</th>
						<th class="label" style="width:7%">${label_userName}</th>
						<th class="label" style="width:15%">${label_userTel}</th>
						<th class="label" style="width:15%">${label_userNickname}</th>
						<th class="label" style="width:16%">${label_userRegdate}</th>
						<th class="label" style="width:15%">${label_email}</th>
						<th class="label" style="width:15%">${label_userStatus}</th>
						<th class="label">${label_action}</th>
					</tr>
					
			
					<!-- 게시판에 글이 없는 경우 -->
					<c:if test="${count==0}">
						<tr>
							<td colspan="8" style="text-align: center;">
								${msg_no_user}
							</td>
						</tr>
					</c:if>
					
					<c:if test="${count!=0}">
						<c:forEach var="dto" items="${dtos}">
							<tr>
								<td>${dto.userId}</td>
								<td>${dto.userName}</td>
								<td>${dto.userTel}</td>
								<td>${dto.userNickname}</td>
								<td>${dto.formattedDate}</td>
								<td>${dto.email}</td>
								<td>
									<c:if test="${dto.userStatus eq 'ACTIVE'}">
										<span class="tags" id="active-user">
											${str_active_user}
										</span>
									</c:if>
									<c:if test="${dto.userStatus eq 'BANNED'}">
										<span class="tags" id="banned-user">
											${str_banned_user}
										</span>
									</c:if>
									<c:if test="${dto.userStatus eq 'WITHDRAWN'}">
										<span class="tags" id="withdrawn-user">
											${str_withdrawn_user}
										</span>
									</c:if>
								</td>
								<td>
									<c:if test="${dto.userStatus eq 'ACTIVE' }">
										<form action="banUser" method="post">
								        	<input type="hidden" name="userId" value="${dto.userId}">
								        	<input type="hidden" name="pageNum" value="${pageNum}">
								        	<button type="button" class="submit-btn" onclick="openConfirmModal(this)">${btn_ban_user}</button>
								        </form>
							        </c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
				
				<div class="pagination">
					<c:if test="${count gt 0}">
						<c:if test="${startPage gt pageBlock}">
							<a href="/admin/user?pageNum=${startPage - pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}&sortBy=${param.sortBy}">
								&laquo;
							</a>
						</c:if>
						<c:forEach var="i" begin="${startPage}" end="${endPage}">
							<c:if test="${i eq currentPage}">
							  <a class="hover current-page" href="#">${i}</a>
							</c:if>
							<c:if test="${i ne currentPage}">
							  <a class="hover" href="/admin/user?pageNum=${i}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}&sortBy=${param.sortBy}">
							    ${i}
							  </a>
							</c:if>
						</c:forEach>
						<c:if test="${pageCount gt endPage}">
							<a  href="/admin/user?pageNum=${startPage + pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}&sortBy=${param.sortBy}">
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