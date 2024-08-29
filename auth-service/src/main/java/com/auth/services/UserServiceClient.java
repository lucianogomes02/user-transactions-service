package com.auth.services;

import com.auth.dto.LoginRequest;
import com.auth.dto.UserCredentialsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${user-service.url}")
    private String userServiceUrl;

    @Value("${api.key}")
    private String apiKey;

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserCredentialsResponse verifyCredentials(String username, String password) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-AUTH-SERVICE-KEY", apiKey);

            HttpEntity<LoginRequest> request = new HttpEntity<>(new LoginRequest(username, password), headers);

            ResponseEntity<UserCredentialsResponse> response = restTemplate.exchange(
                userServiceUrl + "/users/verify",
                HttpMethod.POST,
                request,
                UserCredentialsResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }
}
