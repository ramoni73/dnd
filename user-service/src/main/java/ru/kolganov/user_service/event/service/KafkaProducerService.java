package ru.kolganov.user_service.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.kolganov.user_service.event.model.UserCreatedEvent;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private static final String topicName = "user-event-topic";
    private static final String topicKey = "user-event-key";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserCreatedEvent(final UserCreatedEvent event) {
        final CompletableFuture<SendResult<String, Object>> feature = kafkaTemplate.send(topicName, topicKey, event);
        feature.whenComplete((result, ex) -> {
            if (ex == null) {
                handleSuccess(result);
            } else {
                handleFailure(ex);
            }
        });
    }

    private void handleSuccess(SendResult<String, Object> result) {
        RecordMetadata metadata = result.getRecordMetadata();
        log.info("Message delivered successfully: Topic = {}, Partition = {}, Offset = {}", metadata.topic(), metadata.partition(), metadata.offset());
    }

    private void handleFailure(Throwable ex) {
        log.error("Failed to deliver message: {}", ex.getMessage());
    }
}
