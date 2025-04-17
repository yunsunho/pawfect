<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="setting.jsp" %>
<c:set var="currentPage" value="main" />
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<html lang="ko">
<head>
<meta charset="UTF-8">
	<title>${page_title}</title>
	<link rel="stylesheet" href="/css/common.css">
	<link rel="stylesheet" href="/css/main.css">
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
	<script src="/js/script_main.js"></script>
</head>
<body>

	<main class="main">
		<section class="hero">
			<div class="tagline">${str_website_desc}</div>
			<h1>
				${str_main_quote1}<br> <span class="highlight">${str_main_quote2}</span>
			</h1>
			<form action="/main" method="get">
				<div class="search">
					<input type="text" name="keyword" placeholder="${placeholder_searchbar}" autocomplete="off"/>
					<button type="submit" id="searchBtn">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							viewBox="0 0 24 24" fill="none" stroke="currentColor"
							stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
							class="lucide lucide-search-icon lucide-search">
							<circle cx="11" cy="11" r="8" />
							<path d="m21 21-4.3-4.3" />
						</svg>
					</button>
				</div>
			</form>
		</section>
	</main>
</body>
</html>
