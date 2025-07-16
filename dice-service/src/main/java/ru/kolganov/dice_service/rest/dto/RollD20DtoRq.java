package ru.kolganov.dice_service.rest.dto;

import java.util.List;

public record RollD20DtoRq(
        List<DiceDto> diceRolls
) {
}
