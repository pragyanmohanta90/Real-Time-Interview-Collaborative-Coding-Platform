package com.interviewplatform.backend.integration.onlinecompiler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OnlineCompilerClient {

    private final RestTemplate restTemplate;

    @Value("${onlinecompiler.api.url}")
    private String apiUrl;

    @Value("${onlinecompiler.api.key}")
    private String apiKey;

    public OnlineCompilerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OnlineCompilerResponse execute(OnlineCompilerRequest request) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", apiKey);

        HttpEntity<OnlineCompilerRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<OnlineCompilerResponse> response =
                restTemplate.exchange(
                        apiUrl,
                        HttpMethod.POST,
                        entity,
                        OnlineCompilerResponse.class
                );

        return response.getBody();
    }
}