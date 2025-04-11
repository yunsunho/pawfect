<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="setting.jsp" %>

<!DOCTYPE html>

<html lang="ko">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/css/style_board_list.css">
	<link rel="stylesheet" href="/css/common.css">
	<link rel="stylesheet" href="/css/style_board_sidebar.css">
</head>
<body>
	<c:set var="currentPage">${currentPage}</c:set>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
	
	<div class="board-container">
		<aside class="sidebar">
			<%@ include file="/WEB-INF/views/board/sidebar.jsp" %>
		</aside>
		<main class="board-main">
			<form action="board" method="get" class="board-filter-form">
				<label for="startDate">${label_start_date}</label>
				<input type="date" name="startDate" value="${param.startDate}"/>
				
				<label for="endDate">${label_end_date}</label>
				<input type="date" name="endDate" value="${param.endDate}"/>
				
				<input type="text" name="keyword" placeholder="검색어를 입력하세요" value="${param.keyword}"/>
				<select name="sortBy">
					<option value="latest" ${param.sortBy == 'latest' ? 'selected' : ''}>${label_sort_latest}</option>
					<option value="views" ${param.sortBy == 'views' ? 'selected' : ''}>${label_sort_views}</option>
					<option value="comments" ${param.sortBy == 'comments' ? 'selected' : ''}>${label_sort_comments}</option>
					<option value="likes" ${param.sortBy == 'likes' ? 'selected' : '' }>${label_sort_likes}</option>
				</select>
				
				<c:if test="${not empty param.postType}">
				    <input type="hidden" name="postType" value="${param.postType}"/>
				</c:if>
				
				<button type="submit">${btn_search}</button>
				
			</form>
			
			<table class="board-table">
				<tr>
					<th class="label" style="width:10%">${label_tag}</th>
					<th class="label">${label_title}</th>
					<th class="label" style="width:15%">${label_writer}</th>
					<th class="label" style="width:15%">${label_date}</th>
					<th class="label" style="width:7%">${label_likes}</th>
					<th class="label" style="width:7%">${label_views}</th>
				</tr>
		
				<!-- 게시판에 글이 없는 경우 -->
				<c:if test="${count==0}">
					<tr>
						<td colspan="6" style="text-align: center;">
							${msg_no_posts}
						</td>
					</tr>
				</c:if>
				
				<c:if test="${count!=0}">
					<c:forEach var="dto" items="${dtos}">
						<tr>
							<td class="tags">
								<c:choose>
									<c:when test="${dto.postType eq 1}">
										<button class="tags" id="tag-review">${tag_review}</button>
									</c:when>
									<c:when test="${dto.postType eq 2}">
										<button class="tags" id="tag-recommendation">${tag_recommendation}</button>
									</c:when>
									<c:when test="${dto.postType eq 3}">
										<button class="tags" id="tag-info">${tag_info}</button>
									</c:when>
									<c:when test="${dto.postType eq 4}">
										<button class="tags" id="tag-other">${tag_other}</button>
									</c:when>
									<c:when test="${dto.postType eq 5}">
										<button class="tags" id="tag-qna">${tag_qna}</button>
									</c:when>
									<c:otherwise>
										<button class="tags" id="tag-default">${tag_default}</button>
									</c:otherwise>
								</c:choose>
							</td>
							<td style="text-align:left">
								<a class="post-title" href="board/content?num=${dto.postId}&pageNum=${pageNum}&number=${number+1}">
									${dto.postTitle}
								</a>
							</td>
							<td>${dto.displayName}</td>
							<td>${dto.postRegdate}</td>
							<td>${dto.likeCount}</td>
							<td>${dto.postViewCount}</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
			<div class="pagination">
				<c:if test="${count gt 0}">
					<c:if test="${startPage gt pageBlock}">
						<a href="board?pageNum=${startPage - pageBlock}">
							&laquo;
						</a>
					</c:if>
					<c:forEach var="i" begin="${startPage}" end="${endPage}">
						<c:if test="${i eq currentPage}">
						  <a class="hover current-page" href="#">${i}</a>
						</c:if>
						<c:if test="${i ne currentPage}">
						  <a class="hover" href="board?pageNum=${i}&keyword=${param.keyword}&sortBy=${param.sortBy}&startDate=${param.startDate}&endDate=${param.endDate}&postType=${param.postType}">
						    ${i}
						  </a>
						</c:if>
					</c:forEach>
					<c:if test="${pageCount gt endPage}">
						<a  href="board?pageNum=${startPage + pageBlock}&keyword=${param.keyword}&sortBy=${param.sortBy}&startDate=${param.startDate}&endDate=${param.endDate}&postType=${param.postType}">
							&raquo;
						</a>
					</c:if>
				</c:if>
			</div>
		</main>
	</div>
</body>