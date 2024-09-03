package com.transactions.services;

import com.transactions.domain.aggregate.Wallet;
import com.transactions.domain.value_objects.TransactionPublicDto;
import com.transactions.domain.value_objects.TransactionStatus;
import com.transactions.domain.value_objects.WalletPublicDto;
import com.transactions.domain.value_objects.WalletRecordDto;
import com.transactions.repositories.WalletRepository;
import com.transactions.services.validators.wallet.WalletValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletValidator walletValidator;

    @Autowired
    TransactionService transactionService;

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

    @Transactional
    public void updateUserWalletBalance(TransactionPublicDto transactionPublicDto) {
        try {
            var senderWallet = walletRepository.findByUserId(UUID.fromString(transactionPublicDto.senderId()));
            var receiverWallet = walletRepository.findByUserId(UUID.fromString(transactionPublicDto.receiverId()));
            walletValidator.validate(senderWallet, receiverWallet, Double.parseDouble(transactionPublicDto.amount()));

            var receiverNewBalance = receiverWallet.getBalance() + Double.parseDouble(transactionPublicDto.amount());
            var senderNewBalance = senderWallet.getBalance() - Double.parseDouble(transactionPublicDto.amount());
            senderWallet.setBalance(senderNewBalance);
            senderWallet.setUpdatedAt(LocalDateTime.now());
            receiverWallet.setBalance(receiverNewBalance);
            receiverWallet.setUpdatedAt(LocalDateTime.now());
            walletRepository.saveAll(new Wallet[]{senderWallet, receiverWallet});
            transactionService.updateTransactionStatus(TransactionStatus.SUCCEEDED, transactionPublicDto.id());
        } catch (Exception e) {
            transactionService.updateTransactionStatus(TransactionStatus.FAILED, transactionPublicDto.id());
            throw e;
        }
    }

    public WalletPublicDto getWallet(String currentUserId) {
        var wallet = walletRepository.findByUserId(UUID.fromString(currentUserId));
        return new WalletPublicDto(
            wallet.getId().toString(),
            wallet.getUserId().toString(),
            wallet.getBalance().toString(),
            wallet.getCreatedAt().toString()
        );
    }
}
