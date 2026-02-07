package ru.kolganov.reference_service.entity.enums;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import ru.kolganov.reference_service.exception.ElementNotFoundException;
import ru.kolganov.reference_service.exception.InvalidParameterException;

import java.util.Arrays;
import java.util.Locale;

public enum AbilityCode {
    STR, DEX, CON, INT, WIS, CHA;

    public static AbilityCode getByName(@Nullable final String abilityCode) {
        if (!StringUtils.hasText(abilityCode)) {
            throw new InvalidParameterException(abilityCode, "Ability code '%s' is invalid".formatted(abilityCode));
        }

        return Arrays.stream(AbilityCode.values())
                .filter(f -> abilityCode.toUpperCase(Locale.ROOT).equals(f.name()))
                .findAny()
                .orElseThrow(() -> new ElementNotFoundException(abilityCode, "Ability code '%s' not found".formatted(abilityCode)));
    }
}
