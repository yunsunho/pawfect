<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- list.jsp -->
<c:set var="page_list" value = "글 목록"/>
<!-- filter -->
<c:set var="label_start_date" value="시작일"/>
<c:set var="label_end_date" value="종료일"/>
<c:set var="label_sort_latest" value="최신순"/>
<c:set var="label_sort_views" value="조회순"/>
<c:set var="label_sort_comments" value="댓글순"/>
<c:set var="label_sort_likes" value="좋아요순"/>
<c:set var="btn_search" value="검색"/>
<c:set var="placeholder_searchbar" value="검색어를 입력하세요"/>
<!-- table labels -->
<c:set var="label_tag" value="말머리"/>
<c:set var="label_title" value="제목"/>
<c:set var="label_writer" value="작성자"/>
<c:set var="label_date" value="작성일"/>
<c:set var="label_likes" value="추천"/>
<c:set var="label_views" value="조회"/>
<!-- messages -->
<c:set var="msg_no_posts" value="게시판에 글이 없습니다."/>


<!-- sidebar.jsp -->
<c:set var="label_tags" value="말머리 태그"/>
<c:set var="label_redirect" value="바로가기"/>
<c:set var="label_stats" value="통계"/>
<c:set var="str_view_my_posts" value="내 글 보기"/>
<c:set var="str_view_my_comments" value="내 댓글 보기"/>
<c:set var="str_community_guideline" value="커뮤니티 가이드라인"/>
<c:set var="str_total_posts" value="총 게시글"/>
<c:set var="str_total_comments" value="총 댓글"/>
<c:set var="str_total_users" value="회원 수"/>

<!-- content.jsp -->
<!-- dropdown menu -->
<c:set var="str_modify_post" value="게시물 수정"/>
<c:set var="str_delete_post" value="게시물 삭제"/>
<c:set var="str_confirm_delete" value="정말 이 글을 삭제하시겠습니까?"/>
<c:set var="btn_delete" value="삭제"/>

<!-- writeForm.jsp -->
<c:set var="page_write" value="게시물 작성"/>
<c:set var="label_tag" value="말머리:"/>
<c:set var="placeholder_title" value="제목을 입력하세요"/>

<c:set var="btn_write" value="작성"/>
<c:set var="btn_cancel" value="취소"/>
<!-- 말머리 tags -->
<c:set var="str_default" value="전체"/>
<c:set var="str_review" value="후기"/>
<c:set var="str_recommendation" value="추천"/>
<c:set var="str_info" value="정보"/>
<c:set var="str_other" value="일상/자유"/>
<c:set var="str_qna" value="질문"/>

<c:set var="tag_default" value="#전체"/>
<c:set var="tag_review" value="#후기"/>
<c:set var="tag_recommendation" value="#추천"/>
<c:set var="tag_info" value="#정보"/>
<c:set var="tag_other" value="#일상/자유"/>
<c:set var="tag_qna" value="#질문"/>


<!-- writePro.jsp -->
<c:set var="msg_write_success" value="게시물이 등록되었습니다."/>
<c:set var="msg_write_fail" value="글 수정에 실패했습니다.\n잠시 후 다시 시도하세요."/>


<!-- modifyForm.jsp -->
<c:set var="page_modify" value="게시물 수정"/>
<c:set var="btn_modify" value="저장"/>

<!-- modal -->
<c:set var="msg_loading" value="잠시 후 페이지가 이동합니다..."/>
