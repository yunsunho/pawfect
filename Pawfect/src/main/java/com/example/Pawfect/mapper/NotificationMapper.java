package com.example.Pawfect.mapper;

import com.example.Pawfect.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
	int insertNotification(NotificationDto notification);

	List<NotificationDto> getNotificationsByReceiver(@Param("receiverId") String receiverId);

	int markAsRead(@Param("notificationId") int notificationId);
}
