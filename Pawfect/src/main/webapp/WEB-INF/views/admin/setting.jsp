<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- menu -->
<c:set var="sidebar_manage_site" value="사이트 관리"/>
<c:set var="sidebar_manage_users" value="사용자 관리"/>
<c:set var="sidebar_manage_posts" value="게시물 관리"/>
<c:set var="sidebar_manage_comments" value="댓글 관리"/>
<c:set var="sidebar_manage_reviews" value="리뷰 관리"/>
<c:set var="sidebar_manage_qna" value="1:1 문의 처리"/>

<!-- DASHBOARD -->
<c:set var="str_admin_page" value="관리자 페이지"/>
<c:set var="str_welcome" value="환영합니다"/>
<!-- dashboard stats -->
<c:set var="str_active_user" value="활동"/>
<c:set var="str_withdrawn_user" value="탈퇴"/>
<c:set var="str_banned_user" value="정지"/>
<c:set var="str_review_count" value="리뷰"/>
<c:set var="str_post_count" value="게시물"/>
<c:set var="str_comment_count" value="댓글"/>


<!-- 문의 처리 -->
<c:set var="page_inquiry" value="문의 페이지"/>
<!-- filter -->
<c:set var="label_start_date" value="시작일"/>
<c:set var="label_end_date" value="종료일"/>
<c:set var="label_default_all" value="전체"/>
<c:set var="label_unhandled" value="미처리"/>
<c:set var="label_handled" value="처리"/>
<c:set var="btn_search" value="검색"/>
<c:set var="placeholder_searchbar" value="검색어를 입력하세요"/>
<!-- table -->
<c:set var="label_inquiryId" value="ID"/>
<c:set var="label_inquiryTitle" value="제목"/>
<c:set var="label_inquiryContent" value="내용"/>
<c:set var="label_userId" value="사용자 ID"/>
<c:set var="label_inquiryRegdate" value="작성일"/>
<c:set var="label_inquiryStatus" value="상태"/>
<c:set var="label_adminId" value="관리자 ID"/>
<c:set var="label_inquiryReply" value="관리자 답변"/>
<c:set var="msg_no_inquiry" value="문의 글이 없습니다."/>
<!-- inquiryRespond -->
<c:set var="page_handle_inquiry" value="문의 처리"/>
<c:set var="str_status_unhandled" value="대기"/>
<c:set var="str_status_handled" value="답변 완료"/>
<c:set var="str_admin_response" value="관리자 답변"/>
<c:set var="placeholder_inquiry_response" value="답변을 입력하세요"/>
<c:set var="btn_respond" value="보내기"/>
<c:set var="btn_cancel" value="취소"/>
<!--  
inquiryId INT PRIMARY KEY AUTO_INCREMENT,
  inquiryTitle VARCHAR(255) NOT NULL,
  userId VARCHAR(50) NOT NULL,
  inquiryContent TEXT NOT NULL,
  inquiryReply TEXT,
  inquiryStatus BOOLEAN DEFAULT 0,
  adminId VARCHAR(50),
  inquiryRegdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 -->