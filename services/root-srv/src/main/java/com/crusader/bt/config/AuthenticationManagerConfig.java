package com.crusader.bt.config;

import com.crusader.bt.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationManagerConfig implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String token = authentication.getCredentials().toString();
        String username;

        try {
            username = JwtUtil.extractUsername(token);
        } catch (Exception e) {
            username = null;

            log.error(e.getMessage());
        }

        if (Objects.nonNull(username) && JwtUtil.validateToken(token)) {

            List<SimpleGrantedAuthority> authorities =
                    (List<SimpleGrantedAuthority>) JwtUtil.getClaimsFromToken(token)
                            .get("role", List.class)
                            .stream()
                            .map(role -> new SimpleGrantedAuthority((String) role))
                            .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

            return Mono.just(authenticationToken);
        } else {
            return Mono.empty();
        }
    }
}
