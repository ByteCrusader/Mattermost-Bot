package com.crusader.bt.service.impl;

import com.crusader.bt.converter.UserEntityToDtoConverter;
import com.crusader.bt.dto.UserDto;
import com.crusader.bt.entity.UserEntity;
import com.crusader.bt.entity.UserRoleEntity;
import com.crusader.bt.repos.UserRepository;
import com.crusader.bt.service.UserService;
import com.crusader.bt.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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
    public Mono<String> loginUser(UserDto userDto) {

        return userRepository.findByUsername(userDto.getUsername())
                .map(userDetails -> passwordEncoder.matches(
                                userDto.getPassword(),
                                userDetails.getPassword()
                        ) ?
                                JwtUtil.generateToken(userDetails) :
                                StringUtils.EMPTY
                )
                .filter(StringUtils::isNotBlank);
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
