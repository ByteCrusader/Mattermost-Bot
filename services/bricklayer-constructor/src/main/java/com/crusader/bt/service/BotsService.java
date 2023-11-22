package com.crusader.bt.service;


import com.crusader.bt.dto.BotDto;
import reactor.core.publisher.Mono;

public interface BotsService {

    /**
     * Create Bot entity into system
     *
     * @param createRequest user id of the user that currently owns this bot
     * @return created bot
     */
    Mono<BotDto> createBot(BotDto createRequest);

    /**
     * Update information about bot
     *
     * @param updateRequest
     * @return resulted bot
     */
    Mono<BotDto> updateBotInfo(BotDto updateRequest);

    /**
     * Delete bot from system
     *
     * @param ownerId
     * @param username bot username
     * @return resulted bot
     */
    Mono<BotDto> deleteBot(String ownerId, String username);

    /**
     * Get bot information by username
     *
     * @param ownerId
     * @param username bot username
     * @return founded bot
     */
    Mono<BotDto> getBotInfo(String ownerId, String username);

}
