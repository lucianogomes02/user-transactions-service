package com.auth.services;

import com.auth.dto.LoginRequest;
import com.auth.dto.UserCredentialsResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${user-service.url}")
    private String userServiceUrl;

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserCredentialsResponse verifyCredentials(String username, String password) {
        try {
            ResponseEntity<UserCredentialsResponse> response = restTemplate.postForEntity(
                userServiceUrl + "/users/verify",
                new LoginRequest(username, password),
                UserCredentialsResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }
}
