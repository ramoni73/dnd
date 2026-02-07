package ru.kolganov.reference_service.event.model.application;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.kolganov.reference_service.service.model.BackgroundModel;

@Getter
public class BackgroundCreatedApplicationEvent extends ApplicationEvent {
    private final BackgroundModel model;

    public BackgroundCreatedApplicationEvent(Object source, BackgroundModel model) {
        super(source);
        this.model = model;
    }
}
