package ru.kolganov.dice_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kolganov.dice_service.model.*;

import java.util.Collections;
import java.util.Comparator;
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
                .flatMap(m -> advantageDisadvantageRoll(m.diceType(), m.count(), m.rollType()).stream())
                .toList();

        final int totalResult = rollResults.stream()
                .mapToInt(DiceModelResult::rollResult)
                .sum();

        log.info("Finished getRolls. Total result: {}, Roll results: {}", totalResult, rollResults);
        return new DiceModelRs(totalResult, rollResults);
    }

    private List<DiceModelResult> advantageDisadvantageRoll(final DiceType diceType, final Integer count, final RollType rollType) {
        log.debug("Processing advantageDisadvantageRoll with diceType: {}, count: {}, rollType: {}", diceType, count, rollType);

        if ((rollType == RollType.ADVANTAGE || rollType == RollType.DISADVANTAGE)) {
            if (count != 2) {
                log.warn("Invalid count for advantage/disadvantage roll. Expected 2, but got: {}", count);
                throw new IllegalArgumentException("Count must be exactly 2 for advantage/disadvantage rolls");
            }

            List<DiceModelResult> rolls = rollDice(diceType, 2);

            DiceModelResult selectedResult = rollType == RollType.ADVANTAGE
                    ? Collections.max(rolls, Comparator.comparingInt(DiceModelResult::rollResult))
                    : Collections.min(rolls, Comparator.comparingInt(DiceModelResult::rollResult));

            log.debug("Selected result for {} roll: {}", rollType, selectedResult);
            return List.of(selectedResult);
        }

        return rollDice(diceType, count);
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
