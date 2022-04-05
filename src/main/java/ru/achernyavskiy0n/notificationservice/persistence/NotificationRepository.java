package ru.achernyavskiy0n.notificationservice.persistence;


import ru.achernyavskiy0n.notificationservice.kafka.messages.NotificationMessage;

public interface NotificationRepository {

    void saveNotification(NotificationMessage message);
    int count();
}