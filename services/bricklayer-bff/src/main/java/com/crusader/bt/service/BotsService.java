package com.crusader.bt.service;


import com.crusader.bt.dto.BotDto;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface BotsService {

    /**
     * Create Bot entity into system
     *
     * @param ownerId       user id of the user that currently owns this bot
     * @param createRequest
     * @return created bot
     */
    Mono<BotDto> createBot(Principal principal, BotDto createRequest);

    /**
     * Update information about bot
     *
     * @param updateDto@return resulted bot
     */
    Mono<BotDto> updateBotInfo(Principal principal, BotDto updateDto);

    /**
     * Delete bot from system
     *
     * @param username bot username
     * @return resulted bot
     */
    Mono<BotDto> deleteBot(Principal principal, String username);

    /**
     * Get bot information by username
     *
     * @param username bot username
     * @return founded bot
     */
    Mono<BotDto> getBotInfo(Principal principal, String username);

}
