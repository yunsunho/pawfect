package com.example.Pawfect.service;

import com.example.Pawfect.dto.NotificationDto;
import com.example.Pawfect.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationMapper notificationMapper;

	public void sendNotification(NotificationDto dto) {
		notificationMapper.insertNotification(dto);
	}

	public List<NotificationDto> getUserNotifications(String userId) {
		List<NotificationDto> list = notificationMapper.getNotificationsByReceiver(userId);

		for (NotificationDto dto : list) {
			dto.setFormattedTime(formatTime(dto.getNotiRegdate()));
		}
		return list;
	}

	public void markNotificationAsRead(int notificationId) {
		notificationMapper.markAsRead(notificationId);
	}

	private String formatTime(Timestamp regDate) {
		LocalDateTime notiTime = regDate.toLocalDateTime();
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(notiTime, now);

		if (duration.toMinutes() < 1) {
			return "방금 전";
		} else if (duration.toMinutes() < 60) {
			return duration.toMinutes() + "분 전";
		} else if (duration.toHours() < 24) {
			return duration.toHours() + "시간 전";
		} else {
			return notiTime.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
		}
	}	
}
