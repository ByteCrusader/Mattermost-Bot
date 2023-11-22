package com.crusader.bt.client;

import com.crusader.bt.dto.BotDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

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

    public BotDto createBot(BotDto createRequest) {

        return outboundWebClient.sendPostRequest(
                mattermostWebClient,
                BOTS_BASE_ROUTE,
                Map.of(),
                createRequest,
                BotDto.class
        );
    }

    public List<BotDto> getBots() {

        return outboundWebClient.sendGetRequestWithListResponse(
                mattermostWebClient,
                BOTS_BASE_ROUTE,
                Map.of(),
                BotDto.class
        );
    }

}
