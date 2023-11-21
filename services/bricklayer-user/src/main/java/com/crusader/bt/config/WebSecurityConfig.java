package com.crusader.bt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationManagerConfig authenticationManager;
    private final SecurityContextRepositoryConfig securityContextRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(
                        exchange ->
                                exchange.pathMatchers("/user/**").permitAll()
                                        .pathMatchers("/actuator/**").permitAll()
                                        .anyExchange().authenticated()
                )
                .exceptionHandling(
                        handle ->
                                handle.authenticationEntryPoint(
                                                (exchange, ex) ->
                                                        Mono.fromRunnable(
                                                                () -> exchange.getResponse()
                                                                        .setStatusCode(HttpStatus.UNAUTHORIZED)
                                                        )

                                        )
                                        .accessDeniedHandler(
                                                (exchange, ex) ->
                                                        Mono.fromRunnable(
                                                                () -> exchange.getResponse()
                                                                        .setStatusCode(HttpStatus.FORBIDDEN)
                                                        )

                                        )
                )
                .build();
    }
}
