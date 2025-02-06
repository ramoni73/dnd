package ru.kolganov.dice_service.service;

import org.springframework.stereotype.Service;
import ru.kolganov.dice_service.model.DiceModelResult;
import ru.kolganov.dice_service.model.DiceModelRq;
import ru.kolganov.dice_service.model.DiceModelRs;
import ru.kolganov.dice_service.model.DiceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class RollService {
    public DiceModelRs getRolls(final List<DiceModelRq> diceModelRqs) {
        Integer result = 0;
        final List<DiceModelResult> rollResults = diceModelRqs.stream()
                .map(m -> rollDice(m.diceType(), m.count()))
                .flatMap(Collection::stream)
                .toList();

        for (DiceModelResult diceModelResult : rollResults) {
            result += diceModelResult.rollResult();
        }

        return new DiceModelRs(result, rollResults);
    }

    private List<DiceModelResult> rollDice(final DiceType diceType, final Integer count) {
        final List<DiceModelResult> diceResults = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            final var diceRoll = getRandom(diceType);
            diceResults.add(new DiceModelResult(diceType, diceRoll));
        }

        return diceResults;
    }

    private int getRandom(final DiceType diceType) {
        final Random random = new Random();
        return random.ints(diceType.getMin(), diceType.getMax()).findFirst().getAsInt();
    }
}
