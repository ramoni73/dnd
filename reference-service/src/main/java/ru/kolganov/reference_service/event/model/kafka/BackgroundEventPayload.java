package ru.kolganov.reference_service.event.model.kafka;

import java.util.Set;

public record BackgroundEventPayload(
        String code,
        String name,
        String description,
        FeatEvent feat,
        Set<AbilityEvent> abilities,
        Set<SkillEvent> skills,
        String equipment,
        String instruments
) {
    public BackgroundEventPayload(String code) {
        this(code, null, null, null, null, null, null, null);
    }
}
