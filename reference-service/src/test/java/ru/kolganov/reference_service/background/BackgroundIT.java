package ru.kolganov.reference_service.background;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.kolganov.reference_service.BaseIT;
import ru.kolganov.reference_service.rest.dto.background.BackgroundCreateRqDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRsDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundUpdateRqDto;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.COLLECTION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BackgroundIT extends BaseIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /rest/api/v1/background — успешное создание")
    void createBackground_success() throws Exception {
        var actual = create(new BackgroundCreateRqDto(
                "IMBA_42",
                "Это имба 42",
                "Просто потому что не подошло ничего из официального по статам и тп.",
                "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"),
                "Просто берем 50 голды.",
                "Этим никто не пользуется, но пусть будут воровские инструменты."
        ));

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

    @Test
    @DisplayName("PATCH /rest/api/v1/background/{code} — успешное частичное обновление")
    void updateBackground_success() throws Exception {
        var created = create(new BackgroundCreateRqDto(
                "UPDATE_TEST",
                "Old Name",
                "Old description",
                "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"),
                "Old equipment",
                "Old instruments"
        ));

        var updateRequest = new BackgroundUpdateRqDto(
                "New name",
                "New Description",
                "Skilled",
                null,
                null,
                null,
                null
        );

        var responseContent = mockMvc.perform(patch("/rest/api/v1/background/{code}", created.code())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var actual = objectMapper.readValue(responseContent, BackgroundRsDto.class);

        assertThat(actual).isNotNull();
        assertThat(actual.code()).isEqualTo("UPDATE_TEST");
        assertThat(actual.name()).isEqualTo("New name");
        assertThat(actual.description()).contains("New Description");

        assertThat(actual.featDto()).isNotNull();
        assertThat(actual.featDto().code()).isEqualTo("Skilled");

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

        assertThat(actual.equipment()).isEqualTo("Old equipment");
        assertThat(actual.instruments()).isEqualTo("Old instruments");
    }

    @Test
    @DisplayName("GET /rest/api/v1/background/{code} — успешное получение по коду")
    void getBackground_success() throws Exception {
        var created = create(new BackgroundCreateRqDto(
                "IMBA_73",
                "Это имба 73",
                "Просто потому что не подошло ничего из официального по статам и тп.",
                "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"),
                "Просто берем 50 голды.",
                "Этим никто не пользуется, но пусть будут воровские инструменты."
        ));

        var getContent = mockMvc.perform(get("/rest/api/v1/background/{code}", created.code()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var background = objectMapper.readValue(getContent, BackgroundRsDto.class);

        assertThat(background).isNotNull();
        assertThat(background.code()).isEqualTo("IMBA_73");
        assertThat(background.name()).isEqualTo("Это имба 73");
        assertThat(background.description()).contains("официального по статам");

        assertThat(background.featDto()).isNotNull();
        assertThat(background.featDto().code()).isEqualTo("Skilled");
        assertThat(background.featDto().name()).isNotEmpty();

        assertThat(background.abilities())
                .asInstanceOf(COLLECTION)
                .isNotNull()
                .hasSize(3)
                .extracting("code")
                .containsExactlyInAnyOrder("CHA", "STR", "DEX");

        assertThat(background.skills())
                .asInstanceOf(COLLECTION)
                .isNotNull()
                .hasSize(2)
                .extracting("code")
                .containsExactlyInAnyOrder("Performance", "Acrobatics");

        assertThat(background.equipment()).isEqualTo("Просто берем 50 голды.");
        assertThat(background.instruments()).contains("воровские инструменты");
    }

    @Test
    @DisplayName("DELETE /rest/api/v1/background/{code} — успешное удаление")
    void deleteBackground_success() throws Exception {
        // Arrange: создаём запись
        var created = create(new BackgroundCreateRqDto(
                "DELETE_TEST",
                "To Be Deleted",
                "This will be removed",
                "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"),
                "Some equipment",
                "Some instruments"
        ));

        mockMvc.perform(delete("/rest/api/v1/background/{code}", created.code()))
                .andExpect(status().isNoContent());  // 204 No Content

        mockMvc.perform(get("/rest/api/v1/background/{code}", created.code()))
                .andExpect(status().isNotFound());
    }

    private BackgroundRsDto create(BackgroundCreateRqDto request) throws Exception {
        var responseContent = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(responseContent, BackgroundRsDto.class);
    }
}
