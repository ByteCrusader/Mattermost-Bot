package com.crusader.bt.client;

import com.crusader.bt.dto.BotDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Client for storage service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StorageClient {

    private static final String BOTS_BASE_ROUTE = "/bot";
    private static final String IDENTITY_API = "/{ownerId}/{username}";

    private final WebClient storageWebClient;
    private final OutboundWebClient outboundWebClient;

    public Mono<BotDto> createBot(BotDto createRequest) {

        return outboundWebClient.sendPutRequest(
                storageWebClient,
                BOTS_BASE_ROUTE,
                Map.of(),
                createRequest,
                BotDto.class
        );
    }

    public Mono<BotDto> updateBot(BotDto updateRequest) {

        return outboundWebClient.sendPostRequest(
                storageWebClient,
                BOTS_BASE_ROUTE,
                Map.of(),
                updateRequest,
                BotDto.class
        );
    }

    public Mono<BotDto> deleteBot(String ownerId, String botName) {

        return outboundWebClient.sendDeleteRequest(
                storageWebClient,
                BOTS_BASE_ROUTE + IDENTITY_API,
                Map.of("ownerId", ownerId, "username", botName),
                BotDto.class
        );
    }

    public Mono<BotDto> getBot(String ownerId, String botName) {

        return outboundWebClient.sendGetRequest(
                storageWebClient,
                BOTS_BASE_ROUTE + IDENTITY_API,
                Map.of("ownerId", ownerId, "username", botName),
                BotDto.class
        );
    }

}
