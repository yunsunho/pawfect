<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="setting.jsp" %>

<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="/css/common.css">
		<!-- Quill Styles -->
	    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
	    <link rel="stylesheet" href="/css/style_board.css">
	    <link rel="stylesheet" type="text/css" href="/css/style_board_write.css">
	    <link rel="stylesheet" href="/css/common.css">
	    <link rel="stylesheet" href="/css/style_board_sidebar.css">
	    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
	    <script src="/js/script_board.js" defer></script>
	    <script src="/js/script_board_modify.js" defer></script>
	</head>
	<body>
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
		<div class="board-container">
			<aside class="sidebar">
				<%@ include file="/WEB-INF/views/board/sidebar.jsp" %>
			</aside>
		    <div class="container">
		    	<form name="modifyForm" method="post" action=modify>
			    	<h2>${page_modify}</h2>
			    	<div class="tag-row">
				    	<h3>${label_tag}&nbsp;</h3>
				    	<input type="hidden" id="initial-tag" value="${postDto.postType}"/>
				    	<ul>
						    <li>
						        <input id="tag-default" type="radio" value="0" name="tag-select"/>
						        <label for="tag-default">${tag_default}</label>
						    </li>
						    <li>
						        <input id="tag-review" type="radio" value="1" name="tag-select"/>
						        <label for="tag-review">${tag_review}</label>
						    </li>
						    <li>
						        <input id="tag-recommendation" type="radio" value="2" name="tag-select"/>
						        <label for="tag-recommendation">${tag_recommendation}</label>
						    </li>
						    <li>
						        <input id="tag-info" type="radio" value="3" name="tag-select"/>
						        <label for="tag-info">${tag_info}</label>
						    </li>
						    <li>
						        <input id="tag-other" type="radio" value="4" name="tag-select"/>
						        <label for="tag-other">${tag_other}</label>
						    </li>
						    <li>
						        <input id="tag-qna" type="radio" value="5" name="tag-select"/>
						        <label for="tag-qna">${tag_qna}</label>
						    </li>
						</ul>
					</div>
			    	
				    <input type="text" id="subject" value="${postDto.postTitle}">
				    
				    <!-- Quill Editor -->
				    <textarea id="existingContent" style="display:none">${postDto.postContent}</textarea>
				    <div id="editor-container"></div>
				    <input type="hidden" name="postId" value="${postDto.postId}">
				    <input type="hidden" name="postTitle">
			    	<input type="hidden" name="postContent">
			    	<input type="hidden" name="postType">
				    
				    
				    <!-- Buttons -->
				    <div class="button-container">
				        <button class="submit-btn" type="submit">${btn_modify}</button>
				        <button class="cancel-btn">${btn_cancel}</button>
				    </div>
			    </form>
	    	</div>
	    </div>
	</body>
</html>