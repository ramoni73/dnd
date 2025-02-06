package ru.kolganov.dice_service.rest.dto;

import java.util.List;

public record RollDtoRq(
        List<DiceDto> diceRolls
) {
}
