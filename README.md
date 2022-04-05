


| #   | Имя параметра                       | По умолчанию            | Обязательность | Описание                                        |
|-----|-------------------------------------|-------------------------|:--------------:|-------------------------------------------------|
| 1   | NOTIFICATION_TOPIC                | `notify`                |      Yes       | Топик, откуда приходит сообщение с нотификацией |
| 2   | KAFKA_CLIENT_ID                     | `notification-service`       |       No       | client.id kafka consumer                        |
| 3   | SERVER_PORT                         | `8080`                  |      Yes       | Порт приложения                                 |
| 4   | KAFKA_BOOTSTRAP_SERVERS             | `localhost:9092`        |      Yes       | Хосты Kafka broker                              |
| 5   | KAFKA_PROTOCOL                      | `PLAINTEXT`             |       No       | Соединение с kafka без шифрования               |
