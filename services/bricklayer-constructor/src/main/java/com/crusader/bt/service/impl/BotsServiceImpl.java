package com.crusader.bt.service.impl;

import com.crusader.bt.client.EngineClient;
import com.crusader.bt.client.MattermostClient;
import com.crusader.bt.client.StorageClient;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.CronDto;
import com.crusader.bt.service.BotsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotsServiceImpl implements BotsService {

    private final MattermostClient mattermostClient;
    private final StorageClient storageClient;
    private final EngineClient engineClient;

    @Override
    public Mono<BotDto> createBot(BotDto createRequest) {

        return mattermostClient.createBot(createRequest)
                .flatMap(storageClient::createBot)
                .map(botDto -> Pair.of(botDto, CronDto.builder().cron("some cron").message("some message").build()))
                .flatMap(pair ->
                        engineClient.createJob(pair.getRight())
                                .map(statusDto -> pair.getLeft())
                );
    }

    @Override
    public Mono<BotDto> updateBotInfo(BotDto updateRequest) {

        return storageClient.updateBot(updateRequest);
    }

    @Override
    public Mono<BotDto> deleteBot(String ownerId, String username) {

        return storageClient.deleteBot(ownerId, username);
    }

    @Override
    public Mono<BotDto> getBotInfo(String ownerId, String username) {

        return storageClient.getBot(ownerId, username);
    }

}
