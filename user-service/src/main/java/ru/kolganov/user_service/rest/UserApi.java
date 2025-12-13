package ru.kolganov.user_service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kolganov.user_service.rest.dto.UserDtoRq;
import ru.kolganov.user_service.rest.dto.UserDtoRs;

public interface UserApi {

    @PostMapping("rest/api/v1/user/identify")
    ResponseEntity<UserDtoRs> getUserByExternalId(@Nullable @RequestBody final UserDtoRq userDtoRq);
}
