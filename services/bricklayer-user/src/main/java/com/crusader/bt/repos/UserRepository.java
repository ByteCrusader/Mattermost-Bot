package com.crusader.bt.repos;

import com.crusader.bt.entity.UserEntity;
import com.crusader.bt.repos.custom.CustomUserRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long>, CustomUserRepository {
    Mono<UserEntity> findByUsername(String username);

    Mono<UserEntity> save(UserEntity department);
}
