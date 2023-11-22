package com.crusader.bt.client;

import com.crusader.bt.dto.BotDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Client for mattermost service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MattermostClient {

    private static final String BOTS_BASE_ROUTE = "/bots";

    private final WebClient mattermostWebClient;
    private final OutboundWebClient outboundWebClient;

    public Mono<BotDto> createBot(BotDto createRequest) {

        return Mono.just(
                createNewBotDto(createRequest)
        );
        /** Comment for correct work into env without mattermost
        return outboundWebClient.sendPostRequest(
                mattermostWebClient,
                BOTS_BASE_ROUTE,
                Map.of(),
                createRequest,
                BotDto.class
        );
         */
    }

    public Flux<BotDto> getBots() {

        return Flux.empty();
        /** Comment for correct work into env without mattermost
        return outboundWebClient.sendGetRequestWithListResponse(
                mattermostWebClient,
                BOTS_BASE_ROUTE,
                Map.of(),
                BotDto.class
        );
         */
    }

    private BotDto createNewBotDto(BotDto createRequest) {

        createRequest.setUserId(UUID.randomUUID().toString());
        createRequest.setCreateAt(BigInteger.valueOf(System.currentTimeMillis()));

        return createRequest;
    }

}
