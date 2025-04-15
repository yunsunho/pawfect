package com.example.Pawfect.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {
    private int notificationId;
    private String senderId;
    private String receiverId;
    private String notiType;
    private String notiContent;
    private String notiUrl;
    private boolean notiReadStatus;
    private Timestamp notiRegdate;
    private String formattedTime;
}
