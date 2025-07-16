package ru.kolganov.dice_service.model;

import java.util.List;

public record RollD20ModelRq(
        RollType rollType,
        DiceType diceType,
        List<DiceModel> diceModels
) {
}
