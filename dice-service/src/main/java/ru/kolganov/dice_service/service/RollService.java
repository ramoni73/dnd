package ru.kolganov.dice_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kolganov.dice_service.model.DiceModelResult;
import ru.kolganov.dice_service.model.DiceModelRq;
import ru.kolganov.dice_service.model.DiceModelRs;
import ru.kolganov.dice_service.model.DiceType;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
@Service
public class RollService {
    private final Random random = new Random();

    public DiceModelRs getRolls(final List<DiceModelRq> diceModelRqs) {
        log.info("Starting getRolls with requests: {}", diceModelRqs);

        if (diceModelRqs == null || diceModelRqs.isEmpty()) {
            log.warn("Received null or empty diceModelRqs list");
            throw new IllegalArgumentException("DiceModelRqs list cannot be null or empty");
        }

        final List<DiceModelResult> rollResults = diceModelRqs.stream()
                .flatMap(m -> rollDice(m.diceType(), m.count()).stream())
                .toList();

        final int totalResult = rollResults.stream()
                .mapToInt(DiceModelResult::rollResult)
                .sum();

        log.info("Finished getRolls. Total result: {}, Roll results: {}", totalResult, rollResults);
        return new DiceModelRs(totalResult, rollResults);
    }

    private List<DiceModelResult> rollDice(final DiceType diceType, final Integer count) {
        if (count <= 0) {
            log.warn("Invalid count value for diceType {}: {}", diceType, count);
            throw new IllegalArgumentException("Count must be greater than zero");
        }

        log.debug("Rolling {} dice of type {}", count, diceType);
        return IntStream.range(0, count)
                .mapToObj(i -> new DiceModelResult(diceType, getRandom(diceType)))
                .toList();
    }

    private int getRandom(final DiceType diceType) {
        int result = random.nextInt(diceType.getMin(), diceType.getMax() + 1);
        log.debug("Generated random number for diceType {}: {}", diceType, result);
        return result;
    }
}
