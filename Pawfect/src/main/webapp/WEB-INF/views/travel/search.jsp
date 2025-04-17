<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ include file="setting.jsp" %>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/common.css">
	<link rel="stylesheet" href="/css/main.css">
	<link rel="stylesheet" href="/css/style_search.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>
	<div class="search-container">
		<form action="main" method="get" style="width: 100%; display: flex;">
			<input type="text" name="keyword" placeholder="${placeholder_searchbar}" value="${param.keyword}" />
			<button type="submit">
				<i class="fas fa-search"></i>
			</button>
		</form>
	</div>

	<div id="ajaxResults">
		<c:if test="${not empty areaList}">
			<c:forEach var="place" items="${areaList}">
				<a href="/detail/${place.contentid}/${place.contenttypeid}">
					<div class="result-item">
						<c:choose>
							<c:when test="${not empty place.firstimage}">
								<img src="${place.firstimage}" alt="${place.title}" />
							</c:when>
							<c:otherwise>
								<img src="${'/images/no-image.png'}" alt="${str_no_img}"/>
							</c:otherwise>
						</c:choose>
						<div class="result-info">
							<strong>${place.title}</strong><br/>
							<small>${place.addr1}</small>
						</div>
					</div>
				</a>
			</c:forEach>
		</c:if>
		<c:if test="${empty areaList}">
			<p style="text-align:center; font-size: 18px; color: #666;">${msg_no_results}</p>
		</c:if>
	</div>
	<div class="center">
		<c:if test="${pageNo ne 1}">
			<a class="button" id="btn-prev" href="/main?keyword=${keyword}&pageNo=${pageNo-1}">&laquo;&nbsp;${btn_previous}</a>
		</c:if>
		<c:if test="${pageNo ne totalPages}">
			<a class="button" id="btn-next" href="/main?keyword=${keyword}&pageNo=${pageNo+1}">&raquo;&nbsp;${btn_next}</a>
		</c:if>
	</div>
	<br><br><br><br><br>
</body>
</html>
