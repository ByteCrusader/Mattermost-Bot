package com.crusader.bt.service.impl;

import com.crusader.bt.client.ConstructorClient;
import com.crusader.bt.constant.StatusConstants;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.MessageDto;
import com.crusader.bt.dto.StatusDto;
import com.crusader.bt.enums.MessageEventType;
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
    public Mono<StatusDto> createBot(Principal principal, BotDto createRequest) {

        return Mono.just(
                        enrichBotInfo(principal, createRequest)
                )
                .map(botDto -> sendRequestMessage(botDto, MessageEventType.CREATE_BOT_EVENT));
    }

    @Override
    public Mono<StatusDto> updateBotInfo(Principal principal, BotDto updateDto) {

        return Mono.just(
                        enrichBotInfo(principal, updateDto)
                )
                .map(botDto -> sendRequestMessage(botDto, MessageEventType.EDIT_BOT_EVENT));
    }

    @Override
    public Mono<StatusDto> deleteBot(Principal principal, String username) {

        return Mono.just(
                        createBotInfo(principal, username)
                )
                .map(botDto -> sendRequestMessage(botDto, MessageEventType.DELETE_BOT_EVENT));
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

    private BotDto createBotInfo(Principal principal, String username) {

        return BotDto.builder()
                .ownerId(principal.getName())
                .username(username)
                .build();
    }

    private StatusDto sendRequestMessage(BotDto createRequest, MessageEventType eventType) {
        MessageDto messageDto = messageDtoMapper.mapToMessageDto(createRequest);

        try {
            messageProducer.sendConstructorMessage(messageDto, eventType);
        } catch (Exception ex) {
            return createResponse(
                    StatusConstants.FAIL_STATUS
            );
        }

        return createResponse(
                StatusConstants.OK_STATUS
        );
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
