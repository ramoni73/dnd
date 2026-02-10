package ru.kolganov.reference_service.event.model.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BackgroundUpdateEventPayload(
        String code,
        FieldChange<String> name,
        FieldChange<String> description,
        FieldChange<String> equipment,
        FieldChange<String> instruments,
        FieldChange<FeatEvent> feat,
        FieldChange<List<AbilityEvent>> abilities,
        FieldChange<List<SkillEvent>> skills
) implements BackgroundEventPayload {
}
