package ru.kolganov.dice_service.model;

public record DiceModelRq(
        DiceType diceType,
        Integer count
) {
}
