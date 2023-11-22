package com.crusader.bt.client.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingClientFilter {

    public ExchangeFilterFunction logRequestFunction() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request from constructor service to {} has been sent", clientRequest.url().getPath());
            return Mono.just(clientRequest);
        });
    }

    public ExchangeFilterFunction logResponseHttpStatusFunction() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Received response into constructor service with status {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }

}
