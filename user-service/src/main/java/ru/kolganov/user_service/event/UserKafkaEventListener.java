package ru.kolganov.user_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;
import ru.kolganov.user_service.event.model.UserCreatedEvent;
import ru.kolganov.user_service.event.model.UserCreatedPayload;
import ru.kolganov.user_service.event.service.KafkaProducerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserKafkaEventListener {
    private final KafkaProducerService kafkaProducerService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreated(UserCreatedApplicationEvent event) {
        try {
            log.info("Sending UserCreated event to Kafka for userId='{}'", event.getUserId());

            final UserCreatedEvent kafkaEvent = new UserCreatedEvent(
                    new UserCreatedPayload(
                            event.getUserId(),
                            event.getProvider(),
                            event.getExternalId()
                    )
            );

            kafkaProducerService.sendUserCreatedEvent(kafkaEvent);
        } catch (Exception e) {
            log.error("Failed to send UserCreated event to Kafka for userId='{}'", event.getUserId(), e);
        }
    }
}
