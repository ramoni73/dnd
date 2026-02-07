package ru.kolganov.reference_service.exception;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Set;

@Getter
public class ElementsNotFoundException extends RuntimeException {
    private final Set<String> elementsName;

    public ElementsNotFoundException(@NonNull final Set<String> elementsName, @NonNull final String message) {
        super(message);
        this.elementsName = elementsName;
    }
}
