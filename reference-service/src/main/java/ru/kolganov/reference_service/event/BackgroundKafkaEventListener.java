package ru.kolganov.reference_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.kolganov.reference_service.event.model.application.BackgroundCreatedApplicationEvent;
import ru.kolganov.reference_service.event.model.application.BackgroundDeletedApplicationEvent;
import ru.kolganov.reference_service.event.model.application.BackgroundUpdatedApplicationEvent;
import ru.kolganov.reference_service.event.model.kafka.*;
import ru.kolganov.reference_service.event.service.KafkaProducerService;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BackgroundKafkaEventListener {
    private final KafkaProducerService kafkaProducerService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBackgroundCreated(BackgroundCreatedApplicationEvent event) {
        try {
            log.info("Sending BackgroundCreated event to Kafka for background='{}'", event.getModel().code());

            final BackgroundEvent kafkaEvent = new BackgroundEvent(
                    "BackgroundCreated",
                    new BackgroundEventPayload(
                            event.getModel().code(),
                            event.getModel().name(),
                            event.getModel().description(),
                            new FeatEvent(event.getModel().feat().code(), event.getModel().feat().name(), event.getModel().feat().category()),
                            event.getModel().abilities().stream().map(m -> new AbilityEvent(m.code(), m.name(), m.description())).collect(Collectors.toSet()),
                            event.getModel().skills().stream().map(m -> new SkillEvent(m.code(), m.name(), m.description())).collect(Collectors.toSet()),
                            event.getModel().equipment(),
                            event.getModel().instruments()
                    )
            );

            kafkaProducerService.sendBackgroundEvent(kafkaEvent);
        } catch (Exception e) {
            log.error("Failed to send BackgroundCreated event to Kafka for background='{}'", event.getModel().code(), e);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBackgroundDeleted(BackgroundDeletedApplicationEvent event) {
        try {
            log.info("Sending BackgroundDeleted event to Kafka for background='{}'", event.getCode());

            final BackgroundEvent kafkaEvent = new BackgroundEvent(
                    "BackgroundDeleted",
                    new BackgroundEventPayload(event.getCode())
            );

            kafkaProducerService.sendBackgroundEvent(kafkaEvent);
        } catch (Exception e) {
            log.error("Failed to send BackgroundDeleted event to Kafka for background='{}'", event.getCode(), e);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBackgroundUpdated(BackgroundUpdatedApplicationEvent event) {
        try {
            log.info("Sending BackgroundUpdated event to Kafka for background='{}'", event.getModel().code());

            final BackgroundEvent kafkaEvent = new BackgroundEvent(
                    "BackgroundUpdated",
                    new BackgroundEventPayload(
                            event.getModel().code(),
                            event.getModel().name(),
                            event.getModel().description(),
                            new FeatEvent(event.getModel().feat().code(), event.getModel().feat().name(), event.getModel().feat().category()),
                            event.getModel().abilities().stream().map(m -> new AbilityEvent(m.code(), m.name(), m.description())).collect(Collectors.toSet()),
                            event.getModel().skills().stream().map(m -> new SkillEvent(m.code(), m.name(), m.description())).collect(Collectors.toSet()),
                            event.getModel().equipment(),
                            event.getModel().instruments()
                    )
            );

            kafkaProducerService.sendBackgroundEvent(kafkaEvent);
        } catch (Exception e) {
            log.error("Failed to send BackgroundUpdated event to Kafka for background='{}'", event.getModel().code(), e);
        }
    }
}
