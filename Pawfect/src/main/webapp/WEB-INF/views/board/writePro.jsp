<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="setting.jsp" %>
<script src="${project}script_board.js"></script>

<h2>${page_write}</h2>

<c:if test="${result == 0}">
    <script>
        alert(msg_write_fail);
    </script>
    <c:redirect url="/board"/>
</c:if>

<c:if test="${result == 1}">
    <script>
        alert(msg_write_success);
    </script>
    <c:redirect url="/board"/>
</c:if>
