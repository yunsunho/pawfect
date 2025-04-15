document.addEventListener("DOMContentLoaded", () => {
	const notiIcon = document.getElementById("notiIcon");
	const sidebar = document.getElementById("notificationSidebar");
	const closeBtn = document.getElementById("closeNotifBtn");
	const notifList = document.getElementById("notificationList");

	notiIcon?.addEventListener("click", () => {
		if (sidebar.classList.contains("open")) {
			sidebar.classList.remove("open");
		} else {
			sidebar.classList.add("open");

			// 로그인 여부 확인용 요청
			fetch("/notifications", {
				headers: { "X-Requested-With": "XMLHttpRequest" }
			})
				.then(res => {
					if (!res.ok) throw new Error("요청 실패");
					return res.json();
				})
				.then(notifs => {
					notifList.innerHTML = "";

					if (notifs.length === 0) {
						notifList.innerHTML = `<p class="empty-msg">알림이 없습니다.</p>`;
					} else {
						notifs.forEach(noti => {
							const item = document.createElement("div");
							item.className = "noti-item" + (noti.notiReadStatus ? " read" : "");

							item.innerHTML = `
								<div class="noti-time">${noti.formattedTime}</div>
								<div>${noti.notiContent}
									<a href="${noti.notiUrl}" class="noti-link" data-id="${noti.notificationId}">→</a>
								</div>
							`;

							notifList.appendChild(item);
						});

						// 클릭 시 읽음 처리 후 이동
						document.querySelectorAll(".noti-link").forEach(link => {
							link.addEventListener("click", e => {
								e.preventDefault(); // 기본 이동 막기

								const id = e.target.dataset.id;
								const url = e.target.getAttribute("href");

								fetch(`/notifications/read/${id}`, { method: "POST" })
									.then(() => {
										window.location.href = url; // 완료 후 이동
									});
							});
						});
					}
				})
				.catch(() => {
					notifList.innerHTML = `<p class="empty-msg">알림이 없습니다.</p>`;
				});
		}
	});

	closeBtn?.addEventListener("click", () => {
		sidebar.classList.remove("open");
	});
});
