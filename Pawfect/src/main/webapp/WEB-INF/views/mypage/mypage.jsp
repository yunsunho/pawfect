<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/mypage.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/mypage.js"></script>
</head>
<body>

	<jsp:include page="../common/header.jsp" />

	<div class="mypage-wrapper">
		<div class="mypage-container">
			<h2 class="mypage-title">마이페이지</h2>

			<div class="mypage-content">
				<!-- 좌측 메뉴 -->
				<div class="mypage-sidebar">
					<ul>
						<li class="active" data-tab="profile"> 프로필</li>
						<li data-tab="info">내 정보</li>
						<li data-tab="password">비밀번호 변경</li>
						<li data-tab="bookmark">북마크 리스트</li>
						<li data-tab="activity">활동내역</li>
						<li data-tab="inquiry">1:1 문의</li>
					</ul>
				</div>

				<div class="mypage-main" id="mypage-content-area"></div>
			</div>
		</div>
	</div>

</body>
</html>
