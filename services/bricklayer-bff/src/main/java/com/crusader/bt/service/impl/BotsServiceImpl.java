package com.crusader.bt.service.impl;

import com.crusader.bt.client.ConstructorClient;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.mapper.MessageDtoMapper;
import com.crusader.bt.message.MessageProducer;
import com.crusader.bt.service.BotsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotsServiceImpl implements BotsService {

    private final ConstructorClient constructorClient;
    private final MessageProducer messageProducer;
    private final MessageDtoMapper messageDtoMapper;

    @Override
    public Mono<BotDto> createBot(Principal principal, BotDto createRequest) {

        return Mono.just(
                        enrichBotInfo(principal, createRequest)
                )
                .flatMap(constructorClient::createBot)
                .map(bto -> {
                    MessageDto messageDto = messageDtoMapper.mapToMessageDto(bto);
                    messageProducer.sendConstructorMessage(messageDto, "create");
                    return bto;
                });
    }

    @Override
    public Mono<BotDto> updateBotInfo(Principal principal, BotDto updateDto) {

        return Mono.just(
                        enrichBotInfo(principal, updateDto)
                )
                .flatMap(constructorClient::updateBot);
    }

    @Override
    public Mono<BotDto> deleteBot(Principal principal, String username) {

        return constructorClient.deleteBot(
                principal.getName(), username
        );
    }

    @Override
    public Mono<BotDto> getBotInfo(Principal principal, String username) {

        return constructorClient.getBot(
                principal.getName(), username
        );
    }

    private BotDto enrichBotInfo(Principal principal, BotDto inboundRequest) {

        inboundRequest.setOwnerId(principal.getName());

        return inboundRequest;
    }

}
