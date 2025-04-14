<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="setting.jsp" %>

<!DOCTYPE html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/css/common.css">
	<link rel="stylesheet" type="text/css" href="/css/style_board.css">
	<link rel="stylesheet" type="text/css" href="/css/style_board_sidebar.css">
	<link rel="stylesheet" type="text/css" href="/css/style_board_list.css">
</head>
<body>
	<c:set var="currentPage">${currentPage}</c:set>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
	
	<div class="board-container">
		<aside class="sidebar">
			<%@ include file="/WEB-INF/views/board/sidebar.jsp" %>
		</aside>
		<main class="board-main">
			<form action="/board/commentlist" method="get" class="board-filter-form">
				<label for="startDate">${label_start_date}</label>
				<input type="date" name="startDate" value="${param.startDate}"/>
				
				<label for="endDate">${label_end_date}</label>
				<input type="date" name="endDate" value="${param.endDate}"/>
				

				<input id="searchbox" type="text" name="keyword" placeholder="${placeholder_searchbar}" value="${param.keyword}"/>
				<button type="submit">${btn_search}</button>
				
			</form>
			
			<table class="board-table">
				<tr>
					<th class="label" style="width:30%">${label_post_title}</th>
					<th class="label">${label_comment}</th>
					<th class="label" style="width:15%">${label_date}</th>
				</tr>
		
				<!-- 게시판에 글이 없는 경우 -->
				<c:if test="${count==0}">
					<tr>
						<td colspan="3" style="text-align: center;">
							${msg_no_posts}
						</td>
					</tr>
				</c:if>
				
				<c:if test="${count!=0}">
					<c:forEach var="dto" items="${dtos}">
						<tr>
							<td style="text-align:left">
								<a class="post-title" href="/board/content?num=${dto.postId}">
									${dto.postTitle}
								</a>
							</td>
							<td style="text-align: left">${dto.comContent}</td>
							<td>${dto.formattedDate}</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
			<div class="pagination">
				<c:if test="${count gt 0}">
					<c:if test="${startPage gt pageBlock}">
						<a href="/board/commentlist?pageNum=${startPage - pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
							&laquo;
						</a>
					</c:if>
					<c:forEach var="i" begin="${startPage}" end="${endPage}">
						<c:if test="${i eq currentPage}">
						  <a class="hover current-page" href="#">${i}</a>
						</c:if>
						<c:if test="${i ne currentPage}">
						  <a class="hover" href="/board/commentlist?pageNum=${i}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
						    ${i}
						  </a>
						</c:if>
					</c:forEach>
					<c:if test="${pageCount gt endPage}">
						<a  href="/board/commentlist?pageNum=${startPage + pageBlock}&keyword=${param.keyword}&startDate=${param.startDate}&endDate=${param.endDate}">
							&raquo;
						</a>
					</c:if>
				</c:if>
			</div>
		</main>
	</div>
</body>