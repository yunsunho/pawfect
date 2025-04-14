<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	

<div class="profile-tab">
	<!-- 프로필 이미지 -->
	<div class="center-box">
		<div class="profile-img-box">
			<c:choose>
				<c:when test="${not empty user.userImage}">
					<img class="profile-img" src="${user.userImage}?v=${now.time}"
						alt="프로필 이미지" />
				</c:when>
				<c:otherwise>
					<img class="profile-img" src="/images/default_profile.jpg"
						alt="기본 이미지" />
				</c:otherwise>
			</c:choose>
			<br />
			<button class="edit-btn" id="editProfileImgBtn">수정</button>
			<button class="edit-btn" id="deleteProfileImgBtn">삭제</button>
			<input type="file" name="profileImage" id="profileImageInput"
				accept="image/*" style="display: none;" />
		</div>
	</div>

	<!-- 닉네임 -->
	<div class="box" style="margin-bottom: 20px;">
		<label for="nickname">닉네임</label><br />
		<div class="input-group">
			<input type="text" id="nickname" name="nickname"
				value="${user.userNickname}" data-can-edit="${canEditNickname}"
				readonly />
			<button class="edit-btn" id="editNicknameBtn">수정</button>
		</div>
		<p class="nickname-hint">※ 닉네임은 30일마다 변경 가능합니다.</p>
	</div>

	<!-- 반려동물 정보 -->
	<div class="box">
		<div class="input-group" style="margin-bottom: 15px;">
			<span style="font-weight: bold;">반려동물 정보</span>
			<button class="edit-btn" id="editPetBtn">수정</button>
		</div>

		<div class="input-group">
			<label for="petName">이름</label> <input type="text" id="petName"
				name="petName" value="${user.petName}" readonly />
		</div>

		<div class="input-group">
			<label for="petType">종류</label> <select name="petType" id="petType"
				disabled>
				<option value="0" <c:if test="${user.petType == 0}">selected</c:if>>선택</option>
				<option value="1" <c:if test="${user.petType == 1}">selected</c:if>>강아지</option>
				<option value="2" <c:if test="${user.petType == 2}">selected</c:if>>고양이</option>
				<option value="3" <c:if test="${user.petType == 3}">selected</c:if>>기타</option>
			</select>
		</div>
	</div>

	<!-- 가입일 -->
	<div class="join-date-box">
		가입일 :
		<fmt:formatDate value="${user.userRegdate}" pattern="yyyy-MM-dd" />
	</div>

	<!-- 저장 버튼 -->
	<div class="form-actions save-btn-row">
		<button id="btnSaveProfile" class="edit-btn">저장</button>
	</div>
</div>

<!-- 공통 모달 -->
<div id="commonModal" class="modal">
	<div class="modal-content">
		<p id="modalMessage">메시지</p>
		<button onclick="closeModal()">확인</button>
	</div>
</div>

<!-- 삭제 확인 모달 -->
<div id="confirmModal" class="modal">
	<div class="modal-content">
		<p id="confirmModalMessage">정말 삭제하시겠습니까?</p>
		<div class="modal-buttons">
			<button id="btnConfirmYes">확인</button>
			<button id="btnConfirmNo">취소</button>
		</div>
	</div>
</div>
