package ru.kolganov.reference_service.background;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.kolganov.reference_service.BaseIT;
import ru.kolganov.reference_service.exception.ErrorResponse;
import ru.kolganov.reference_service.rest.dto.background.BackgroundCreateRqDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundRsDto;
import ru.kolganov.reference_service.rest.dto.background.BackgroundUpdateRqDto;

import java.util.Map;
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
    @DisplayName("POST /rest/api/v1/background — дубликат code → 409 Conflict")
    void createBackground_duplicateCode_returns409() throws Exception {
        var firstRequest = new BackgroundCreateRqDto(
                "DUPLICATE_TEST", "First", "Desc", "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"), "Eq", "Inst"
        );
        mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(firstRequest)))
                .andExpect(status().isCreated());

        var secondRequest = new BackgroundCreateRqDto(
                "DUPLICATE_TEST", "Second", "Desc2", "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"), "Eq2", "Inst2"
        );

        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(secondRequest)))
                .andExpect(status().isConflict())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isEqualTo(409);
        assertThat(error.error()).isEqualTo("Element already exists exception");
        assertThat(error.message()).contains("DUPLICATE_TEST");
    }

    @Test
    @DisplayName("POST /rest/api/v1/background — null code → 400 Bad Request (валидация)")
    void createBackground_nullCode_returns400() throws Exception {
        var request = new BackgroundCreateRqDto(
                null,
                "Name", "Desc", "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"), "Eq", "Inst"
        );

        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isEqualTo(400);
        assertThat(error.error()).isEqualTo("Validation failed");
        assertThat(error.message()).contains("code");
    }

    @Test
    @DisplayName("POST /rest/api/v1/background — null name → 400 Bad Request (валидация)")
    void createBackground_nullName_returns400() throws Exception {
        var request = new BackgroundCreateRqDto(
                "CODE_123",
                null,
                "Desc", "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"), "Eq", "Inst"
        );

        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isEqualTo(400);
        assertThat(error.error()).isEqualTo("Validation failed");
        assertThat(error.message()).contains("name");
    }

    @Test
    @DisplayName("POST /rest/api/v1/background — пустой code → 400 Bad Request (валидация)")
    void createBackground_emptyCode_returns400() throws Exception {
        var request = new BackgroundCreateRqDto(
                "",
                "Name", "Desc", "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"), "Eq", "Inst"
        );

        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isEqualTo(400);
        assertThat(error.error()).isEqualTo("Validation failed");
    }

    @Test
    @DisplayName("POST /rest/api/v1/background — неправильное количество abilityCodes → 400 Bad Request")
    void createBackground_wrongAbilityCount_returns400() throws Exception {
        var request = new BackgroundCreateRqDto(
                "CODE_123", "Name", "Desc", "Skilled",
                Set.of("CHA"),
                Set.of("Performance", "Acrobatics"), "Eq", "Inst"
        );

        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isEqualTo(400);
        assertThat(error.error()).isEqualTo("Validation failed");
        assertThat(error.message()).contains("abilityCodes");
    }

    @Test
    @DisplayName("POST /rest/api/v1/background — некорректный JSON → 400 Bad Request")
    void createBackground_invalidJson_returns400() throws Exception {
        var invalidJson = "{\"code\": \"TEST\", \"name\": 123}";

        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isIn(400);
    }

    @Test
    @DisplayName("POST /rest/api/v1/background — несуществующий featCode → 404")
    void createBackground_nonExistentFeat_returns404() throws Exception {
        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new BackgroundCreateRqDto(
                                "NEW_CODE", "Name", "Desc", "NON_EXISTENT_FEAT",
                                Set.of("CHA", "STR", "DEX"),
                                Set.of("Performance", "Acrobatics"), "Eq", "Inst"
                        ))))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);
        assertThat(error.status()).isEqualTo(404);
        assertThat(error.error()).isEqualTo("Element not found");
    }

    @Test
    @DisplayName("POST /rest/api/v1/background — несуществующие skillCodes → 404")
    void createBackground_nonExistentSkills_returns404() throws Exception {
        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new BackgroundCreateRqDto(
                                "NEW_CODE", "Name", "Desc", "Skilled",
                                Set.of("CHA", "STR", "DEX"), Set.of("NON_EXISTENT_SKILL", "Acrobatics"), "Eq", "Inst"
                        ))))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);
        assertThat(error.status()).isEqualTo(404);
        assertThat(error.error()).isEqualTo("Elements not found");
    }

    @Test
    @DisplayName("POST /rest/api/v1/background — несуществующие abilityCodes → 404")
    void createBackground_nonExistentAbilities_returns404() throws Exception {
        var response = mockMvc.perform(post("/rest/api/v1/background")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new BackgroundCreateRqDto(
                                "NEW_CODE", "Name", "Desc", "Skilled",
                                Set.of("NON_EXISTENT_ABILITY", "CHA", "STR"), Set.of("Performance", "Acrobatics"), "Eq", "Inst"
                        ))))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);
        assertThat(error.status()).isEqualTo(404);
        assertThat(error.error()).isEqualTo("Element not found");
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
    @DisplayName("PATCH /rest/api/v1/background/{code} — обновление несуществующего кода → 404")
    void updateBackground_notFound_returns404() throws Exception {
        var updateRequest = new BackgroundUpdateRqDto("New Name", null, null, null, null, null, null);

        var response = mockMvc.perform(patch("/rest/api/v1/background/{code}", "NON_EXISTENT_CODE")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateRequest)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isEqualTo(404);
        assertThat(error.error()).isEqualTo("Element not found");
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
    @DisplayName("GET /rest/api/v1/background/{code} — несуществующий код → 404")
    void getBackground_notFound_returns404() throws Exception {
        var response = mockMvc.perform(get("/rest/api/v1/background/{code}", "NON_EXISTENT_CODE"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isEqualTo(404);
        assertThat(error.error()).isEqualTo("Element not found");
        assertThat(error.message()).contains("NON_EXISTENT_CODE");
    }

    @Test
    @DisplayName("DELETE /rest/api/v1/background/{code} — успешное удаление")
    void deleteBackground_success() throws Exception {
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
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/rest/api/v1/background/{code}", created.code()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /rest/api/v1/background/{code} — удаление несуществующего кода → 404")
    void deleteBackground_notFound_returns404() throws Exception {
        var response = mockMvc.perform(delete("/rest/api/v1/background/{code}", "NON_EXISTENT_CODE"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var error = objectMapper.readValue(response, ErrorResponse.class);

        assertThat(error.status()).isEqualTo(404);
        assertThat(error.error()).isEqualTo("Element not found");
    }

    @Test
    @DisplayName("DELETE + CREATE /rest/api/v1/background/{code} — повторное создание с тем же кодом")
    void deleteThenCreate_sameCode_success() throws Exception {
        var code = "RECYCLE_TEST";

        create(new BackgroundCreateRqDto(
                code, "DELETE + CREATE First", "DELETE + CREATE First desc", "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"), "Eq1", "Inst1"
        ));

        mockMvc.perform(delete("/rest/api/v1/background/{code}", code))
                .andExpect(status().isNoContent());

        var second = create(new BackgroundCreateRqDto(
                code, "DELETE + CREATE Second", "DELETE + CREATE Second desc", "Skilled",
                Set.of("CHA", "STR", "DEX"),
                Set.of("Performance", "Acrobatics"), "Eq2", "Inst2"
        ));

        assertThat(second.name()).isEqualTo("DELETE + CREATE Second");
        assertThat(second.equipment()).isEqualTo("Eq2");
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
