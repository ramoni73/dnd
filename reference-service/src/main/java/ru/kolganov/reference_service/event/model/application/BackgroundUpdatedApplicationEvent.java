package ru.kolganov.reference_service.event.model.application;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.kolganov.reference_service.service.model.BackgroundModel;

@Getter
public class BackgroundUpdatedApplicationEvent extends ApplicationEvent {
    private final BackgroundModel oldModel;
    private final BackgroundModel newModel;

    public BackgroundUpdatedApplicationEvent(Object source, BackgroundModel oldModel, BackgroundModel newModel) {
        super(source);
        this.oldModel = oldModel;
        this.newModel = newModel;
    }
}
