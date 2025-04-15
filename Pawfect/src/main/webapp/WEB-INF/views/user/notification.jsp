<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="notificationSidebar" class="notification-sidebar">
	<div class="sidebar-header">
		<h3>알림 <span class="noti-subtext">(최근 7일)</span></h3>
		<button id="closeNotifBtn">
		<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-x-icon lucide-x"><path d="M18 6 6 18"/><path d="m6 6 12 12"/></svg>
		</button>
	</div>
	<div class="sidebar-content" id="notificationList">
		<p class="empty-msg">알림이 없습니다.</p>
	</div>
</div>
