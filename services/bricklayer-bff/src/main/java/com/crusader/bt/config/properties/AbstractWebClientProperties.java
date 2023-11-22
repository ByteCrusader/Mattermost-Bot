package com.crusader.bt.config.properties;

import lombok.Data;


/**
 * Abstract class for client params
 */
@Data
public abstract class AbstractWebClientProperties {

    /**
     * Base URL params
     */
    private String scheme;
    private String host;
    private String port;
    private String path;

    /**
     * Timeout params
     */
    private Long connectionTimeout;
    private Long receiveTimeout;

}
