package com.booklovers.booklovers.DTO;
 
import java.time.LocalDateTime;

import com.booklovers.booklovers.Entity.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

    private Long notificationId;

    private String title;

    private String message;

    private NotificationType type;

    private LocalDateTime createdAt;

    private Long senderId;

    private String senderName;

    private String senderEmail;
}