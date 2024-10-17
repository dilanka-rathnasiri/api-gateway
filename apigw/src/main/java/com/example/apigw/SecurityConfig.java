package com.example.apigw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(
            final CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            final CustomAccessDeniedHandler customAccessDeniedHandler
    ) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                HttpMethod.GET,
                                "/server-1",
                                "/server-2",
                                "/server-1/*",
                                "/server-2/*",
                                "/server-5/sse"
                        ).permitAll()
                        .pathMatchers(HttpMethod.POST, "/server-3/*", "/server-4/*").permitAll()
                        .pathMatchers(HttpMethod.GET, "/server-6/ws").permitAll()
                ).build();
    }
}
