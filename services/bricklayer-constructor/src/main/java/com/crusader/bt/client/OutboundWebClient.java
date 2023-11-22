package com.crusader.bt.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Generic class for create universal REST requests
 */
@Component
public class OutboundWebClient {

    /**
     * PUT request
     */
    protected <T, R> Mono<T> sendPutRequest(WebClient webClient,
                                            String path,
                                            Map<String, ?> uriVariables,
                                            R request,
                                            Class<T> responseClass) throws WebClientResponseException {
        return webClient.put()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseClass);
    }

    /**
     * POST request
     */
    protected <T, R> Mono<T> sendPostRequest(WebClient webClient,
                                             String path,
                                             Map<String, ?> uriVariables,
                                             R request,
                                             Class<T> responseClass) throws WebClientResponseException {
        return webClient.post()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseClass);
    }

    /**
     * DELETE request
     */
    protected <T> Mono<T> sendDeleteRequest(WebClient webClient,
                                            String path,
                                            Map<String, ?> uriVariables,
                                            Class<T> responseClass) throws WebClientResponseException {
        return webClient.delete()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseClass);
    }

    /**
     * GET request
     */
    protected <T> Mono<T> sendGetRequest(WebClient webClient,
                                         String path,
                                         Map<String, ?> uriVariables,
                                         Class<T> responseClass) throws WebClientResponseException {
        return webClient.get()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseClass);
    }

    /**
     * GET request with list response
     */
    protected <T> Flux<T> sendGetRequestWithListResponse(WebClient webClient,
                                                         String path,
                                                         Map<String, ?> uriVariables,
                                                         Class<T> responseClass) throws WebClientResponseException {
        return webClient.get()
                .uri(path, uriVariables)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(responseClass);
    }

}
