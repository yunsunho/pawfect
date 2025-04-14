<%@ page contentType="text/html;charset=UTF-8"%>

<div class="profile-tab center-box">
	<!-- 제목 -->
	<h2 class="password-title">비밀번호 변경</h2>
	<p style="text-align: left; margin-bottom: 25px;">현재 비밀번호가 일치하는 경우
		새 비밀번호로 변경할 수 있습니다.</p>

	<!-- 비밀번호 입력 박스 -->
	<div class="box">
		<div class="input-group">
			<label for="currentPwd" class="input-label">현재 비밀번호</label> <input
				type="password" id="currentPwd" placeholder="현재 비밀번호 입력" />
		</div>

		<div class="input-group">
			<label for="newPwd" class="input-label">새 비밀번호</label> <input
				type="password" id="newPwd" placeholder="새 비밀번호 입력" />
			<ul class="check-list" id="pwd-check-list" style="display: none;">
				<li id="pwd-condition-length" class="invalid">8자 이상</li>
				<li id="pwd-condition-pattern" class="invalid">영문, 숫자, 특수문자 포함</li>
			</ul>
		</div>
		<div class="input-group">
			<label for="newPwdCheck" class="input-label">새 비밀번호 확인</label> <input
				type="password" id="newPwdCheck" placeholder="비밀번호 다시 입력" />
			<ul class="check-list" id="pwd-match-check-list"
				style="display: none;">
				<li id="pwd-match-condition" class="invalid">비밀번호 일치</li>
			</ul>
		</div>
	</div>

	<!-- 버튼 -->
	<div class="form-actions save-btn-row"
		style="margin-top: 30px; display: flex; justify-content: center;">
		<button class="edit-btn" id="btnSavePwd" type="button"
			style="background-color: #3d4fa1; color: white; font-weight: bold;">
			변경하기</button>
	</div>


	<!-- 공통 모달 -->
	<div id="commonModal" class="modal" style="display: none;">
		<div class="modal-content">
			<p id="modalMessage"></p>
			<div class="modal-actions">
				<button onclick="closeModal()">확인</button>
			</div>
		</div>
	</div>
</div>
