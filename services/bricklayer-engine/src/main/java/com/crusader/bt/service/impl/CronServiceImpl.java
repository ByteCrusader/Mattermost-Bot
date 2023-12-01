package com.crusader.bt.service.impl;

import com.crusader.bt.constant.StatusConstants;
import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.dto.StatusDto;
import com.crusader.bt.service.CronService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CronServiceImpl implements CronService {

    @Override
    public Mono<Void> createCronJob(MessageDto createDto) {
        return Mono.just(
                createResponse(
                        StatusConstants.OK_STATUS
                )
        ).then();
    }

    /**
     * Build service response dto
     *
     * @param status result status
     * @return result dto
     */
    private StatusDto createResponse(String status) {

        StatusDto response = new StatusDto();
        response.setStatus(status);

        return response;
    }
}
