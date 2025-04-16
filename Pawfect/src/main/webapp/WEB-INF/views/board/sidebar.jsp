<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="setting.jsp" %>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="/css/style_board_sidebar.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  
  
</head>
<body>
	<div class="community-layout">
		<aside class="sidebar">
	    	<a class="button-write" href="/board/write">${page_write}</a>
	    	
	    	<h3>${label_tags}</h3>
	    	<ul class="tag-filter">
				<li><a class="button button-default" href="/board?postType=0">${tag_default}</a></li>
				<li><a class="button button-review" href="/board?postType=1">${tag_review}</a></li>
				<li><a class="button button-recommend" href="/board?postType=2">${tag_recommendation}</a></li>
				<li><a class="button button-info" href="/board?postType=3">${tag_info }</a></li>
				<li><a class="button button-other" href="/board?postType=4">${tag_other }</a></li>
				<li><a class="button button-qna" href="/board?postType=5">${tag_qna}</a></li>
			</ul>
	      	
	      	<h3>${label_redirect}</h3>
	      	<ul class="quick-links">
		        <li><a href="/board?myPost=true">${str_view_my_posts}</a></li>
		        <li><a href="/board/commentlist">${str_view_my_comments}</a></li>
	        </ul>
	        
	        
	        <ul class="trending-links">
	        	<c:if test="${not empty hottestPosts}">
	        		<h3>${label_hot}</h3>
	        		<c:forEach var="post" items="${hottestPosts}" begin="0" end="4">
	        			<li>
	        				<i class="fa-solid fa-star" style="color: #B0E0E6;"></i>
	        				<a href="/board/content?num=${post.postId}">
		        				<c:choose>
				                    <c:when test="${fn:length(post.postTitle) > 15}">
				                        ${fn:substring(post.postTitle, 0, 15)}...
				                    </c:when>
				                    <c:otherwise>
				                        ${post.postTitle}
				                    </c:otherwise>
			                	</c:choose>
	        				</a>
	        			</li>
	        		</c:forEach> 
	        	</c:if>
	        </ul>
	        
			<h3>${label_stats}</h3>
			<div class="stats">
		        <p><b>${str_total_posts}: </b>${totalPosts}</p>
		        <p><b>${str_total_comments}: </b>${totalComments}</p>
		        <p><b>${str_total_users}: </b>${totalUsers}</p>
      		</div>
      		
      		
      		<script type="text/javascript" src="/js/script_board.js"></script>
	    </aside>
	</div>
</body>