package ru.kolganov.dice_service.event.model;

import ru.kolganov.dice_service.model.DiceModelRs;

public record DiceRollPayload(DiceModelRs diceModelRs) {
}
