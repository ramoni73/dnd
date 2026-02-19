package ru.kolganov.reference_service.background;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.kolganov.reference_service.BaseIT;
import ru.kolganov.reference_service.rest.dto.background.BackgroundCreateRqDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRsDto;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.COLLECTION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BackgroundIT extends BaseIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /rest/api/v1/background — успешное создание")
    void createBackground_success() throws Exception {
        var request = new BackgroundCreateRqDto(
                "IMBA_42",
                "Это имба 42",
                "Просто потому что не подошло ничего из официального по статам и тп.",
                "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"),
                "Просто берем 50 голды.",
                "Этим никто не пользуется, но пусть будут воровские инструменты."
        );

        var responseContent = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var actual = objectMapper.readValue(responseContent, BackgroundRsDto.class);

        assertThat(actual).isNotNull();
        assertThat(actual.code()).isEqualTo("IMBA_42");
        assertThat(actual.name()).isEqualTo("Это имба 42");
        assertThat(actual.description()).contains("официального по статам");

        assertThat(actual.abilities())
                .asInstanceOf(COLLECTION)
                .isNotNull()
                .hasSize(3)
                .extracting("code")
                .containsExactlyInAnyOrder("CHA", "STR", "DEX");

        assertThat(actual.skills())
                .asInstanceOf(COLLECTION)
                .isNotNull()
                .hasSize(2)
                .extracting("code")
                .containsExactlyInAnyOrder("Performance", "Acrobatics");

        assertThat(actual.equipment()).isEqualTo("Просто берем 50 голды.");
        assertThat(actual.instruments()).contains("воровские инструменты");
    }
}
