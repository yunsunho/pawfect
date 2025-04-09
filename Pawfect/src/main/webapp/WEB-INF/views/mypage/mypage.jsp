<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/common.css" />
<link rel="stylesheet" href="/css/mypage.css" />
</head>
<body>

	<div class="mypage-container">
		<h2 class="mypage-title">마이페이지</h2>

		<div class="mypage-content">
			<!-- 좌측 메뉴 -->
			<div class="mypage-sidebar">
				<ul>
					<li class="active">My 프로필</li>
					<li><a href="#">회원정보 수정</a></li>
					<li><a href="#">비밀번호 변경</a></li>
					<li><a href="#">북마크 리스트</a></li>
					<li><a href="#">커뮤니티 활동내역</a></li>
					<li><a href="#">1:1 문의</a></li>
				</ul>
			</div>

			<!-- 우측 기본 정보 -->
			<div class="mypage-main">
				<div class="profile-box">
					<img
						src="${user.userImage != null ? user.userImage : '/images/default_profile.jpg'}"
						class="profile-img" alt="프로필 이미지" />
					<h3 class="nickname">${user.userNickname}</h3>
					<p class="reg-date">
						가입일:
						<fmt:formatDate value="${user.userRegdate}" pattern="yyyy-MM-dd" />
					</p>

					<div class="profile-actions">
						<button class="edit-btn">정보 수정</button>
						<button class="delete-btn">탈퇴하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
