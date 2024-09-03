package com.transactions.controllers;

import com.transactions.domain.aggregate.Wallet;
import com.transactions.domain.value_objects.WalletPublicDto;
import com.transactions.domain.value_objects.WalletRecordDto;
import com.transactions.services.validators.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Wallet Controller", description = "Endpoints for wallets")
@RestController
@RequestMapping("/wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Operation(summary = "Create a wallet for current authenticated user")
    @PostMapping
    public ResponseEntity<WalletPublicDto> createWallet(
            @RequestBody
            @Valid
            WalletRecordDto walletRecordDto,
            JwtAuthenticationToken jwt
    ) {
        String currentUserId = jwt.getToken().getClaimAsString("sub");
        WalletPublicDto wallet =  walletService.createWallet(walletRecordDto, currentUserId);
        return ResponseEntity.ok(wallet);
    }
}
