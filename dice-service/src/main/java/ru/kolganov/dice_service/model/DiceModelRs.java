package ru.kolganov.dice_service.model;

import java.util.List;

public record DiceModelRs(
        Integer result,
        List<DiceModelResult> diceResults
) implements RollResponse
{
}
