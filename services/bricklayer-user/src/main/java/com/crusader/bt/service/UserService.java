package com.crusader.bt.service;

import com.crusader.bt.dto.JwtDto;
import com.crusader.bt.dto.TokenDto;
import com.crusader.bt.dto.UserDto;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    /**
     * Register new user dto
     */
    Mono<UserDto> registerNewUser(UserDto userDto);

    /**
     * Login user into system
     */
    Mono<JwtDto> loginUser(TokenDto tokenDto);

}
