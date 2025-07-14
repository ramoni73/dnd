package ru.kolganov.dice_service.rest.dto;

import ru.kolganov.dice_service.model.DiceType;
import ru.kolganov.dice_service.model.RollType;

public record DiceDto(
        DiceType diceType,
        Integer count,
        RollType rollType
) {
    public DiceDto(DiceType diceType, Integer count) {
        this(diceType, count, null);
    }
}
