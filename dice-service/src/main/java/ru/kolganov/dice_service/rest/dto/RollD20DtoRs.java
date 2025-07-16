package ru.kolganov.dice_service.rest.dto;

import java.util.List;

public record RollD20DtoRs(
        Integer result,
        List<DiceDto> rolls,
        List<DiceDto> typedRolls,
        String rollType,
        String error
) {
    public RollD20DtoRs(Integer result, List<DiceDto> rolls, List<DiceDto> typedRolls, String rollType) {
        this(result, rolls, typedRolls, rollType, null);
    }

    public RollD20DtoRs(String error) {
        this(null, null, null, null, error);
    }
}
