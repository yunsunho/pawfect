<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="setting.jsp" %>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="/css/style_board_sidebar.css">
  
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
		        <li><a href="#">${str_view_my_posts}</a></li>
		        <li><a href="#">${str_view_my_comments}</a></li>
		        <li><a href="#">${str_community_guideline}</a></li>
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