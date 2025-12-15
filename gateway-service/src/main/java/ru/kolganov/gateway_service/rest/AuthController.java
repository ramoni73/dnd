package ru.kolganov.gateway_service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolganov.gateway_service.security.JwtService;
import ru.kolganov.gateway_service.security.RefreshTokenStore;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final RefreshTokenStore refreshTokenStore;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/auth/refresh")
    public Mono<Void> refresh(final ServerWebExchange exchange) {
        final String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return sendUnauthorized(exchange);
        }

        final String refreshToken = authHeader.substring(7);

        final Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(jwtService.getSignInKey())
                    .build()
                    .parseSignedClaims(refreshToken)
                    .getPayload();

            if (!"refresh".equals(claims.get("type", String.class))) {
                return sendUnauthorized(exchange);
            }
        } catch (final Exception e) {
            return sendUnauthorized(exchange);
        }

        final String userId = claims.getSubject();

        return refreshTokenStore.isValid(userId, refreshToken)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return sendUnauthorized(exchange);
                    }

                    // TODO: в продакшене — получать роли из user-service или сохранять их при генерации refresh-токена
                    String roles = "USER";

                    final String newAccessToken = jwtService.generateAccessToken(userId, roles);
                    return sendAccessTokenResponse(exchange, newAccessToken, refreshToken);
                });
    }

    private Mono<Void> sendUnauthorized(final ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> sendAccessTokenResponse(
            final ServerWebExchange exchange,
            final String accessToken,
            final String refreshToken
    ) {
        final ObjectNode response = objectMapper.createObjectNode()
                .put("access_token", accessToken)
                .put("refresh_token", refreshToken)
                .put("token_type", "Bearer");

        final String jsonResponse = response.toString();
        final byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        exchange.getResponse().getHeaders().setContentLength(bytes.length);
        exchange.getResponse().setStatusCode(HttpStatus.OK);

        return exchange.getResponse().writeWith(
                reactor.core.publisher.Flux.just(
                        exchange.getResponse().bufferFactory().wrap(bytes)
                )
        );
    }
}
