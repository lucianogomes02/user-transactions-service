package com.auth.controllers;

import com.auth.dto.LoginRequest;
import com.auth.dto.LoginResponse;
import com.auth.services.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class TokenController {
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserServiceClient userServiceClient;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var userCredentialsResponse = userServiceClient.verifyCredentials(loginRequest.username(), loginRequest.password());
        if (userCredentialsResponse == null) {
            return ResponseEntity.badRequest().build();
        }

        var expiresAt = Instant.ofEpochSecond(System.currentTimeMillis() + 3600000);
        var claims = JwtClaimsSet.builder()
                .issuer("user-transactions-auth-service")
                .subject(userCredentialsResponse.id())
                .claim("username", userCredentialsResponse.email())
                .expiresAt(Instant.ofEpochSecond(System.currentTimeMillis() + 3600000))
                .issuedAt(Instant.now())
                .build();

        var jwtToken = jwtEncoder.encode(
                JwtEncoderParameters.from(claims)
        ).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtToken, expiresAt.toEpochMilli()));
    }
}
