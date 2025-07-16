package ru.kolganov.dice_service.exception;

import lombok.Getter;

@Getter
public class InvalidParameterException extends RuntimeException {
    private final String parameterName;

    public InvalidParameterException(String parameterName, String message) {
        super(message);
        this.parameterName = parameterName;
    }
}