package com.atlas.atlas_backend.notifications.controller;

import com.atlas.atlas_backend.common.dto.ApiResponse;
import com.atlas.atlas_backend.notifications.dto.NotificationResponse;
import com.atlas.atlas_backend.notifications.entity.Notification;
import com.atlas.atlas_backend.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService
            notificationService;

    @GetMapping
    public ApiResponse<List<NotificationResponse>>
    getNotifications() {

        UUID userId =
                UUID.fromString(
                        "11111111-1111-1111-1111-111111111111"
                );

        List<NotificationResponse> response =
                notificationService
                        .getNotifications(userId)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ApiResponse
                .<List<NotificationResponse>>
                        builder()
                .success(true)
                .message(
                        "Notifications fetched successfully"
                )
                .data(response)
                .build();
    }

    private NotificationResponse
    toResponse(
            Notification notification
    ) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .message(
                        notification.getMessage()
                )
                .isRead(
                        notification.isRead()
                )
                .createdAt(
                        notification.getCreatedAt()
                )
                .build();
    }
}