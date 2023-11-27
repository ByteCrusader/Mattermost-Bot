package com.crusader.bt.client;

import com.crusader.bt.dto.CronDto;
import com.crusader.bt.dto.StatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Client for engine service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EngineClient {

    private static final String JOBS_BASE_ROUTE = "/cron";

    private final WebClient engineWebClient;
    private final OutboundWebClient outboundWebClient;

    public Mono<StatusDto> createJob(CronDto createRequest) {

        return outboundWebClient.sendPutRequest(
                engineWebClient,
                JOBS_BASE_ROUTE,
                Map.of(),
                createRequest,
                StatusDto.class
        );
    }

}
