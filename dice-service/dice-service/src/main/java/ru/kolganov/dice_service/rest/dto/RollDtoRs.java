package ru.kolganov.dice_service.rest.dto;

import java.util.List;

public record RollDtoRs(
        Integer result,
        List<DiceDto> rolls,
        String error
)
{
    public RollDtoRs(Integer result, List<DiceDto> rolls) {
        this(result, rolls, null);
    }

    public RollDtoRs(String error) {
        this(null, null, error);
    }
}
