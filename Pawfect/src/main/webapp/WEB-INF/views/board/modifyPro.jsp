<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="setting.jsp" %>
<script src="/js/script_board_modify.js" defer></script>
<c:if test="${result eq 0}">
	<c:redirect url="/board"/>
	<script type="text/javascript">
		alert(error_modify);
		console.log("unsuccessful");
	</script>
</c:if>
<c:if test="${result eq 1}">
	<c:redirect url="/board"/>
	<script type="text/javascript">
		alert("successful");
		console.log("successful");
	</script>
</c:if>