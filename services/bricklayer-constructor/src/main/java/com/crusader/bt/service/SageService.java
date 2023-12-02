package com.crusader.bt.service;

import com.crusader.bt.dto.MessageDto;
import reactor.core.publisher.Mono;

public interface SageService {

    /**
     * Successful create Bot entity into system
     *
     * @param createRequest user id of the user that currently owns this bot
     * @return created bot
     */
    Mono<Void> successCreateBot(MessageDto createRequest);

    /**
     * Successful update information about bot
     *
     * @param updateRequest
     * @return resulted bot
     */
    Mono<Void> successUpdateBot(MessageDto updateRequest);

    /**
     * Successful delete bot from system
     *
     * @return resulted bot
     */
    Mono<Void> successDeleteBot(MessageDto deleteRequest);

    /**
     * Successful create cron job into system
     *
     * @return resulted bot
     */
    Mono<Void> successCreateJob(MessageDto createRequest);

}
