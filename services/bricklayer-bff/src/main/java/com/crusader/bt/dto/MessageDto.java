package com.crusader.bt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * Message dto for internal integrity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {

    /**
     * The user id of the associated user entry
     */
    @JsonProperty("user_id")
    private String userId;
    /**
     * The time in milliseconds a bot was created
     */
    @JsonProperty("create_at")
    private BigInteger createAt;
    /**
     * The time in milliseconds a bot was last updated
     */
    @JsonProperty("update_at")
    private BigInteger updateAt;
    /**
     * The time in milliseconds a bot was deleted
     */
    @JsonProperty("delete_at")
    private BigInteger deleteAt;
    @JsonProperty("username")
    private String username;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("description")
    private String description;
    /**
     * The user id of the user that currently owns this bot
     */
    @JsonProperty("owner_id")
    private String ownerId;
    /**
     * The job id of the associated cron job
     */
    @JsonProperty("job_id")
    private String jobId;
    /**
     * The job message of the associated cron job
     */
    @JsonProperty("job_message")
    private String jobMessage;
    /**
     * The job cron of the associated cron job
     */
    @JsonProperty("job_cron")
    private String jobCron;
    /**
     * The is associated cron job enable
     */
    @JsonProperty("job_enable")
    private String jobEnable;

}

