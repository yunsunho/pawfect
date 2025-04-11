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
	    	<a class="button-write" href="board/writeForm">${page_write}</a>
	    	
	    	<h3>${label_tags}</h3>
	    	<ul class="tag-filter">
				<li><a class="button button-default" href="board?postType=0" onclick="setActiveLink(this)">${tag_default}</a></li>
				<li><a class="button button-review" href="board?postType=1" onclick="setActiveLink(this)">${tag_review}</a></li>
				<li><a class="button button-recommend" href="board?postType=2&keyword=${keyword}&sortBy=${sortBy}&startDate=${startDate}&endDate=${endDate}" onclick="setActiveLink(this)">${tag_recommendation}</a></li>
				<li><a class="button button-info" href="board?postType=3&keyword=${keyword}&sortBy=${sortBy}&startDate=${startDate}&endDate=${endDate}" onclick="setActiveLink(this)">${tag_info }</a></li>
				<li><a class="button button-other" href="board?postType=4&keyword=${keyword}&sortBy=${sortBy}&startDate=${startDate}&endDate=${endDate}" onclick="setActiveLink(this)">${tag_other }</a></li>
				<li><a class="button button-qna" href="board?postType=5&keyword=${keyword}&sortBy=${sortBy}&startDate=${startDate}&endDate=${endDate}" onclick="setActiveLink(this)">${tag_qna}</a></li>
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
	    </aside>
	</div>
	<script>
    function setActiveLink(link) {
    	// Remove the active class from all links
    	const links = document.querySelectorAll('.button');
    	links.forEach(function(btn) {
        	btn.classList.remove('active');
    	});
    	// Add the active class to the clicked link
      	link.classList.add('active');
    }
  </script>
</body>