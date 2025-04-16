<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- menu -->
<c:set var="sidebar_manage_site" value="Pawfect Tour"/>
<c:set var="sidebar_manage_users" value="회원 관리"/>
<c:set var="sidebar_manage_posts" value="게시물 관리"/>
<c:set var="sidebar_manage_comments" value="댓글 관리"/>
<c:set var="sidebar_manage_reviews" value="리뷰 관리"/>
<c:set var="sidebar_manage_qna" value="1:1 문의 처리"/>

<!-- DASHBOARD -->
<c:set var="str_admin_page" value="관리자 페이지"/>
<c:set var="str_welcome" value="환영합니다"/>
<c:set var="str_admin" value="관리자"/>
<!-- dashboard stats -->
<c:set var="str_active_user" value="활동"/>
<c:set var="str_withdrawn_user" value="탈퇴"/>
<c:set var="str_banned_user" value="정지"/>
<c:set var="str_review_count" value="리뷰"/>
<c:set var="str_post_count" value="게시물"/>
<c:set var="str_comment_count" value="댓글"/>

<!-- 회원 관리 -->
<c:set var="page_user" value="회원 관리"/>
<c:set var="placeholder_searchbar_id" value="회원 아이디를 입력하세요"/>
<!-- user table -->
<c:set var="label_userId" value="ID"/>
<c:set var="label_userName" value="이름"/>
<c:set var="label_userTel" value="전화번호"/>
<c:set var="label_userNickname" value="사용자명"/>
<c:set var="label_userRegdate" value="가입일"/>
<c:set var="label_email" value="이메일"/>
<c:set var="label_userStatus" value="상태"/>
<c:set var="label_action" value="처리"/>

<c:set var="msg_no_user" value="회원이 없습니다."/>
<c:set var="btn_ban_user" value="회원 정지"/>
<c:set var="modal_confirm_ban" value="정말로 회원을 정지 시키겠습니까?"/>
 
<!-- 게시물 관리 -->
<c:set var="page_post" value="게시물 관리"/>
<c:set var="label_postId" value="ID"/>
<c:set var="label_post_userId" value="사용자 아이디"/>
<c:set var="label_postTitle" value="제목"/>
<c:set var="label_postRegdate" value="작성일"/>

<c:set var="msg_no_post" value="게시물이 없습니다."/>
<c:set var="btn_delete_post" value="게시물 삭제"/>
<c:set var="modal_confirm_delete_post" value="정말로 게시물을 삭제하시겠습니까?"/>

<!-- 댓글 관리 -->
<c:set var="page_comment" value="댓글 관리"/>
<c:set var="label_commentId" value="ID"/>
<c:set var="label_comContent" value="댓글 내용"/>
<c:set var="msg_no_comment" value="댓글이 없습니다."/>
<c:set var="btn_delete_comment" value="댓글 삭제"/>
<c:set var="modal_confirm_delete_comment" value="정말로 댓글을 삭제하시겠습니까?"/>

<!-- 리뷰 관리 -->
<c:set var="page_review" value="리뷰 관리"/>
<c:set var="label_reviewId" value="ID"/>
<!-- userId -->
<c:set var="label_reviewRating" value="별점"/>
<c:set var="reviewContent" value="내용"/>

<c:set var="msg_no_review" value="리뷰가 없습니다."/>
<c:set var="btn_delete_review" value="리뷰 삭제"/>
<c:set var="modal_confirm_delete_review" value="정말로 리뷰를 삭제하시겠습니까?"/>
<!-- 작성일 -->

<!-- 

reviewId INT PRIMARY KEY AUTO_INCREMENT,
contentId INT,
contentTypeId INT,
title VARCHAR(255),
imgpath VARCHAR(255),
userId VARCHAR(50),
reviewRating INT NOT NULL CHECK (reviewRating BETWEEN 1 AND 5),
reviewContent TEXT NOT NULL,
reviewRegdate 
 -->

<!-- 문의 처리 -->
<c:set var="page_inquiry" value="문의 내역"/>
<!-- filter -->
<c:set var="label_start_date" value="시작일"/>
<c:set var="label_end_date" value="종료일"/>
<c:set var="label_default_all" value="전체"/>
<c:set var="label_unhandled" value="미처리"/>
<c:set var="label_handled" value="처리"/>
<c:set var="btn_search" value="검색"/>
<c:set var="placeholder_searchbar" value="검색어를 입력하세요"/>
<!-- inquiry table -->
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
<!-- modal -->
<c:set var="modal_confirm_reply" value="정말로 답변을 전송하시겠습니까?"/>
<c:set var="str_confirm" value="확인"/>
<c:set var="str_cancel" value="취소"/>
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