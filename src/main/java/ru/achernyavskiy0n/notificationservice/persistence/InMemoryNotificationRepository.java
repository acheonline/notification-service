package ru.achernyavskiy0n.notificationservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.achernyavskiy0n.notificationservice.kafka.messages.NotificationMessage;

import java.util.*;

@Repository
@Slf4j
public class InMemoryNotificationRepository implements NotificationRepository {

    private final List<NotificationMessage> notificationMessages;

    public InMemoryNotificationRepository() {
        this.notificationMessages = new ArrayList<>();
    }

    @Override
    public void saveNotification(NotificationMessage message) {
        notificationMessages.add(message);
        log.info("Сообщение {} от пользователя '{}' добавлено в хранилище", message.getPayload(), message.getUsername());
    }

    @Override
    public int count() {
        return notificationMessages.size();
    }
}
