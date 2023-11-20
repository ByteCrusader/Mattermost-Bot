package com.crusader.bt.service;

import com.crusader.bt.dto.StatusDto;
import reactor.core.publisher.Mono;

/**
 * Application health service
 */
public interface HealthService {

    /**
     * Check application health status
     *
     * @return check dto
     */
    Mono<StatusDto> checkStatus();

}
