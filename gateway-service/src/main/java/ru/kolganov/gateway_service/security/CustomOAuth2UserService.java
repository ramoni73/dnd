package ru.kolganov.gateway_service.security;

import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kolganov.gateway_service.rest.dto.UserDtoRs;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomOAuth2UserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final WebClient userWebClient;
    private final ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> defaultUserService =
            new DefaultReactiveOAuth2UserService();

    public CustomOAuth2UserService(WebClient userWebClient) {
        this.userWebClient = userWebClient;
    }

    @Override
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) {
        return defaultUserService.loadUser(userRequest)
                .flatMap(oauth2User -> {
                    String externalId = oauth2User.getAttribute("sub");
                    String email = oauth2User.getAttribute("email");
                    String name = oauth2User.getAttribute("name");

                    return userWebClient.post()
                            .uri("/rest/api/v1/user/identify")
                            .bodyValue(Map.of(
                                    "externalId", externalId,
                                    "provider", "GOOGLE",
                                    "email", email,
                                    "displayName", name
                            ))
                            .retrieve()
                            .bodyToMono(UserDtoRs.class)
                            .map(response -> {
                                Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());
                                attributes.put("userId", response.userId().toString());
                                attributes.put("roles", response.roles());
                                return new DefaultOAuth2User(
                                        oauth2User.getAuthorities(),
                                        attributes,
                                        "sub"
                                );
                            });
                });
    }
}
