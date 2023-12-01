package com.crusader.bt.controller;

import com.crusader.bt.constant.RouteConstants;
import com.crusader.bt.dto.BotDto;
import com.crusader.bt.dto.StatusDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * PUT : create new bot account
     */
    @PutMapping
    @Operation(
            operationId = "createBot",
            summary = "Create new Bot account",
            description = "Create new Bot account into system",
            tags = {"bots"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created bot information if successful", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatusDto.class)))
                    })
            }
    )
    public Mono<ResponseEntity<StatusDto>> createBot(
            @Parameter(name = "createBotBody", description = "Object with the bot info") @RequestBody BotDto createDto,
            ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
                .flatMap(principal -> botsService.createBot(
                        principal, createDto
                ))
                .map(ResponseEntity::ok);
    }

    /**
     * POST : update info about bot
     */
    @PostMapping
    @Operation(
            operationId = "updateBot",
            summary = "Update Bot account",
            description = "Update Bot account information into system",
            tags = {"bots"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Update bot information if successful", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatusDto.class)))
                    })
            }
    )
    public Mono<ResponseEntity<StatusDto>> updateBot(
            @Parameter(name = "updateBotBody", description = "Object with the bot info") @RequestBody BotDto updateDto,
            ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
                .flatMap(principal -> botsService.updateBotInfo(
                        principal, updateDto
                ))
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE : delete bot account from system
     */
    @DeleteMapping(RouteConstants.IDENTITY_API)
    @Operation(
            operationId = "deleteBot",
            summary = "Delete bot from system",
            description = "Delete information about bot from system",
            tags = {"bots"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resulted bot info if successful", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatusDto.class)))
                    })
            }
    )
    public Mono<ResponseEntity<StatusDto>> deleteBot(
            @Parameter(name = "username", description = "Name of bot", in = ParameterIn.QUERY) @PathVariable String username,
            ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
                .flatMap(principal -> botsService.deleteBot(principal, username))
                .map(ResponseEntity::ok);
    }

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
            @Parameter(name = "username", description = "Name of bot", in = ParameterIn.QUERY) @PathVariable String username,
            ServerWebExchange exchange
    ) {
        return exchange.getPrincipal()
                .flatMap(principal -> botsService.getBotInfo(principal, username))
                .map(ResponseEntity::ok);
    }

}
