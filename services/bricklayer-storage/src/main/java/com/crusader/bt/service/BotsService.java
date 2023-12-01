package com.crusader.bt.service;


import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.MessageDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BotsService {

    /**
     * Create Bot entity into system
     *
     * @param ownerId     user id of the user that currently owns this bot
     * @param username    bot username
     * @param displayName bot display name
     * @param description bot description
     * @return created bot
     */
    Mono<Void> createBot(MessageDto createDto);

    /**
     * Update information about bot
     *
     * @param ownerId
     * @param username    bot username
     * @param displayName bot display name
     * @param description bot description
     * @param updateAt    bot last update time
     * @return resulted bot
     */
    Mono<Void> updateBotInfo(MessageDto updateDto);

    /**
     * Delete bot from system
     *
     * @param ownerId
     * @param username bot username
     * @return resulted bot
     */
    Mono<Void> deleteBot(MessageDto updateDto);

    /**
     * Get bot information by username
     *
     * @param ownerId
     * @param username bot username
     * @return founded bot
     */
    Mono<BotDto> getBotInfo(String ownerId, String username);

    /**
     * Get list of bots into current team
     *
     * @return bots list info
     */
    Flux<BotDto> getBotsList();

}
