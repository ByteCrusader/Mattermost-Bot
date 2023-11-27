package com.crusader.bt.service;

import com.crusader.bt.dto.CronDto;
import com.crusader.bt.dto.StatusDto;
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
    Mono<StatusDto> checkStatus(CronDto createDto);

}
