package ru.achernyavskiy0n.notificationservice.kafka;

import ru.achernyavskiy0n.notificationservice.kafka.messages.NotificationMessage;

/**
 */
public interface TransportConsumer {
    void createAccount(NotificationMessage message);
}
