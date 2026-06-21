package com.atlas.atlas_backend.notifications.dto;

import com.atlas.atlas_backend.notifications.entity.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class NotificationResponse {

    private UUID id;
    private NotificationType type;
    private String message;
    private boolean isRead;
    private Instant createdAt;
}