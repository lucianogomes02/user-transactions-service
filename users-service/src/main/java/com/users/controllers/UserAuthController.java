package com.users.controllers;

import com.users.domain.value_objects.LoginRequestDto;
import com.users.domain.value_objects.LoginResponseDto;
import com.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "User Auth Controller", description = "Endpoint para autenticação de usuários")
@RestController
@RequestMapping("/users/auth")
public class UserAuthController {
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserService userService;

    @Operation(summary = "Autenticar Usuário (Gerar Token JWT)")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        var userCredentialsResponse = userService.verifyCredentials(loginRequest);
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

        return ResponseEntity.ok(new LoginResponseDto(jwtToken, expiresAt.toEpochMilli()));
    }
}
