<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="inquiry-tab">
	<div class="inquiry-header">
		<h2 class="inquiry-title">1:1 문의 내역</h2>
		<button id="btnWriteInquiry" class="edit-btn write-btn" type="button">문의
			작성</button>
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
					<td>${totalCount - ((currentPage - 1) * pageSize + status.index)}</td>
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
							<strong style="color: green;">[문의내용]</strong>
							<pre style="white-space: pre-wrap; margin: 4px 0 0;">${inquiry.inquiryContent}</pre>
						</div> <c:if test="${inquiry.inquiryReply != null}">
							<div style="margin-bottom: 10px;">
								<strong style="color: green;">[답변]</strong>
								<pre style="white-space: pre-wrap; margin: 4px 0 0;">${inquiry.inquiryReply}</pre>
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

	<%-- 페이징 처리 --%>
	<%
	int currentPageVal = (request.getAttribute("currentPage") != null) ? (Integer) request.getAttribute("currentPage") : 1;
	int totalPagesVal = (request.getAttribute("totalPages") != null) ? (Integer) request.getAttribute("totalPages") : 1;

	int pageBlockSize = 5;
	int startPage = ((currentPageVal - 1) / pageBlockSize) * pageBlockSize + 1;
	int endPage = Math.min(startPage + pageBlockSize - 1, totalPagesVal);
	%>

	<c:if test="${totalPages > 1}">
		<div class="pagination" style="margin-top: 20px; text-align: center;">
			<c:if test="${currentPage > 1}">
				<button class="page-btn" data-page="1">«</button>
				<button class="page-btn" data-page="${currentPage - 1}">‹</button>
			</c:if>

			<c:forEach begin="<%=startPage%>" end="<%=endPage%>" var="i">
				<c:set var="iVal" value="${i}" />
				<button class="page-btn ${iVal == currentPage ? 'active' : ''}"
					data-page="${iVal}">${iVal}</button>
			</c:forEach>

			<c:if test="${currentPage < totalPages}">
				<button class="page-btn" data-page="${currentPage + 1}">›</button>
				<button class="page-btn" data-page="${totalPages}">»</button>
			</c:if>
		</div>
	</c:if>


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
