package ru.achernyavskiy0n.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.achernyavskiy0n.notificationservice.persistence.InMemoryNotificationRepository;


/**
 *
 */
@RestController
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final InMemoryNotificationRepository repository;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse returnAccountNo() {
        var notifications = repository.getAllNotification();
        return ApiResponse.builder()
                .message(notifications.toString())
                .status(HttpStatus.OK.value())
                .success(true)
                .build();
    }
}
