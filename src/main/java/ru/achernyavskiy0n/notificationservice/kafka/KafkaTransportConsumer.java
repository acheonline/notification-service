package ru.achernyavskiy0n.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.achernyavskiy0n.notificationservice.kafka.messages.NotificationMessage;
import ru.achernyavskiy0n.notificationservice.persistence.InMemoryNotificationRepository;

/**
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTransportConsumer implements TransportConsumer {

    @Autowired
    private InMemoryNotificationRepository repository;

    @KafkaListener(
            topics = {"${notification-service.transport.topics.notification.notify}"},
            containerFactory = "notificationConsumerFactory")
    public void createAccount(@NonNull NotificationMessage message) {
        repository.saveNotification(message);
    }
}
