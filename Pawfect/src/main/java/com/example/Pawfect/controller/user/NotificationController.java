package com.example.Pawfect.controller.user;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.NotificationDto;
import com.example.Pawfect.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping
	public List<NotificationDto> getNotifications(@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (userDetails == null)
			return List.of(); // 비로그인 사용자용

		String userId = userDetails.getUser().getUserId();
		return notificationService.getUserNotifications(userId);
	}

	@PostMapping("/read/{id}")
	public void markAsRead(@PathVariable("id") int id) {
		notificationService.markNotificationAsRead(id);
	}
}
