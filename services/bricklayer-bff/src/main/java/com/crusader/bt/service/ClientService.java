package com.crusader.bt.service;

import com.crusader.bt.dto.MessageDto;
import reactor.core.publisher.Mono;

public interface ClientService {

    /**
     * Successful create Bot entity into system
     *
     * @param createRequest user id of the user that currently owns this bot
     * @return created bot
     */
    Mono<Void> successCreateBot(MessageDto createRequest);

    /**
     * Create Bot entity into system has failed
     *
     * @param createRequest user id of the user that currently owns this bot
     * @return created bot
     */
    Mono<Void> failedCreateBot(MessageDto createRequest);


    /**
     * Successful update information about bot
     *
     * @param updateRequest
     * @return resulted bot
     */
    Mono<Void> successUpdateBot(MessageDto updateRequest);

    /**
     * Update information about bot has failed
     *
     * @param updateRequest
     * @return resulted bot
     */
    Mono<Void> failedUpdateBot(MessageDto updateRequest);

    /**
     * Successful delete bot from system
     *
     * @return resulted bot
     */
    Mono<Void> successDeleteBot(MessageDto deleteRequest);

    /**
     * Delete bot from system has failed
     *
     * @return resulted bot
     */
    Mono<Void> failedDeleteBot(MessageDto deleteRequest);

}
