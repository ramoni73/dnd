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
public enum RollType {
    NORMAL,
    ADVANTAGE,
    DISADVANTAGE
    ;

    public static RollType getByName(@Nullable final String rollType) {
        if (!StringUtils.hasText(rollType)) {
            throw new InvalidParameterException(rollType, "Roll type '%s' is invalid".formatted(rollType));
        }

        return Arrays.stream(RollType.values())
                .filter(f -> rollType.toUpperCase(Locale.ROOT).equals(f.name()))
                .findAny()
                .orElseThrow(() -> new ElementNotFoundException(rollType, "Roll type '%s' not found".formatted(rollType)));
    }
}
