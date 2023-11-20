package com.crusader.bt.controller;

import com.crusader.bt.constant.RouteConstants;
import com.crusader.bt.dto.UserDto;
import com.crusader.bt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(RouteConstants.USERS_API)
public class UserController {

    private final UserService userService;

    /**
     * PUT : register new user for system
     */
    @PutMapping(RouteConstants.REGISTER_API)
    @Operation(
            operationId = "registerUser",
            summary = "Register new user",
            description = "Register new user into system",
            tags = {"user"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created user info if successful", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
                    })
            }
    )
    public Mono<ResponseEntity<UserDto>> registerUser(
            @Parameter(name = "createUserBody", description = "Object with the user info") @RequestBody UserDto createDto,
            ServerWebExchange exchange
    ) {
        return userService.registerNewUser(createDto)
                .map(ResponseEntity::ok);
    }

    /**
     * POST : login user into system
     */
    @PostMapping(RouteConstants.LOGIN_API)
    @Operation(
            operationId = "loginUser",
            summary = "Login user",
            description = "Login user into system",
            tags = {"user"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "JWT token if successful", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    })
            }
    )
    public Mono<ResponseEntity<String>> loginUser(
            @Parameter(name = "userLoginBody", description = "Object with the user info") @RequestBody UserDto loginDto,
            ServerWebExchange exchange
    ) {

        return userService.loginUser(loginDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
