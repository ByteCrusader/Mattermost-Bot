package com.crusader.bt.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

/**
 * Generic class for create universal REST requests
 */
@Component
public class OutboundWebClient {

    /**
     * POST request
     */
    protected <T, R> T sendPostRequest(WebClient webClient,
                                       String path,
                                       Map<String, ?> uriVariables,
                                       R request,
                                       Class<T> responseClass) throws WebClientResponseException {
        return webClient.post()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseClass)
                .block();
    }

    /**
     * GET request
     */
    protected <T> T sendGetRequest(WebClient webClient,
                                   String path,
                                   Map<String, ?> uriVariables,
                                   Class<T> responseClass) throws WebClientResponseException {
        return webClient.get()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseClass)
                .block();
    }

    /**
     * GET request with list response
     */
    protected <T> List<T> sendGetRequestWithListResponse(WebClient webClient,
                                                         String path,
                                                         Map<String, ?> uriVariables,
                                                         Class<T> responseClass) throws WebClientResponseException {
        return webClient.get()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(responseClass)
                .collectList()
                .block();
    }

}
