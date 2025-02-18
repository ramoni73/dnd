package ru.kolganov.reference_service.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.kolganov.reference_service.entity.BackgroundEntity;
import ru.kolganov.reference_service.service.filter.BackgroundFilter;

import java.util.ArrayList;
import java.util.List;

public class BackgroundSpecifications {
    public static Specification<BackgroundEntity> withFilter(BackgroundFilter backgroundFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (backgroundFilter.name() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + backgroundFilter.name().toLowerCase() + "%"));
            }

            if (backgroundFilter.description() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + backgroundFilter.description().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
