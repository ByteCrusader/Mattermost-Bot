package com.crusader.bt.service.impl;

import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.enums.MessageEventType;
import com.crusader.bt.message.MessageProducer;
import com.crusader.bt.service.SageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SageServiceImpl implements SageService {

    private final MessageProducer messageProducer;

    @Override
    public Mono<Void> successCreateBot(MessageDto createRequest) {
        return messageProducer.sendGenericMessage(createRequest, MessageEventType.COMPLETE_CREATE_BOT_EVENT);
    }

    @Override
    public Mono<Void> failedCreateBot(MessageDto createRequest) {
        return null;
    }

    @Override
    public Mono<Void> successUpdateBot(MessageDto updateRequest) {
        return messageProducer.sendGenericMessage(updateRequest, MessageEventType.COMPLETE_EDIT_BOT_EVENT);
    }

    @Override
    public Mono<Void> failedUpdateBot(MessageDto updateRequest) {
        return null;
    }

    @Override
    public Mono<Void> successDeleteBot(MessageDto deleteRequest) {
        return messageProducer.sendGenericMessage(deleteRequest, MessageEventType.COMPLETE_DELETE_BOT_EVENT);
    }

    @Override
    public Mono<Void> failedDeleteBot(MessageDto deleteRequest) {
        return null;
    }
}
