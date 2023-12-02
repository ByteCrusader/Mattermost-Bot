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
        log.info("Bot service start processing create bot from message: {}", createRequest);
        return Mono.just(messageDtoMapper.mapToBotDto(createRequest))
                .flatMap(mattermostClient::createBot)
                .flatMap(botDto -> sendQueueMessage(botDto, MessageEventType.PROCESSING_CREATE_BOT_EVENT))
                .then(messageProducer.sendGenericMessage(createRequest, MessageEventType.PROCESSING_CREATE_JOB_EVENT));
    }

    @Override
    public Mono<Void> updateBotInfo(MessageDto updateRequest) {
        log.info("Bot service start processing update bot from message: {}", updateRequest);
        return Mono.just(messageDtoMapper.mapToBotDto(updateRequest))
                .flatMap(botDto -> sendQueueMessage(botDto, MessageEventType.PROCESSING_EDIT_BOT_EVENT));
    }

    @Override
    public Mono<Void> deleteBot(MessageDto deleteRequest) {
        log.info("Bot service start processing delete bot from message: {}", deleteRequest);
        return messageProducer.sendGenericMessage(deleteRequest, MessageEventType.PROCESSING_DELETE_BOT_EVENT);
    }

    @Override
    public Mono<BotDto> getBotInfo(String ownerId, String username) {
        log.info("Bot service start processing get bot info with bot username: {}", username);
        return storageClient.getBot(ownerId, username);
    }

    private Mono<Void> sendQueueMessage(BotDto createRequest, MessageEventType eventType) {
        MessageDto messageDto = messageDtoMapper.mapToMessageDto(createRequest);

        return messageProducer.sendGenericMessage(messageDto, eventType);
    }

}
