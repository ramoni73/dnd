package ru.kolganov.character_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kolganov.character_service.model.CharacterModel;

public interface CharacterRepository extends MongoRepository<CharacterModel, String> {
}
