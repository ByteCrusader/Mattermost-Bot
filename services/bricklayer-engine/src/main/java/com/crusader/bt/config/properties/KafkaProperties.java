package com.crusader.bt.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private Boolean enabled;
    private boolean autoCommit = false;
    private String bootstrap;
    private String topic;
    private String securityProtocol;
    private String saslMechanism;
    private String saslJaasUsername;
    private String saslJaasPassword;
    private String sessionTimeoutMs;
    private String heartbeatIntervalMs;
    private String maxPollRecords;
    private String maxPollIntervalMs;
    private String offsetReset;

}
