package com.crusader.bt.service;


import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.MessageDto;
import reactor.core.publisher.Mono;

public interface BotsService {

    /**
     * Create Bot entity into system
     *
     * @param createRequest user id of the user that currently owns this bot
     * @return created bot
     */
    Mono<Void> createBot(MessageDto createRequest);

    /**
     * Update information about bot
     *
     * @param updateRequest
     * @return resulted bot
     */
    Mono<Void> updateBotInfo(MessageDto updateRequest);

    /**
     * Delete bot from system
     *
     * @param ownerId
     * @param username bot username
     * @return resulted bot
     */
    Mono<Void> deleteBot(MessageDto deleteRequest);

    /**
     * Get bot information by username
     *
     * @param ownerId
     * @param username bot username
     * @return founded bot
     */
    Mono<BotDto> getBotInfo(String ownerId, String username);

}
