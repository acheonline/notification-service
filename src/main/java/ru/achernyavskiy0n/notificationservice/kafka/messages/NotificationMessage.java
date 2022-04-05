package ru.achernyavskiy0n.notificationservice.kafka.messages;

import lombok.*;

/**
 *
 */
@Setter
@Getter
@ToString
@Builder
public class NotificationMessage {
    private String username;
    private String payload;
}
