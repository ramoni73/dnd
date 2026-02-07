package ru.kolganov.reference_service.event.model.application;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BackgroundDeletedApplicationEvent extends ApplicationEvent {
    private final String code;

    public BackgroundDeletedApplicationEvent(Object source, String code) {
        super(source);
        this.code = code;
    }
}
