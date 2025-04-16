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
        <h2></h2>
        <ul>
        	<li>
        		<i class="fa-solid fa-chart-simple"></i>
        		&nbsp;<a href="/admin">${sidebar_manage_site}</a>
        	</li>
        	<li>
        		<i class="fa-solid fa-user"></i>
        		&nbsp;<a href="/admin/user">${sidebar_manage_users }</a>
        	</li>
            <li>
            	<i class="fa-solid fa-list"></i>
            	&nbsp;<a href="/admin/post">${sidebar_manage_posts }</a>
            </li>
            <li>
            	<i class="fa-solid fa-comments"></i>
            	&nbsp;<a href="/admin/comment">${sidebar_manage_comments }</a>
            </li>
            <li>
            	<i class="fa-solid fa-database"></i>
            	&nbsp;<a href="/admin/review">${sidebar_manage_reviews }</a>
            </li>
            <li>
            	<i class="fa-solid fa-list-check"></i>
            	&nbsp;<a href="/admin/inquiry">${sidebar_manage_qna }</a>
            </li>
        </ul>
    </div>
    
    
    <div class="main-content" id="main-content">
    	<div class="board-container">
    		<h1><span class="admin-name">${page_comment}</span></h1>
			<main class="board-main">
				<form action="comment" method="get" class="board-filter-form">
					<label for="startDate">${label_start_date}</label>
					<input type="date" name="startDate" value="${param.startDate}"/>
					
					<label for="endDate">${label_end_date}</label>
					<input type="date" name="endDate" value="${param.endDate}"/>
					<input id="searchbox" type="text" name="keyword" placeholder="${placeholder_searchbar}" value="${param.keyword}"/>
					<button type="submit">${btn_search}</button>
					
				</form>
				
				<table class="board-table">
					<tr>
						<th class="label" style="width:7%">${label_commentId}</th>
						<th class="label" style="width:15%">${label_post_userId}</th>
						<th class="label">${label_comContent}</th>
						<th class="label" style="width:16%">${label_postRegdate}</th>
						<th class="label" style="width:15%">${label_action}</th>
					</tr>
					
			
					<!-- 게시판에 글이 없는 경우 -->
					<c:if test="${count==0}">
						<tr>
							<td colspan="5" style="text-align: center;">
								${msg_no_comment}
							</td>
						</tr>
					</c:if>
					
					<c:if test="${count!=0}">
						<c:forEach var="dto" items="${dtos}">
							<tr>
								<td>${dto.commentId}</td>
								<td>${dto.userId}</td>
								<td>${dto.comContent}</td>
								<td>${dto.formattedDate}</td>
								<td>
									<form action="deleteComment" method="post">
							        	<input type="hidden" name="commentId" value="${dto.commentId}">
							        	<input type="hidden" name="pageNum" value="${pageNum}">
							        	<button type="button" class="submit-btn" onclick="openConfirmModal(this)">${btn_delete_comment}</button>
							        </form>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
				
				<div class="pagination">
					<c:if test="${count gt 0}">
						<c:if test="${startPage gt pageBlock}">
							<a href="/admin/comment?pageNum=${startPage - pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
								&laquo;
							</a>
						</c:if>
						<c:forEach var="i" begin="${startPage}" end="${endPage}">
							<c:if test="${i eq currentPage}">
							  <a class="hover current-page" href="#">${i}</a>
							</c:if>
							<c:if test="${i ne currentPage}">
							  <a class="hover" href="/admin/comment?pageNum=${i}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
							    ${i}
							  </a>
							</c:if>
						</c:forEach>
						<c:if test="${pageCount gt endPage}">
							<a  href="/admin/comment?pageNum=${startPage + pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
								&raquo;
							</a>
						</c:if>
					</c:if>
				</div>
			</main>
		</div>
    </div>
</body>