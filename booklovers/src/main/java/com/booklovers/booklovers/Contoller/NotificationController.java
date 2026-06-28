package com.booklovers.booklovers.Contoller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.NotificationRequest;
import com.booklovers.booklovers.DTO.NotificationResponse;
import com.booklovers.booklovers.Services.NotificationService;

@RestController
@RequestMapping("/api/open/notifications")
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>>
    createNotification(
            @RequestBody NotificationRequest request) {

        NotificationResponse response =
                notificationService.createNotification(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notification created successfully.",
                        response));
    }

    @GetMapping("/{clubId}/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>>
    getNotifications(
            @PathVariable Long clubId,
            @PathVariable Long userId) {

        List<NotificationResponse> notifications =
                notificationService
                .getClubNotifications(clubId, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Notifications retrieved successfully.",
                        notifications));
    }
}