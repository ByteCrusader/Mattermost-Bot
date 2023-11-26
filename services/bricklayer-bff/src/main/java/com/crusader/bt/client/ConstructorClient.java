package com.crusader.bt.client;

import com.crusader.bt.dto.BotDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Client for constructor service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConstructorClient {

    private static final String BOTS_BASE_ROUTE = "/bot";
    private static final String IDENTITY_API = "/{ownerId}/{username}";

    private final WebClient constructorWebClient;
    private final OutboundWebClient outboundWebClient;

    public Mono<BotDto> createBot(BotDto createRequest) {

        return outboundWebClient.sendPutRequest(
                constructorWebClient,
                BOTS_BASE_ROUTE,
                Map.of(),
                createRequest,
                BotDto.class
        );
    }

    public Mono<BotDto> updateBot(BotDto updateRequest) {

        return outboundWebClient.sendPostRequest(
                constructorWebClient,
                BOTS_BASE_ROUTE,
                Map.of(),
                updateRequest,
                BotDto.class
        );
    }

    public Mono<BotDto> deleteBot(String ownerId, String botName) {

        return outboundWebClient.sendDeleteRequest(
                constructorWebClient,
                BOTS_BASE_ROUTE + IDENTITY_API,
                Map.of("ownerId", ownerId, "username", botName),
                BotDto.class
        );
    }

    public Mono<BotDto> getBot(String ownerId, String botName) {

        return outboundWebClient.sendGetRequest(
                constructorWebClient,
                BOTS_BASE_ROUTE + IDENTITY_API,
                Map.of("ownerId", ownerId, "username", botName),
                BotDto.class
        );
    }

}
