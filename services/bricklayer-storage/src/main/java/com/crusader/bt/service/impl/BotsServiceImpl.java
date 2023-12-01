package com.crusader.bt.service.impl;

import com.crusader.bt.converter.BotEntityToDtoConverter;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.entity.BotEntity;
import com.crusader.bt.mapper.MessageDtoMapper;
import com.crusader.bt.repos.BotRepository;
import com.crusader.bt.service.BotsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private final MessageDtoMapper messageDtoMapper;

    @Override
    public Mono<Void> createBot(MessageDto createDto) {

        return Mono.just(
                        messageDtoMapper.mapToBotEntity(createDto)
                )
                .map(this::createNewBotEntity)
                .flatMap(botRepository::save)
                .then();
    }

    @Override
    public Mono<Void> updateBotInfo(MessageDto updateDto) {

        return botRepository.findByUsername(updateDto.getUsername())
                .filter(entity -> checkUser(updateDto.getOwnerId(), entity.getOwnerId()))
                .filter(botEntity -> checkBotVersion(botEntity, updateDto.getUpdateAt()))
                .map(botEntity -> updateBotInfo(
                        botEntity,
                        updateDto.getDisplayName(),
                        updateDto.getDescription()
                ))
                .flatMap(botRepository::save)
                .then();
    }

    @Override
    public Mono<Void> deleteBot(MessageDto deleteDto) {

        return botRepository.findByUsername(deleteDto.getUsername())
                .filter(entity -> checkUser(deleteDto.getOwnerId(), entity.getOwnerId()))
                .filter(this::checkBotAvailability)
                .map(this::deleteBot)
                .flatMap(botRepository::save)
                .then();
    }

    @Override
    public Mono<BotDto> getBotInfo(String ownerId, String username) {

        return botRepository.findByUsername(username)
                .filter(entity -> checkUser(ownerId, entity.getOwnerId()))
                .filter(entity -> Objects.isNull(entity.getDeleteAt()))
                .map(botConverter::convert);
    }

    @Override
    public Flux<BotDto> getBotsList() {

        return botRepository.findAll()
                .map(botConverter::convert);
    }

    private BotEntity createNewBotEntity(BotEntity entity) {
        entity.setUserId(UUID.randomUUID().toString());
        entity.setCreateAt(BigInteger.valueOf(System.currentTimeMillis()));

        return entity;
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

    private boolean checkUser(String principal, String ownerId) {

        return StringUtils.equals(principal, ownerId);
    }

    private boolean checkBotVersion(BotEntity entity, BigInteger updateAt) {

        return Objects.equals(entity.getUpdateAt(), updateAt);
    }

    private boolean checkBotAvailability(BotEntity entity) {

        return Objects.isNull(entity.getDeleteAt());
    }

}
