package ru.kolganov.dice_service.event.model;

import ru.kolganov.dice_service.model.RollResponse;

public record DiceRollPayload(RollResponse rollResponse) {
}
