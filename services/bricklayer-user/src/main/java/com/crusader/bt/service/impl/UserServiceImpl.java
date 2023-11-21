package com.crusader.bt.service.impl;

import com.crusader.bt.converter.UserEntityToDtoConverter;
import com.crusader.bt.dto.JwtDto;
import com.crusader.bt.dto.TokenDto;
import com.crusader.bt.dto.UserDto;
import com.crusader.bt.entity.UserEntity;
import com.crusader.bt.entity.UserRoleEntity;
import com.crusader.bt.repos.UserRepository;
import com.crusader.bt.service.UserService;
import com.crusader.bt.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String REQUIRED_CREDENTIALS = "client_credentials";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityToDtoConverter userConverter;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return userRepository.findByUsername(username)
                .cast(UserDetails.class);
    }

    @Override
    public Mono<UserDto> registerNewUser(UserDto userDto) {

        return userRepository.save(
                        buildNewUser(userDto)
                )
                .map(userConverter::convert);
    }

    @Override
    public Mono<JwtDto> loginUser(TokenDto tokenDto) {

        return Mono.just(tokenDto)
                .filter(dto -> REQUIRED_CREDENTIALS.equals(dto.getGrantType()))
                .flatMap(dto -> userRepository.findByUsername(dto.getClientId()))
                .filter(userDetails -> passwordEncoder.matches(
                        tokenDto.getClientSecret(),
                        userDetails.getPassword()
                ))
                .map(JwtUtil::generateToken)
                .map(pair -> JwtDto.builder()
                        .token(pair.getFirst())
                        .exp(pair.getSecond())
                        .build()
                );
    }

    private UserEntity buildNewUser(UserDto userDto) {
        UserRoleEntity defaultRole = UserRoleEntity.builder()
                .name("USER")
                .description("Default app role")
                .build();

        return UserEntity.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Collections.singleton(defaultRole))
                .build();
    }
}
