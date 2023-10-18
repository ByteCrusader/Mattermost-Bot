package com.crusader.bt.repos.custom;

import com.crusader.bt.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface CustomUserRepository {
    Mono<UserEntity> findByUsername(String username);

    Mono<UserEntity> save(UserEntity department);
}
