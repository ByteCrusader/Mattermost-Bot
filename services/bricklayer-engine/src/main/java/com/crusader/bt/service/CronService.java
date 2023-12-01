package com.crusader.bt.service;

import com.crusader.bt.dto.MessageDto;
import reactor.core.publisher.Mono;

/**
 * Cron job service
 */
public interface CronService {

    /**
     * Create new cron job
     *
     * @return status dto
     */
    Mono<Void> createCronJob(MessageDto createDto);

}
