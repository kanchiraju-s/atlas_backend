package com.atlas.atlas_backend.notifications.service;


import com.atlas.atlas_backend.notifications.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    List<Notification> getNotifications(UUID userId);

    Notification createNotification(
            Notification notification
    );
}