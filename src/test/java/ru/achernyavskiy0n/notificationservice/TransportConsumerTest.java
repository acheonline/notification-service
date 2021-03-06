package ru.achernyavskiy0n.notificationservice;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.achernyavskiy0n.notificationservice.kafka.messages.NotificationMessage;
import ru.achernyavskiy0n.notificationservice.persistence.InMemoryNotificationRepository;
import ru.achernyavskiy0n.notificationservice.persistence.NotificationRepository;

import java.time.Duration;
import java.util.Map;


@DirtiesContext
@SpringBootTest(classes = {InMemoryNotificationRepository.class})
@ExtendWith(SpringExtension.class)
@EmbeddedKafka(
        partitions = 1,
        topics = {"${notification-service.transport.topics.notification.notify}"},
        bootstrapServersProperty = "${spring.kafka.producer.bootstrap-servers}"
)
@AutoConfigureDataJpa
class TransportConsumerTest {

    @Value("${notification-service.transport.topics.notification.notify}")
    private String notifyTopic;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private NotificationRepository repository;

    @Test
    void shouldReceiveAndSaveNotification() {
        Producer<String, NotificationMessage> producer = createProducer();
        Assertions.assertEquals(0, repository.count());

        var message = NotificationMessage.builder().payload("payload").username("username").build();
        producer.send(new ProducerRecord<>(notifyTopic, message.getUsername(), message));

        Awaitility.await()
                .atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> Assertions.assertEquals(1, repository.count()));
    }

    private Producer<String, NotificationMessage> createProducer() {
        Map<String, Object> producerProps =
                KafkaTestUtils.producerProps(embeddedKafkaBroker.getBrokersAsString());

        return new DefaultKafkaProducerFactory<>(
                producerProps, new StringSerializer(), new JsonSerializer<NotificationMessage>())
                .createProducer();
    }
}