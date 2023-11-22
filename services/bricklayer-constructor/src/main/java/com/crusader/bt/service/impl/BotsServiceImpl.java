package com.crusader.bt.service.impl;

import com.crusader.bt.dto.BotDto;
import com.crusader.bt.service.BotsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotsServiceImpl implements BotsService {

    @Override
    public Mono<BotDto> createBot(String ownerId, String username, String displayName, String description) {

        return Mono.empty(); //todo add request into bot constructor

    }

    @Override
    public Mono<BotDto> updateBotInfo(String ownerId,
                                      String username,
                                      String displayName,
                                      String description,
                                      BigInteger updateAt) {

        return Mono.empty(); //todo add request into bot constructor
    }

    @Override
    public Mono<BotDto> deleteBot(String ownerId, String username) {

        return Mono.empty(); //todo add request into bot constructor
    }

    @Override
    public Mono<BotDto> getBotInfo(String ownerId, String username) {

        return Mono.empty(); //todo add request into bot constructor
    }

    @Override
    public Flux<BotDto> getBotsList() {

        return Flux.empty(); //todo add request into bot constructor
    }

}
