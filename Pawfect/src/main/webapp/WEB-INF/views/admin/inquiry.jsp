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
	<c:set var="currentPage">${currentPage}</c:set>
	<div class="sidebar">
        <h2></h2>
        <ul>
        	<li>
        		<i class="fa-solid fa-chart-simple" style="color: #1E1E2C;"></i>
        		&nbsp;<a href="/admin">${sidebar_manage_site}</a>
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
    	<div class="board-container">
    		<h1><span class="admin-name">${page_inquiry}</span></h1>
			<main class="board-main">
				<form action="inquiry" method="get" class="board-filter-form">
					<label for="startDate">${label_start_date}</label>
					<input type="date" name="startDate" value="${param.startDate}"/>
					
					<label for="endDate">${label_end_date}</label>
					<input type="date" name="endDate" value="${param.endDate}"/>
					
					<select name="sortBy">
						<option value="default" ${param.sortBy == 'default' ? 'selected' : ''}>${label_default_all}</option>
						<option value="handled" ${param.sortBy == 'handled' ? 'selected' : ''}>${label_handled}</option>
						<option value="unhandled" ${param.sortBy == 'unhandled' ? 'selected' : ''}>${label_unhandled}</option>
					</select>
					
					<input id="searchbox" type="text" name="keyword" placeholder="${placeholder_searchbar}" value="${param.keyword}"/>
					<button type="submit">${btn_search}</button>
					
				</form>
				
				<table class="board-table">
					<tr>
						<th class="label" style="width:10%">${label_inquiryId}</th>
						<th class="label">${label_inquiryTitle}</th>
						<th class="label" style="width:15%">${label_userId}</th>
						<th class="label" style="width:15%">${label_inquiryRegdate}</th>
						<th class="label" style="width:7%">${label_inquiryStatus}</th>
						<th class="label" style="width:15%">${label_adminId}</th>
					</tr>
					
			
					<!-- 게시판에 글이 없는 경우 -->
					<c:if test="${count==0}">
						<tr>
							<td colspan="6" style="text-align: center;">
								${msg_no_inquiry}
							</td>
						</tr>
					</c:if>
					
					<c:if test="${count!=0}">
						<c:forEach var="dto" items="${dtos}">
							<tr>
								<td>${dto.inquiryId}
								</td>
								<td style="text-align:left">
									<a class="inquiry-title" href="/admin/inquiryRespond?num=${dto.inquiryId}">
										${dto.inquiryTitle}
									</a>
								</td>
								<td>${dto.userId}</td>
								<td>${dto.formattedDate}</td>
								<td>
									<c:if test="${dto.inquiryStatus}">
										<span class="tags" id="handled">
											${str_status_handled}
										</span>
									</c:if>
									<c:if test="${not dto.inquiryStatus}">
										<span class="tags" id="unhandled">
											${str_status_unhandled}
										</span>
									</c:if>
								</td>
								<td>${dto.adminId}</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
				
				<div class="pagination">
					<c:if test="${count gt 0}">
						<c:if test="${startPage gt pageBlock}">
							<a href="/admin/inquiry?pageNum=${startPage - pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}&sortBy=${param.sortBy}">
								&laquo;
							</a>
						</c:if>
						<c:forEach var="i" begin="${startPage}" end="${endPage}">
							<c:if test="${i eq currentPage}">
							  <a class="hover current-page" href="#">${i}</a>
							</c:if>
							<c:if test="${i ne currentPage}">
							  <a class="hover" href="/admin/inquiry?pageNum=${i}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}&sortBy=${param.sortBy}">
							    ${i}
							  </a>
							</c:if>
						</c:forEach>
						<c:if test="${pageCount gt endPage}">
							<a  href="/admin/inquiry?pageNum=${startPage + pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}&sortBy=${param.sortBy}">
								&raquo;
							</a>
						</c:if>
					</c:if>
				</div>
			</main>
		</div>
    </div>
</body>