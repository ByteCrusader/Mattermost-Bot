package com.crusader.bt.service.impl;

import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Override
    public Mono<Void> successCreateBot(MessageDto createRequest) {
        return Mono.just(createRequest)
                .doOnSuccess(result -> log.info(
                        "Send to client UI message with successful case of bot creation: {}",
                        result
                ))
                .then();
    }

    @Override
    public Mono<Void> failedCreateBot(MessageDto createRequest) {
        return Mono.just(createRequest)
                .doOnSuccess(result -> log.info(
                        "Send to client UI message with unsuccessful case of bot creation: {}",
                        result
                ))
                .then();
    }

    @Override
    public Mono<Void> successUpdateBot(MessageDto updateRequest) {
        return Mono.just(updateRequest)
                .doOnSuccess(result -> log.info(
                        "Send to client UI message with successful case of bot editing: {}",
                        result
                ))
                .then();
    }

    @Override
    public Mono<Void> failedUpdateBot(MessageDto updateRequest) {
        return Mono.just(updateRequest)
                .doOnSuccess(result -> log.info(
                        "Send to client UI message with unsuccessful case of bot editing: {}",
                        result
                ))
                .then();
    }

    @Override
    public Mono<Void> successDeleteBot(MessageDto deleteRequest) {
        return Mono.just(deleteRequest)
                .doOnSuccess(result -> log.info(
                        "Send to client UI message with successful case of bot deleting: {}",
                        result
                ))
                .then();
    }

    @Override
    public Mono<Void> failedDeleteBot(MessageDto deleteRequest) {
        return Mono.just(deleteRequest)
                .doOnSuccess(result -> log.info(
                        "Send to client UI message with unsuccessful case of bot deleting: {}",
                        result
                ))
                .then();
    }
}
