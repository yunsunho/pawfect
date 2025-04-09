<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- list.jsp -->
<c:set var="page_list" value = "글 목록"/>
<!-- table labels -->
<c:set var="label_tag" value="말머리"/>
<c:set var="label_title" value="제목"/>
<c:set var="label_writer" value="작성자"/>
<c:set var="label_date" value="작성일"/>
<c:set var="label_likes" value="추천"/>
<c:set var="label_views" value="조회"/>
<!-- messages -->
<c:set var="msg_no_posts" value="현재 게시판에 글이 없습니다."/>



<!-- writeForm.jsp -->
<c:set var="page_write" value="게시물 작성"/>
<c:set var="btn_write" value="작성"/>
<c:set var="btn_cancel" value="취소"/>
<!-- 말머리 tags -->
<c:set var="str_default" value="전체"/>
<c:set var="str_review" value="후기"/>
<c:set var="str_recommendation" value="추천"/>
<c:set var="str_info" value="정보"/>
<c:set var="str_other" value="자유"/>
<c:set var="str_qna" value="질문"/>

<c:set var="tag_default" value="#전체"/>
<c:set var="tag_review" value="#후기"/>
<c:set var="tag_recommendation" value="#추천"/>
<c:set var="tag_info" value="#정보"/>
<c:set var="tag_other" value="#자유"/>
<c:set var="tag_qna" value="#질문"/>


<!-- writePro.jsp -->
<c:set var="msg_write_success" value="게시물이 등록되었습니다."/>
<c:set var="msg_write_fail" value="글 수정에 실패했습니다.\n잠시 후 다시 시도하세요."/>