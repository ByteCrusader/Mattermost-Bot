package com.crusader.bt.service;


import com.crusader.bt.dto.BotDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.security.Principal;

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
    Mono<BotDto> createBot(Principal principal, String username, String displayName, String description);

    /**
     * Update information about bot
     *
     * @param username    bot username
     * @param displayName bot display name
     * @param description bot description
     * @param updateAt    bot last update time
     * @return resulted bot
     */
    Mono<BotDto> updateBotInfo(Principal principal,
                               String username,
                               String displayName,
                               String description,
                               BigInteger updateAt);

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

    /**
     * Get list of bots into current team
     *
     * @return bots list info
     */
    Flux<BotDto> getBotsList();

}
