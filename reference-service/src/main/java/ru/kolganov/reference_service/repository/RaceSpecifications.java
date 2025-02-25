package ru.kolganov.reference_service.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.kolganov.reference_service.entity.RaceEntity;
import ru.kolganov.reference_service.service.filter.RaceFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RaceSpecifications {
    public static Specification<RaceEntity> withFilter(RaceFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filter.name().toLowerCase(Locale.ROOT) + "%"));
            }

            if (filter.description() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + filter.description().toLowerCase(Locale.ROOT) + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
