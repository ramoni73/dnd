package ru.kolganov.reference_service.event.model.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BackgroundCreateEventPayload(
        String code,
        String name,
        String description,
        FeatEvent feat,
        Set<AbilityEvent> abilities,
        Set<SkillEvent> skills,
        String equipment,
        String instruments
) implements BackgroundEventPayload {
    public BackgroundCreateEventPayload(String code) {
        this(code, null, null, null, null, null, null, null);
    }
}
