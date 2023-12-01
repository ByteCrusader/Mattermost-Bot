package com.crusader.bt.service.impl;

import com.crusader.bt.client.MattermostClient;
import com.crusader.bt.client.StorageClient;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.enums.MessageEventType;
import com.crusader.bt.mapper.MessageDtoMapper;
import com.crusader.bt.message.MessageProducer;
import com.crusader.bt.service.BotsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotsServiceImpl implements BotsService {

    private final MattermostClient mattermostClient;
    private final StorageClient storageClient;
    private final MessageDtoMapper messageDtoMapper;
    private final MessageProducer messageProducer;

    @Override
    public Mono<Void> createBot(MessageDto createRequest) {

        return Mono.just(messageDtoMapper.mapToBotDto(createRequest))
                .flatMap(mattermostClient::createBot)
                .flatMap(botDto -> sendStorageMessage(botDto, MessageEventType.CREATE_BOT_EVENT))
                .then(sendEngineMessage(createRequest, MessageEventType.CREATE_JOB_EVENT));
    }

    @Override
    public Mono<Void> updateBotInfo(MessageDto updateRequest) {

        return Mono.just(messageDtoMapper.mapToBotDto(updateRequest))
                .flatMap(botDto -> sendStorageMessage(botDto, MessageEventType.EDIT_BOT_EVENT));
    }

    @Override
    public Mono<Void> deleteBot(MessageDto deleteRequest) {

        return sendStorageMessage(deleteRequest, MessageEventType.DELETE_BOT_EVENT);
    }

    @Override
    public Mono<BotDto> getBotInfo(String ownerId, String username) {

        return storageClient.getBot(ownerId, username);
    }

    private Mono<Void> sendStorageMessage(BotDto createRequest, MessageEventType eventType) {
        MessageDto messageDto = messageDtoMapper.mapToMessageDto(createRequest);

        return sendStorageMessage(messageDto, eventType);
    }

    private Mono<Void> sendStorageMessage(MessageDto messageDto, MessageEventType eventType) {

        try {
            return messageProducer.sendStorageMessage(messageDto, eventType);
        } catch (Exception ex) {
            //todo add sage exception flow
        }

        return Mono.empty();
    }


    private Mono<Void> sendEngineMessage(MessageDto createRequest, MessageEventType eventType) {

        try {
            return messageProducer.sendEngineMessage(createRequest, eventType);
        } catch (Exception ex) {
            //todo add sage exception flow
        }

        return Mono.empty();
    }

}
