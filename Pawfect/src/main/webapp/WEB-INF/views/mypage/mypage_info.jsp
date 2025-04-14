<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="profile-tab">
	<!-- 이름 / 아이디 -->
	<div class="box">
		<div class="input-group">
			<label class="input-label">이름</label> <input type="text"
				value="${user.userName}" readonly tabindex="-1"
				style="pointer-events: none;" />
		</div>
		<div class="input-group">
			<label class="input-label">아이디</label> <input type="text"
				value="${user.userId}" readonly tabindex="-1"
				style="pointer-events: none;" />
		</div>
	</div>
	<!-- 이메일 -->
	<div class="box">
		<div class="input-group">
			<label class="input-label">이메일</label> <input type="email" id="email"
				value="${user.email}" readonly style="width: 200px;" />

			<button class="edit-btn" id="editEmailBtn">수정</button>
			<button class="edit-btn" id="btnSendCode" style="display: none;">인증</button>

			<span id="emailSpinner" class="spinner" style="display: none;"></span>
		</div>

		<p id="emailStatus" class="nickname-hint" style="margin-top: 5px;"></p>

		<div class="input-group" id="emailCodeBox"
			style="display: none; margin-top: 8px;">
			<label class="input-label">인증코드</label> <input type="text"
				id="emailCode" placeholder="인증 코드를 입력하세요" />
			<button class="edit-btn" id="btnVerifyCode">확인</button>
			<span id="emailTimer"
				style="color: #cc5500; font-size: 14px; margin-left: 10px;"></span>
		</div>

		<!-- 인증 코드 결과 -->
		<p id="emailResult" class="nickname-hint" style="margin-top: 5px;"></p>
	</div>

	<!-- 전화번호 -->
	<div class="box">
		<div class="input-group">
			<label for="userTel1">전화번호</label>
			<div style="display: flex; gap: 5px;">
				<input type="text" id="userTel1" maxlength="3" style="width: 60px;"
					readonly /> <input type="text" id="userTel2" maxlength="4"
					style="width: 70px;" readonly /> <input type="text" id="userTel3"
					maxlength="4" style="width: 70px;" readonly />
			</div>
			<input type="hidden" id="userTel" name="userTel"
				value="${user.userTel}" />
			<button class="edit-btn" id="editTelBtn">수정</button>
		</div>
	</div>


	<!-- 저장 버튼 -->
	<div class="form-actions save-btn-row">
		<button id="btnSaveInfo" class="edit-btn">저장</button>
	</div>

	<!-- 공통 모달 -->
	<div id="commonModal" class="modal">
		<div class="modal-content">
			<p id="modalMessage">메시지</p>
			<button onclick="closeModal()">확인</button>
		</div>
	</div>
</div>
