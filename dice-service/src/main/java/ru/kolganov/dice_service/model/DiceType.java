package ru.kolganov.dice_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum DiceType {
    D2(1, 2),
    D4(1, 4),
    D6(1, 6),
    D8(1, 8),
    D10(1, 10),
    D100(1, 100),
    D12(1, 12),
    D20(1, 20);

    private final int min;
    private final int max;
}
