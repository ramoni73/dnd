package ru.kolganov.dice_service.model;

public record DiceModelResult(
        DiceType diceType,
        Integer rollResult
) {
}
