package ru.kolganov.reference_service.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kolganov.reference_service.event.model.kafka.*;
import ru.kolganov.reference_service.service.model.AbilityModel;
import ru.kolganov.reference_service.service.model.BackgroundModel;
import ru.kolganov.reference_service.service.model.FeatModel;
import ru.kolganov.reference_service.service.model.SkillModel;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BackgroundUpdateEventBuilder {

    public BackgroundUpdateEventPayload buildEvent(BackgroundModel oldModel, BackgroundModel newModel) {
        BackgroundUpdateEventPayload.BackgroundUpdateEventPayloadBuilder payloadBuilder = BackgroundUpdateEventPayload.builder()
                .code(newModel.code());

        if (!Objects.equals(oldModel.name(), newModel.name())) {
            payloadBuilder.name(new FieldChange<>(oldModel.name(), newModel.name()));
        }

        if (!Objects.equals(oldModel.description(), newModel.description())) {
            payloadBuilder.description(new FieldChange<>(oldModel.description(), newModel.description()));
        }

        if (!Objects.equals(oldModel.equipment(), newModel.equipment())) {
            payloadBuilder.equipment(new FieldChange<>(oldModel.equipment(), newModel.equipment()));
        }

        if (!Objects.equals(oldModel.instruments(), newModel.instruments())) {
            payloadBuilder.instruments(new FieldChange<>(oldModel.instruments(), newModel.instruments()));
        }

        FeatModel oldFeat = oldModel.feat();
        FeatModel newFeat = newModel.feat();

        if (!Objects.equals(
                oldFeat != null ? oldFeat.code() : null,
                newFeat != null ? newFeat.code() : null)) {

            payloadBuilder.feat(
                    new FieldChange<>(
                            oldFeat != null ? toFeatEvent(oldFeat) : null,
                            newFeat != null ? toFeatEvent(newFeat) : null
                    )
            );
        }

        Set<AbilityModel> oldAbilities = oldModel.abilities();
        Set<AbilityModel> newAbilities = newModel.abilities();

        if (!areSetsEqual(oldModel.abilities(), newModel.abilities(), AbilityModel::code)) {
            payloadBuilder.abilities(
                    new FieldChange<>(toAbilityEventList(oldAbilities), toAbilityEventList(newAbilities)));
        }

        Set<SkillModel> oldSkills = oldModel.skills();
        Set<SkillModel> newSkills = newModel.skills();

        if (!areSetsEqual(oldModel.skills(), newModel.skills(), SkillModel::code)) {
            payloadBuilder.skills(new FieldChange<>(toSkillDtoList(oldSkills), toSkillDtoList(newSkills)));
        }

        return payloadBuilder.build();
    }

    private <T> boolean areSetsEqual(Set<T> set1, Set<T> set2, Function<T, String> codeExtractor) {
        if (set1 == null && set2 == null) return true;
        if (set1 == null || set2 == null) return false;
        if (set1.size() != set2.size()) return false;

        Set<String> codes1 = set1.stream().map(codeExtractor).collect(Collectors.toSet());
        Set<String> codes2 = set2.stream().map(codeExtractor).collect(Collectors.toSet());

        return codes1.equals(codes2);
    }

    private FeatEvent toFeatEvent(FeatModel model) {
        return new FeatEvent(model.code(), model.name(), model.category());
    }

    private List<AbilityEvent> toAbilityEventList(Set<AbilityModel> models) {
        if (models == null || models.isEmpty()) {
            return Collections.emptyList();
        }
        return models.stream()
                .map(this::toAbilityDto)
                .sorted(Comparator.comparing(AbilityEvent::code))
                .collect(Collectors.toList());
    }

    private AbilityEvent toAbilityDto(AbilityModel model) {
        return new AbilityEvent(model.code(), model.name(), model.description());
    }

    private List<SkillEvent> toSkillDtoList(Set<SkillModel> models) {
        if (models == null || models.isEmpty()) {
            return Collections.emptyList();
        }
        return models.stream()
                .map(this::toSkillEvent)
                .sorted(Comparator.comparing(SkillEvent::code))
                .collect(Collectors.toList());
    }

    private SkillEvent toSkillEvent(SkillModel model) {
        return new SkillEvent(model.code(), model.name(), model.description());
    }
}
