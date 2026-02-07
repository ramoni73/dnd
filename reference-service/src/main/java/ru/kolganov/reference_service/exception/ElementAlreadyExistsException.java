package ru.kolganov.reference_service.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class ElementAlreadyExistsException extends RuntimeException {
    private final String elementName;

    public ElementAlreadyExistsException(@NonNull final String elementName, @NonNull final String message) {
        super(message);
        this.elementName = null;
    }
}
