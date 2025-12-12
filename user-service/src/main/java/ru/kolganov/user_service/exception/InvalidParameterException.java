package ru.kolganov.user_service.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class InvalidParameterException extends RuntimeException {
    private final String parameterName;

    public InvalidParameterException(@NonNull final String parameterName, @NonNull final String message) {
        super(message);
        this.parameterName = parameterName;
    }
}