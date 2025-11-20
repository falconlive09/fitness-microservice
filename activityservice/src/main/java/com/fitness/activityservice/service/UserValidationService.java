package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        try {
            // make api call
            // parse response body to Boolean.
            return Boolean.TRUE.equals(userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve() // make api call
                    .bodyToMono(Boolean.class) // parse response body to Boolean.
                    .block()); // block to get the response synchronously

        }
        catch (WebClientResponseException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new RuntimeException("User not found: " + userId);
            } else if(e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new RuntimeException("Invalid Request: " + userId);
            }
            return false;
        }
    }

}
