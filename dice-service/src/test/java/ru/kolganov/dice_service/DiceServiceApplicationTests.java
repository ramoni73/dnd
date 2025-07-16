package ru.kolganov.dice_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kolganov.dice_service.model.DiceModel;
import ru.kolganov.dice_service.model.DiceModelRs;
import ru.kolganov.dice_service.model.DiceType;
import ru.kolganov.dice_service.service.RollService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DiceServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testGetRolls() {
		RollService service = new RollService();
		List<DiceModel> requests = List.of(
				new DiceModel(DiceType.D6, 3),
				new DiceModel(DiceType.D20, 2)
		);
		DiceModelRs result = service.getRolls(requests);
		assertNotNull(result);
		assertEquals(5, result.diceResults().size()); // 3 + 2 броска
	}
}
