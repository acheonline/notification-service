package ru.achernyavskiy0n.notificationservice.kafka;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.achernyavskiy0n.notificationservice.kafka.messages.NotificationMessage;

import java.util.HashMap;
import java.util.Map;

/**
 */
@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value("${notification-service.transport.topics.notification.notify}")
    private String newsTopic;

    @Bean("newsConsumerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, NotificationMessage>
    newsKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, NotificationMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(clientIdReceiver());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, NotificationMessage> clientIdReceiver() {
        return createConsumerFactory(NotificationMessage.class, newsTopic);
    }

    @SneakyThrows
    private <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> valueType, String topic) {
        Map<String, Object> props = new HashMap<>();
        var consumer = kafkaProperties.getConsumer();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumer.getBootstrapServers());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, consumer.getClientId() + "-" + topic);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.getAutoOffsetReset());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, valueType);

        return new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), new JsonDeserializer<>(valueType));
    }
}
