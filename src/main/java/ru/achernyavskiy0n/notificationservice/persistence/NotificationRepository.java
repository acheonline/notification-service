package ru.achernyavskiy0n.notificationservice.persistence;


import ru.achernyavskiy0n.notificationservice.kafka.messages.NotificationMessage;

import java.util.List;

public interface NotificationRepository {

    void saveNotification(NotificationMessage message);
    int count();
    List<NotificationMessage> getAllNotification();
}