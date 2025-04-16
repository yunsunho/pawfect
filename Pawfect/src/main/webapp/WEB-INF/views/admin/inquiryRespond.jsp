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
        <p>${modal_confirm_reply}</p>
        <button id="confirmSubmit">${str_confirm }</button>
        <button onclick="closeConfirmModal()">${str_cancel }</button>
    </div>
</div>
<body>
    <div class="sidebar">
        <h2>
        	<a href="/admin" style="color: white;">
        		&nbsp;${sidebar_manage_site}
        	</a>
        </h2>
        <ul>
        	<li><a href="/admin/user">
        		<i class="fa-solid fa-user"></i>
        		&nbsp;${sidebar_manage_users }
        	</a></li>
            <li><a href="/admin/post">
            	<i class="fa-solid fa-list"></i>
            	&nbsp;${sidebar_manage_posts }
            </a></li>
            <li><a href="/admin/comment">
            	<i class="fa-solid fa-comments"></i>
            	&nbsp;${sidebar_manage_comments }
            </a></li>
            <li><a href="/admin/review">
            	<i class="fa-solid fa-database"></i>
            	&nbsp;${sidebar_manage_reviews }
            </a></li>
            <li><a href="/admin/inquiry">
            	<i class="fa-solid fa-list-check"></i>
            	&nbsp;${sidebar_manage_qna }
            </a></li>
        </ul>
        <div id="adminLogoutBtn">
	        <i id="logout-symbol" class="fa-solid fa-right-from-bracket"></i>
			&nbsp;
			<span id="logout-text">${sidebar_logout}</span>
		</div>
    </div>
    
    

    <div class="main-content" id="main-content">
        <h1>
        	<span class="welcome-text">${page_handle_inquiry}</span>&nbsp;
        	<span class="admin-name">#${dto.inquiryId}</span>
        </h1>
        &nbsp;&nbsp;
        <table class="board-table" style="margin: auto; width:80%;">
        	<tr>
        		<th class="label" style="width:15%">${label_inquiryId}</th>
        		<td>${dto.inquiryId}</td>
        		<th class="label" style="width:15%">${label_userId}</th>
        		<td>${dto.userId}</td>
        	</tr>
        	<tr>
        		<th class="label" style="width:15%">${label_inquiryRegdate}</th>
        		<td>${dto.formattedDate}</td>
        		<th class="label" style="width:15%">${label_inquiryStatus}</th>
        		<td>
        			<c:if test="${dto.inquiryStatus}">
						${str_status_handled}
					</c:if>
					<c:if test="${not dto.inquiryStatus}">
						${str_status_unhandled}
					</c:if>
				</td>
        	</tr>
        	<tr>
        		<th class="label" style="width:15%">${label_inquiryTitle}</th>
        		<td colspan="3" style="text-align:left">${dto.inquiryTitle}</tr>
        	</tr>
        	<tr>
        		<th class="label" style="width:15%;">${label_inquiryContent}</th>
        		<td colspan="3" style="text-align:left">${dto.inquiryContent}</tr>
        	</tr>
        	<c:if test="${dto.inquiryStatus}">
        		<tr>
	        		<th class="label" style="width:15%;">${label_inquiryReply}</th>
	        		<td colspan="3" style="text-align:left">${dto.inquiryReply}</tr>
        		</tr>
        	</c:if>
        </table>
        
        <c:if test="${not dto.inquiryStatus}">
        	<br><br>
        	<div style="margin: auto; width:80%;">
        	<h3>${str_admin_response}</h3>
	        <form action="inquiryRespond" method="post">
	        	<input type="hidden" name="inquiryId" value="${dto.inquiryId}">
	        	<textarea name="inquiryReply" id="editor-container" rows="5" required></textarea>
	        	<div class="button-container">
	        		<div class="inner-container">
			        	<button type="button" class="submit-btn" onclick="openConfirmModal(this)">${btn_respond}</button>
			        	<button type="reset" class="cancel-btn">${btn_cancel}</button>
		        	</div>
	        	</div>
	        </form>
	        </div>
        </c:if>
    </div>
	<div id="confirmModal" class="modal">
		<div class="modal-content">
	    	<p id="confirmModalMessage">${modal_confirm_logout}</p>
		    <div class="modal-buttons">
		      <button id="btnConfirmYes">${str_confirm}</button>
		      <button id="btnConfirmNo">${str_cancel}</button>
		    </div>
		</div>
	</div>
</body>
