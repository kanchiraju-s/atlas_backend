package com.atlas.atlas_backend.notifications.service;

import com.atlas.atlas_backend.notifications.entity.Notification;
import com.atlas.atlas_backend.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository
            notificationRepository;

    @Override
    public List<Notification> getNotifications(
            UUID userId
    ) {
        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public Notification createNotification(
            Notification notification
    ) {
        return notificationRepository.save(
                notification
        );
    }
}