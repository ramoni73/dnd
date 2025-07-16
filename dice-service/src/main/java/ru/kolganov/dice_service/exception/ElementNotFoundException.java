package ru.kolganov.dice_service.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class ElementNotFoundException extends RuntimeException {
    private final String element;

    public ElementNotFoundException(@NonNull final String element, @NonNull final String message) {
        super(message);
        this.element = element;
    }
}

