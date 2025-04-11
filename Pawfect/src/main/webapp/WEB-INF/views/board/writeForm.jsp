<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="setting.jsp" %>
<c:set var="currentPage" value="writeForm" />
<!DOCTYPE html>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${page_write}</title>
    
    <!-- Quill Styles -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/style_board.css">
    <link rel="stylesheet" type="text/css" href="/css/style_board_list.css">
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/style_board_sidebar.css">
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <script src="/js/script_board.js" defer></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
	<div class="board-container">
		<aside class="sidebar">
			<%@ include file="/WEB-INF/views/board/sidebar.jsp" %>
		</aside>
	    <div class="container">
	    	<h2>${page_write}</h2>
	    	<!-- Category Tags -->
		    <div class="tag-container">
		        <div id="default" class="tag active" onclick="selectTag(this, '전체')">${tag_default}</div>
		        <div id="review" class="tag" onclick="selectTag(this, '후기')">${tag_review}</div>
		        <div id="recommendation" class="tag" onclick="selectTag(this, '추천')">${tag_recommendation}</div>
		        <div id="info" class="tag" onclick="selectTag(this, '정보')">${tag_info}</div>
		        <div id="other" class="tag" onclick="selectTag(this, '자유')">${tag_other}</div>
		        <div id="qna" class="tag" onclick="selectTag(this, '질문')">${tag_qna}</div>
		    </div>
		    
		    <!-- subject Input -->
		    <input type="text" id="subject" placeholder="제목을 입력하세요">
		    
		    <!-- Quill Editor -->
		    <div id="editor-container"></div>
		    
		    <!-- Buttons -->
		    <div class="button-container">
		        <button class="submit-btn" onclick="saveContent()">${btn_write}</button>
		        <button class="cancel-btn">${btn_cancel}</button>
		    </div>
    	</div>
    </div>
    <!-- Quill JS -->
    <!--
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <script>
        var quill = new Quill('#editor-container', {
            theme: 'snow',
            placeholder: '내용을 입력하세요',
            modules: { toolbar: [['bold', 'italic', 'underline', 'strike'], ['image', 'link']] }
        });
        
        function selectTag(element, tagName) {
            document.querySelectorAll('.tag').forEach(tag => tag.classList.remove('active'));
            element.classList.add('active');
            document.getElementById('selectedTag').value = tagName;
        }
        
        function saveContent() {
            var content = quill.root.innerHTML;
            var subject = document.getElementById('subject').value;
            var tag = document.querySelector('.tag.active').textContent;
            
            fetch('savePost.jsp', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'subject=' + encodeURIComponent(subject) + '&tag=' + encodeURIComponent(tag) + '&content=' + encodeURIComponent(content)
            }).then(response => response.text()).then(data => {
                alert("Saved: " + data);
            });
        }
    </script>
     -->
</body>
</html>
