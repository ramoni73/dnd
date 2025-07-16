package ru.kolganov.dice_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.kolganov.dice_service.event.model.DiceRollEvent;
import ru.kolganov.dice_service.event.model.DiceRollPayload;
import ru.kolganov.dice_service.event.producer.KafkaProducerService;
import ru.kolganov.dice_service.model.DiceModel;
import ru.kolganov.dice_service.model.DiceModelRs;
import ru.kolganov.dice_service.model.RollD20ModelRq;
import ru.kolganov.dice_service.model.RollD20ModelRs;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiceRollService {
    private final RollService rollService;
    private final KafkaProducerService kafkaProducerService;

    public DiceModelRs getRolls(@NonNull final List<DiceModel> diceModels) {
        final var diceModelRs = rollService.getRolls(diceModels);
        kafkaProducerService.sendDiceRollEvent(new DiceRollEvent(new DiceRollPayload(diceModelRs)));

        return diceModelRs;
    }

    public RollD20ModelRs getD20Rolls(@NonNull final RollD20ModelRq rollD20ModelRqs) {
        final var diceModelRs = rollService.getD20Rolls(rollD20ModelRqs);
        kafkaProducerService.sendDiceRollEvent(new DiceRollEvent(new DiceRollPayload(diceModelRs)));

        return diceModelRs;
    }
}
