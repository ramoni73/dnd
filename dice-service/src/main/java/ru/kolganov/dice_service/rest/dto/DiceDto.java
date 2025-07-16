package ru.kolganov.dice_service.rest.dto;

public record DiceDto(
        String diceType,
        Integer count
) {
}
