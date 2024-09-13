package com.transactions.application.wallet.controllers;

import com.transactions.domain.wallet.value_objects.WalletPublicDto;
import com.transactions.domain.wallet.value_objects.WalletRecordDto;
import com.transactions.services.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Get wallet for current authenticated user")
    @GetMapping
    public ResponseEntity<WalletPublicDto> getWallet(
            JwtAuthenticationToken jwt
    ) {
        String currentUserId = jwt.getToken().getClaimAsString("sub");
        WalletPublicDto wallet = walletService.getWallet(currentUserId);
        return ResponseEntity.ok(wallet);
    }
}
