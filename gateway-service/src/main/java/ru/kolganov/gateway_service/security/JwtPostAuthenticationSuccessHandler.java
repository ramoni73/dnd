package ru.kolganov.gateway_service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class JwtPostAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        var oauth2User = (org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken) authentication;
        var attributes = oauth2User.getPrincipal().getAttributes();
        String actualUserId = (String) attributes.get("userId");
        String roles = String.join(",", (Iterable<String>) attributes.get("roles"));

        String jwt = jwtService.generateToken(actualUserId, roles);

        exchange.getResponse().addCookie(
                ResponseCookie.from("X-DND-JWT", jwt)
                        .httpOnly(true)
                        .secure(false) // ← false при разработке по HTTP!
                        .sameSite("Lax")
                        .path("/")
                        .maxAge(Duration.ofMinutes(15))
                        .build()
        );

        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create("/"));

        return exchange.getResponse().setComplete();
    }
}
