package com.crusader.bt.config;

import com.crusader.bt.client.filter.LoggingClientFilter;
import com.crusader.bt.config.properties.AbstractWebClientProperties;
import com.crusader.bt.config.properties.ConstructorProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ConstructorProperties.class})
public class WebClientConfiguration {

    /**
     * Constructor service Web Client
     */
    @Bean("constructorWebClient")
    WebClient webClient(ConstructorProperties constructorProperties,
                        LoggingClientFilter loggingClientFilter) {

        return WebClient.builder()
                .clientConnector(configClientConnection(constructorProperties))
                .baseUrl(createBaseUrl(constructorProperties))
                .filter(loggingClientFilter.logRequestFunction())
                .filter(loggingClientFilter.logResponseHttpStatusFunction())
                .build();
    }

    /**
     * Build connect for Web Client
     */
    private ClientHttpConnector configClientConnection(AbstractWebClientProperties abstractWebClientProperties) {

        HttpClient httpClient = HttpClient.create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        abstractWebClientProperties.getConnectionTimeout().intValue()
                )
                .doOnConnected(conn -> conn
                        .addHandlerLast(
                                new ReadTimeoutHandler(
                                        abstractWebClientProperties.getReceiveTimeout(),
                                        TimeUnit.MILLISECONDS
                                )
                        )
                        .addHandlerLast(
                                new WriteTimeoutHandler(
                                        abstractWebClientProperties.getReceiveTimeout(),
                                        TimeUnit.MILLISECONDS
                                )
                        )
                );

        return new ReactorClientHttpConnector(
                httpClient.wiretap(true)
        );
    }

    /**
     * Build base URL
     */
    protected String createBaseUrl(AbstractWebClientProperties properties) {
        return UriComponentsBuilder.newInstance()
                .scheme(properties.getScheme())
                .host(properties.getHost())
                .port(properties.getPort())
                .path(properties.getPath())
                .build()
                .encode()
                .toUriString();
    }

}
