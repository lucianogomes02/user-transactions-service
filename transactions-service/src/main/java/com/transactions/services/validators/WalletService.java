package com.transactions.services.validators;

import com.transactions.domain.aggregate.Wallet;
import com.transactions.domain.value_objects.WalletPublicDto;
import com.transactions.domain.value_objects.WalletRecordDto;
import com.transactions.repositories.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public WalletPublicDto createWallet(WalletRecordDto walletRecordDto, String currentUserId) {
        var wallet = new Wallet();
        wallet.setUserId(UUID.fromString(currentUserId));
        wallet.setBalance(Double.valueOf(walletRecordDto.balance()));
        wallet.setCreatedAt(LocalDateTime.now());
        wallet.setUpdatedAt(LocalDateTime.now());
        wallet = walletRepository.save(wallet);
        return new WalletPublicDto(
            wallet.getId().toString(),
            wallet.getUserId().toString(),
            wallet.getBalance().toString(),
            wallet.getCreatedAt().toString()
        );
    }
}
