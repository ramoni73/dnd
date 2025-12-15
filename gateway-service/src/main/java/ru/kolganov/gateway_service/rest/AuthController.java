package ru.kolganov.gateway_service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public Mono<Void> refresh(ServerWebExchange exchange) {
        final String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        final String refreshToken = authHeader.substring(7);

        try {
            final Claims claims = Jwts.parser()
                    .verifyWith(jwtService.getSignInKey())
                    .build()
                    .parseSignedClaims(refreshToken)
                    .getPayload();

            if (!"refresh".equals(claims.get("type", String.class))) {
                throw new IllegalArgumentException("Not a refresh token");
            }

            final String userId = claims.getSubject();
            if (refreshTokenStore.isValid(userId, refreshToken)) {
                final String roles = "USER"; // ← временно; лучше запросить user-service
                final String newAccessToken = jwtService.generateAccessToken(userId, roles);

                final String response = objectMapper.createObjectNode()
                        .put("access_token", newAccessToken)
                        .put("refresh_token", refreshToken)
                        .put("token_type", "Bearer")
                        .toString();

                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                exchange.getResponse().setStatusCode(HttpStatus.OK);
                final byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
                exchange.getResponse().getHeaders().setContentLength(bytes.length);
                return exchange.getResponse().writeWith(Flux.just(exchange.getResponse().bufferFactory().wrap(bytes)));
            }
        } catch (Exception e) {
            // ignore
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
