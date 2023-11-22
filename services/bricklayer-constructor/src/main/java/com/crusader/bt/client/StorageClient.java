package com.crusader.bt.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Client for storage service
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StorageClient {

    private final WebClient storageWebClient;
    private final OutboundWebClient outboundWebClient;


}
