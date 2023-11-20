package com.crusader.bt.repos;

import com.crusader.bt.entity.BotEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BotRepository extends ReactiveCrudRepository<BotEntity, String> {

    Mono<BotEntity> findByUsername(String username);

}
