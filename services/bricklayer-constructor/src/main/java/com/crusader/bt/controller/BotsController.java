package com.crusader.bt.controller;

import com.crusader.bt.constant.RouteConstants;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.service.BotsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteConstants.BOTS_API)
public class BotsController {

    private final BotsService botsService;

    /**
     * GET : get information about bot
     */
    @GetMapping(RouteConstants.IDENTITY_API)
    @Operation(
            operationId = "getBot",
            summary = "Get info about bot account",
            description = "Get information about bot account from system",
            tags = {"health"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bot information if successful", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BotDto.class)))
                    })
            }
    )
    public Mono<ResponseEntity<BotDto>> getBot(
            @Parameter(name = "ownerId", description = "Name of bot's owner", in = ParameterIn.QUERY) @PathVariable String ownerId,
            @Parameter(name = "username", description = "Name of bot", in = ParameterIn.QUERY) @PathVariable String username,
            ServerWebExchange exchange
    ) {
        return botsService.getBotInfo(ownerId, username)
                .map(ResponseEntity::ok);
    }

}
