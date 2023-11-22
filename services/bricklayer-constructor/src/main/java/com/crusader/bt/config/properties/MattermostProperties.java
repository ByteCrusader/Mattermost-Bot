package com.crusader.bt.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Web config for mattermost service client.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mtmt")
public class MattermostProperties extends AbstractWebClientProperties {

}

