<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="setting.jsp" %>
<c:set var="currentPage" value="list" />
<!DOCTYPE html>

<html lang="ko">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/css/style_board_list.css">
	<link rel="stylesheet" href="/css/common.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<table>
		<tr>
			<th class="label">${label_tag}</th>
			<th class="label">${label_title}</th>
			<th class="label">${label_writer}</th>
			<th class="label">${label_date}</th>
			<th class="label">${label_likes}</th>
			<th class="label">${label_views}</th>
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
								${tag_review}
							</c:when>
							<c:when test="${dto.postType eq 2}">
								${tag_recommendation}
							</c:when>
							<c:when test="${dto.postType eq 3}">
								${tag_info}
							</c:when>
							<c:when test="${dto.postType eq 4}">
								${tag_other}
							</c:when>
							<c:when test="${dto.postType eq 5}">
								${tag_qna}
							</c:when>
							<c:otherwise>
								${tag_default}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<a href="boardcontent?num=${dto.postId}&pageNum=${pageNum}&number=${number+1}">
							${dto.postTitle}
						</a>
					</td>
					<td>${dto.userId}</td> <!-- CHANGE to userId-->
					<td>${dto.postRegdate}</td>
					<!--
					<td>${like_count}</td>
					  -->
					<td>${dto.postViewCount}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<br>
</body>