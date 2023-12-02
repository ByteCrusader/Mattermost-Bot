package com.crusader.bt.service.impl;

import com.crusader.bt.constant.StatusConstants;
import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.dto.StatusDto;
import com.crusader.bt.enums.MessageEventType;
import com.crusader.bt.message.MessageProducer;
import com.crusader.bt.service.CronService;
import com.crusader.bt.utils.MqUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CronServiceImpl implements CronService {

    private final MessageProducer messageProducer;

    @Override
    public Mono<Void> createCronJob(MessageDto createDto) {
        return Mono.just(
                        createResponse(
                                StatusConstants.OK_STATUS
                        )
                )
                .doOnSuccess(result -> messageProducer.sendConstructorMessage(
                        createDto,
                        MessageEventType.COMPLETE_JOB_EVENT
                ))
                .doOnError(
                        MqUtil::errorPredicate,
                        exc -> messageProducer.sendConstructorMessage(createDto, MessageEventType.FAIL_JOB_EVENT)
                )
                .onErrorContinue(
                        MqUtil::errorPredicate,
                        (exc, val) -> log.info(
                                "Event dropped. Application exception into customer queue consumer : {} - {} ",
                                exc.toString(),
                                exc.getLocalizedMessage()
                        )
                )
                .then();
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
