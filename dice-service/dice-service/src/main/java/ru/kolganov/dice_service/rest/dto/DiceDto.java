package ru.kolganov.dice_service.rest.dto;

import ru.kolganov.dice_service.model.DiceType;

public record DiceDto(
        DiceType diceType,
        Integer count
) {
}
