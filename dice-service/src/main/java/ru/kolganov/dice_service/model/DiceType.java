package ru.kolganov.dice_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import ru.kolganov.dice_service.exception.ElementNotFoundException;
import ru.kolganov.dice_service.exception.InvalidParameterException;

import java.util.Arrays;
import java.util.Locale;

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

    public static DiceType getByName(@Nullable final String diceType) {
        if (!StringUtils.hasText(diceType)) {
            throw new InvalidParameterException(diceType, "Dice type '%s' is invalid".formatted(diceType));
        }

        return Arrays.stream(DiceType.values())
                .filter(f -> diceType.toUpperCase(Locale.ROOT).equals(f.name()))
                .findAny()
                .orElseThrow(() -> new ElementNotFoundException(diceType, "Dice type '%s' not found".formatted(diceType)));
    }
}
