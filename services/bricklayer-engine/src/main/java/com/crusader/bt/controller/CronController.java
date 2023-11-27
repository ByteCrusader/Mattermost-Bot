package com.crusader.bt.controller;

import com.crusader.bt.constant.RouteConstants;
import com.crusader.bt.dto.CronDto;
import com.crusader.bt.dto.StatusDto;
import com.crusader.bt.service.CronService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteConstants.CRON_API)
public class CronController {

    private final CronService cronService;

    /**
     * PUT /cron : Create new cron job
     */
    @PutMapping
    @Operation(
            operationId = "createCronJob",
            summary = "Create new cron job",
            description = "Create new cron job for specific bot into system",
            tags = {"cron"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "New cron job init successful", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatusDto.class)))
                    })
            }
    )
    public Mono<ResponseEntity<StatusDto>> checkHealthStatus(
            @Parameter(name = "createBody", description = "Object with the cron job info") @RequestBody CronDto createDto,
            ServerWebExchange exchange
    ) {
        return cronService.checkStatus(createDto)
                .map(ResponseEntity::ok);
    }
}
