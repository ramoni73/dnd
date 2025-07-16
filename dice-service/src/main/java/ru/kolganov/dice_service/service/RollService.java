package ru.kolganov.dice_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kolganov.dice_service.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
@Service
public class RollService {
    private final Random random = new Random();

    public DiceModelRs getRolls(final List<DiceModel> diceModels) {
        log.info("Starting getRolls with requests: {}", diceModels);

        if (diceModels == null || diceModels.isEmpty()) {
            log.warn("Received null or empty diceModelRqs list");
            throw new IllegalArgumentException("DiceModelRqs list cannot be null or empty");
        }

        final List<DiceModelResult> rollResults = diceModels.stream()
                .flatMap(m -> rollDice(m.diceType(), m.count()).stream())
                .toList();

        final int totalResult = rollResults.stream()
                .mapToInt(DiceModelResult::rollResult)
                .sum();

        log.info("Finished getRolls. Total result: {}, Roll results: {}", totalResult, rollResults);
        return new DiceModelRs(totalResult, rollResults);
    }

    public RollD20ModelRs getD20Rolls(final RollD20ModelRq d20ModelRq) {
        if (d20ModelRq == null) {
            throw new IllegalArgumentException("RollD20ModelRq cannot be null");
        }

        int totalResult = 0;

        List<DiceModelResult> typedRollResults = new ArrayList<>();

        if (!RollType.NORMAL.equals(d20ModelRq.rollType())) {
            int roll1 = getRandom(d20ModelRq.diceType());
            int roll2 = getRandom(d20ModelRq.diceType());

            totalResult += d20ModelRq.rollType() == RollType.ADVANTAGE
                    ? Math.max(roll1, roll2)
                    : Math.min(roll1, roll2);

            typedRollResults.add(new DiceModelResult(d20ModelRq.diceType(), roll1));
            typedRollResults.add(new DiceModelResult(d20ModelRq.diceType(), roll2));
        } else {
            typedRollResults = rollDice(d20ModelRq.diceType(), 1);
            totalResult += typedRollResults.stream()
                    .mapToInt(DiceModelResult::rollResult)
                    .sum();
        }

        final List<DiceModelResult> rollResults = d20ModelRq.diceModels().stream()
                .flatMap(m -> rollDice(m.diceType(), m.count()).stream())
                .toList();

        totalResult += rollResults.stream()
                .mapToInt(DiceModelResult::rollResult)
                .sum();

        return new RollD20ModelRs(
                totalResult,
                rollResults,
                typedRollResults,
                d20ModelRq.rollType()
        );
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
