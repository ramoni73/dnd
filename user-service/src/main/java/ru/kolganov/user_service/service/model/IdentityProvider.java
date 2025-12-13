package ru.kolganov.user_service.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import ru.kolganov.user_service.exception.ElementNotFoundException;
import ru.kolganov.user_service.exception.InvalidParameterException;

import java.util.Arrays;
import java.util.Locale;

@Getter
@AllArgsConstructor
public enum IdentityProvider {
    GOOGLE,
    ;

    public static IdentityProvider getByName(@Nullable final String identityProvider) {
        if (!StringUtils.hasText(identityProvider)) {
            throw new InvalidParameterException(identityProvider, "Identity provider '%s' is invalid".formatted(identityProvider));
        }

        return Arrays.stream(IdentityProvider.values())
                .filter(f -> identityProvider.toUpperCase(Locale.ROOT).equals(f.name()))
                .findAny()
                .orElseThrow(() -> new ElementNotFoundException(identityProvider, "Identity provider '%s' not found".formatted(identityProvider)));
    }
}
