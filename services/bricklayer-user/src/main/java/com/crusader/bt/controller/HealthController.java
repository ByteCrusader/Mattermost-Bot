package com.crusader.bt.controller;

import com.crusader.bt.constant.RouteConstants;
import com.crusader.bt.dto.StatusDto;
import com.crusader.bt.service.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteConstants.HEALTH_API)
public class HealthController {

    private final HealthService healthService;

    /**
     * GET /health : Get application health status
     */
    @GetMapping
    @Operation(
            operationId = "getHealthStatus",
            summary = "Get application health status",
            description = "Get an application health status",
            tags = {"health"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Application status checked successful", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatusDto.class)))
                    })
            }
    )
    public Mono<ResponseEntity<StatusDto>> checkHealthStatus() {
        return healthService.checkStatus()
                .map(ResponseEntity::ok);
    }
}
