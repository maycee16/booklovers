package com.booklovers.booklovers.DTO;


 
import com.booklovers.booklovers.Entity.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    private Long clubId;

    private Long senderId;

    private String title;

    private String message;

    private NotificationType type;
}