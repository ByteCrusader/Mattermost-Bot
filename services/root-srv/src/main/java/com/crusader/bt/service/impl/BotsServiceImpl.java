package com.crusader.bt.service.impl;

import com.crusader.bt.converter.BotEntityToDtoConverter;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.entity.BotEntity;
import com.crusader.bt.repos.BotRepository;
import com.crusader.bt.service.BotsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotsServiceImpl implements BotsService {

    private final BotRepository botRepository;
    private final BotEntityToDtoConverter botConverter;

    @Override
    public Mono<BotDto> createBot(Principal principal, String username, String displayName, String description) {

        return botRepository.save(
                        createNewBotEntity(
                                username, displayName, description, principal.getName()
                        )
                )
                .map(botConverter::convert);
    }

    @Override
    public Mono<BotDto> updateBotInfo(Principal principal,
                                      String username,
                                      String displayName,
                                      String description,
                                      BigInteger updateAt) {

        return botRepository.findByUsername(username)
                .filter(entity -> checkUser(principal, entity.getOwnerId()))
                .filter(botEntity -> checkBotVersion(botEntity, updateAt))
                .map(botEntity -> updateBotInfo(botEntity, displayName, description))
                .flatMap(botRepository::save)
                .map(botConverter::convert);
    }

    @Override
    public Mono<BotDto> deleteBot(Principal principal, String username) {

        return botRepository.findByUsername(username)
                .filter(entity -> checkUser(principal, entity.getOwnerId()))
                .filter(this::checkBotAvailability)
                .map(this::deleteBot)
                .flatMap(botRepository::save)
                .map(botConverter::convert);
    }

    @Override
    public Mono<BotDto> getBotInfo(Principal principal, String username) {

        return botRepository.findByUsername(username)
                .filter(entity -> checkUser(principal, entity.getOwnerId()))
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

    private boolean checkUser(Principal principal, String ownerId) {

        return StringUtils.equals(principal.getName(), ownerId);
    }

    private boolean checkBotVersion(BotEntity entity, BigInteger updateAt) {

        return Objects.equals(entity.getUpdateAt(), updateAt);
    }

    private boolean checkBotAvailability(BotEntity entity) {

        return Objects.isNull(entity.getDeleteAt());
    }

}
