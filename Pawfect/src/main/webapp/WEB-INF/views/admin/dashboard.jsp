<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="setting.jsp" %>

<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/css/common.css">
	<link rel="stylesheet" type="text/css" href="/css/admin_dashboard.css">
	<script src="/js/script_admin.js"></script>
</head>
<body>
    <div class="sidebar">
        <h2>${sidebar_manage_site}</h2>
        <ul>
        	<li onclick="loadSection('users')">${sidebar_manage_users }</li>
            <li onclick="loadSection('users')">${sidebar_manage_users }</li>
            <li onclick="loadSection('posts')">${sidebar_manage_posts }</li>
            <li onclick="loadSection('comments')">${sidebar_manage_comments }</li>
            <li onclick="loadSection('comments')">${sidebar_manage_reviews }</li>
            <li onclick="loadSection('comments')">${sidebar_manage_qna }</li>
        </ul>
    </div>

    <div class="main-content" id="main-content">
        <h1>Welcome to the Admin Dashboard</h1>
    </div>

</body>
