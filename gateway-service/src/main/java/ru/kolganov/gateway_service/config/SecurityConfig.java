package ru.kolganov.gateway_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import ru.kolganov.gateway_service.filter.JwtAuthenticationWebFilter;
import ru.kolganov.gateway_service.security.JwtPostAuthenticationSuccessHandler;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtPostAuthenticationSuccessHandler jwtSuccessHandler;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(final ServerHttpSecurity http,
                                                            final JwtAuthenticationWebFilter jwtAuthFilter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers("/actuator/**", "/auth/refresh").permitAll()
                                .anyExchange().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authenticationSuccessHandler(jwtSuccessHandler)
                )
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}
