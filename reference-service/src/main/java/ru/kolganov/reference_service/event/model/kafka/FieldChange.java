package ru.kolganov.reference_service.event.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldChange<T> {
    private T oldVal;
    private T newVal;
}
