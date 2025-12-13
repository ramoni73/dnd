package ru.kolganov.user_service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.kolganov.user_service.service.model.IdentityProvider;

import java.util.UUID;

@Getter
public class UserCreatedApplicationEvent extends ApplicationEvent {
    private final UUID userId;
    private final IdentityProvider provider;
    private final String externalId;

    public UserCreatedApplicationEvent(
            Object source,
            UUID userId,
            IdentityProvider provider,
            String externalId
    ) {
        super(source);
        this.userId = userId;
        this.provider = provider;
        this.externalId = externalId;
    }
}