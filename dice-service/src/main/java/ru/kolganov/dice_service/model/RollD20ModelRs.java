package ru.kolganov.dice_service.model;

import java.util.List;

public record RollD20ModelRs(
        Integer result,
        List<DiceModelResult> diceResults,
        List<DiceModelResult> typedDiceResults,
        RollType rollType
) implements RollResponse
{
}
