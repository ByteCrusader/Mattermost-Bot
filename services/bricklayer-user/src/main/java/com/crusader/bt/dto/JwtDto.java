package com.crusader.bt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Jwt token dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDto {

    /**
     * Access token into system
     */
    @JsonProperty("access_token")
    private String token;
    /**
     * Token Expired date
     */
    @JsonProperty("expires_in")
    private Long exp;
    /**
     * Token type
     */
    @JsonProperty("token_type")
    private String type = "Bearer";

}

