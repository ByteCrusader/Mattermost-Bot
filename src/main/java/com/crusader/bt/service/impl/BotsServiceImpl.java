package com.crusader.bt.service.impl;

import com.crusader.bt.converter.BotEntityToDtoConverter;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.entity.BotEntity;
import com.crusader.bt.repos.BotRepository;
import com.crusader.bt.service.BotsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotsServiceImpl implements BotsService {

    private final BotRepository botRepository;
    private final BotEntityToDtoConverter botConverter;

    @Override
    public Mono<BotDto> createBot(String username, String displayName, String description, String ownerId) {

        return botRepository.save(
                        createNewBotEntity(
                                username, displayName, description, ownerId
                        )
                )
                .map(botConverter::convert);
    }

    @Override
    public Mono<BotDto> updateBotInfo(String username, String displayName, String description) {

        return botRepository.findByUsername(username)
                .map(botEntity -> updateBotInfo(botEntity, displayName, description))
                .flatMap(botRepository::save)
                .map(botConverter::convert);
    }

    @Override
    public Mono<BotDto> deleteBot(String username) {

        return botRepository.findByUsername(username)
                .map(this::deleteBot)
                .flatMap(botRepository::save)
                .map(botConverter::convert);
    }

    @Override
    public Mono<BotDto> getBotInfo(String username) {

        return botRepository.findByUsername(username)
                .filter(entity -> Objects.isNull(entity.getDeleteAt()))
                .map(botConverter::convert);
    }

    @Override
    public Flux<BotDto> getBotsList() {

        return botRepository.findAll()
                .map(botConverter::convert);
    }

    private BotEntity createNewBotEntity(String username, String displayName, String description, String ownerId) {
        return new BotEntity(
                null,
                UUID.randomUUID().toString(),
                BigInteger.valueOf(System.currentTimeMillis()),
                null,
                null,
                username,
                displayName,
                description,
                ownerId
        );
    }

    private BotEntity updateBotInfo(BotEntity entity, String displayName, String description) {
        entity.setDisplayName(displayName);
        entity.setDescription(description);
        entity.setUpdateAt(BigInteger.valueOf(System.currentTimeMillis()));

        return entity;
    }

    private BotEntity deleteBot(BotEntity entity) {
        entity.setDeleteAt(BigInteger.valueOf(System.currentTimeMillis()));

        return entity;
    }

}
