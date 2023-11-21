package com.crusader.bt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDto {

    /**
     * The user name into system
     */
    @JsonProperty("client_id")
    private String clientId;
    /**
     * The user password into system
     */
    @JsonProperty("client_secret")
    private String clientSecret;
    /**
     * The grant type for login into system
     */
    @JsonProperty("grant_type")
    private String grantType;

}

