package ru.kolganov.dice_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolganov.dice_service.event.model.DiceRollEvent;
import ru.kolganov.dice_service.event.model.DiceRollPayload;
import ru.kolganov.dice_service.event.producer.KafkaProducerService;
import ru.kolganov.dice_service.model.DiceModelRq;
import ru.kolganov.dice_service.model.DiceModelRs;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiceRollService {
    private final RollService rollService;
    private final KafkaProducerService kafkaProducerService;

    public DiceModelRs getRolls(final List<DiceModelRq> diceModelRqs) {
        final var diceModelRs = rollService.getRolls(diceModelRqs);
        kafkaProducerService.sendDiceRollEvent(new DiceRollEvent(new DiceRollPayload(diceModelRs)));

        return diceModelRs;
    }
}
