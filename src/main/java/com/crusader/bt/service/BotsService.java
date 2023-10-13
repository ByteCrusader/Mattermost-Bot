package com.crusader.bt.service;


import com.crusader.bt.dto.BotDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BotsService {

    /**
     * Create Bot entity into system
     *
     * @param username    bot username
     * @param displayName bot display name
     * @param description bot description
     * @param ownerId     user id of the user that currently owns this bot
     * @return created bot
     */
    Mono<BotDto> createBot(String username, String displayName, String description, String ownerId);

    /**
     * Update information about bot
     *
     * @param username    bot username
     * @param displayName bot display name
     * @param description bot description
     * @return resulted bot
     */
    Mono<BotDto> updateBotInfo(String username, String displayName, String description);

    /**
     * Delete bot from system
     *
     * @param username bot username
     * @return resulted bot
     */
    Mono<BotDto> deleteBot(String username);

    /**
     * Get bot information by username
     *
     * @param username bot username
     * @return founded bot
     */
    Mono<BotDto> getBotInfo(String username);

    /**
     * Get list of bots into current team
     *
     * @return bots list info
     */
    Flux<BotDto> getBotsList();

}
