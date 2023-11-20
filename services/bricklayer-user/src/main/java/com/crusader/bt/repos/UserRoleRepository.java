package com.crusader.bt.repos;

import com.crusader.bt.entity.UserRoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRoleRepository extends ReactiveCrudRepository<UserRoleEntity, String> {

    Mono<UserRoleEntity> findByName(String name);

}
