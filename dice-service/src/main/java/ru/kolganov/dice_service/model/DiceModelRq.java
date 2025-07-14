package ru.kolganov.dice_service.model;

public record DiceModelRq(
        DiceType diceType,
        Integer count,
        RollType rollType
) {
    public DiceModelRq(DiceType diceType, Integer count) {
        this(diceType, count, null);
    }
}
