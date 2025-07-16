package ru.kolganov.dice_service.model;

public record DiceModel(
        DiceType diceType,
        Integer count
) {
}
