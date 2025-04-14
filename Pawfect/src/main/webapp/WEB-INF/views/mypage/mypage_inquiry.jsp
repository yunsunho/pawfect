<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="inquiry-tab">
	<div class="inquiry-header">
		<h2 class="inquiry-title">1:1 문의 내역</h2>
		<button id="btnWriteInquiry" class="edit-btn" type="button"
			style="background-color: #3d4fa1; color: white; font-weight: bold;">
			문의 작성</button>
	</div>

	<table class="inquiry-table">
		<colgroup>
			<col style="width: 7%;" />
			<col style="width: 63%;" />
			<col style="width: 15%;" />
			<col style="width: 10%;" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>처리 상태</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="inquiry" items="${inquiries}" varStatus="status">
				<tr class="inquiry-summary">
					<td>${fn:length(inquiries) - status.index}</td>
					<td class="clickable-title">${inquiry.inquiryTitle}</td>
					<td><fmt:formatDate value="${inquiry.inquiryRegdate}"
							pattern="yyyy-MM-dd" /></td>
					<td><c:choose>
							<c:when test="${inquiry.inquiryStatus}">
								<span class="status-complete">답변 완료</span>
							</c:when>
							<c:otherwise>
								<span class="status-pending">답변 대기</span>
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr class="inquiry-detail" style="display: none;">
					<td colspan="4">
						<div style="margin-bottom: 10px;">
							<strong style="color: green;">[문의내용] </strong> ${inquiry.inquiryContent}
						</div> <c:if test="${inquiry.inquiryReply != null}">
							<div style="margin-bottom: 10px;">
								<strong style="color: green;">[답변] </strong> ${inquiry.inquiryReply}
							</div>
						</c:if> <c:if test="${inquiry.inquiryReply == null}">
							<div style="text-align: right;">
								<button class="deleteBtn edit-btn"
									data-id="${inquiry.inquiryId}">삭제</button>
							</div>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<!-- 문의 작성 모달 -->
<div id="inquiryModal" class="modal" style="display: none;">
	<div class="modal-content">
		<h3>문의 작성</h3>
		<div>
			<input type="text" id="inquiryTitle" placeholder="제목을 입력하세요"
				style="width: 100%; margin-bottom: 10px;" />
			<textarea id="inquiryContent" placeholder="문의 내용을 입력하세요" rows="5"
				style="width: 100%;"></textarea>
		</div>
		<div style="margin-top: 10px;">
			<button id="btnSubmitInquiry" class="edit-btn">작성</button>
			<button onclick="closeInquiryModal()" class="edit-btn">취소</button>
		</div>
	</div>
</div>

<script>
	// 제목 클릭 시 상세 토글
	document.querySelectorAll(".inquiry-summary").forEach(row => {
		row.addEventListener("click", function () {
			const detailRow = this.nextElementSibling;
			if (detailRow && detailRow.classList.contains("inquiry-detail")) {
				detailRow.style.display = detailRow.style.display === "none" ? "table-row" : "none";
			}
		});
	});

	// 삭제 버튼 클릭
	document.querySelectorAll(".deleteBtn").forEach(btn => {
		btn.addEventListener("click", function (e) {
			e.stopPropagation();
			const id = this.dataset.id;

			showConfirmModal("정말 삭제하시겠습니까?", () => {
				fetch("/mypage/inquiry/delete", {
					method: "POST",
					headers: { "Content-Type": "application/x-www-form-urlencoded" },
					body: new URLSearchParams({ inquiryId: id })
				})
					.then(res => res.text())
					.then(result => {
						if (result === "success") {
							showModalWithCallback("문의글이 삭제되었습니다.", () => {
								loadTab("inquiry");
							});
						} else {
							showModal("문의글 삭제에 실패했습니다.");
						}
					})
					.catch(() => {
						showModal("서버 오류가 발생했습니다.");
					});
			});
		});
	});

</script>
