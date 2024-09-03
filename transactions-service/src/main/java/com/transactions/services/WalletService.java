package com.transactions.services;

import com.transactions.domain.aggregate.Wallet;
import com.transactions.domain.value_objects.TransactionPublicDto;
import com.transactions.domain.value_objects.TransactionStatus;
import com.transactions.domain.value_objects.WalletPublicDto;
import com.transactions.domain.value_objects.WalletRecordDto;
import com.transactions.repositories.TransactionRepository;
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

    @Autowired
    private TransactionRepository transactionRepository;

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
        var senderWallet = walletRepository.findByUserId(UUID.fromString(transactionPublicDto.senderId()));
        var receiverWallet = walletRepository.findByUserId(UUID.fromString(transactionPublicDto.receiverId()));
        var transaction = transactionRepository.findById(UUID.fromString(transactionPublicDto.id()));
        if (senderWallet != null && receiverWallet != null && transaction.isPresent()) {
            var currentTransaction = transaction.get();
            var receiverNewBalance = receiverWallet.getBalance() + Double.parseDouble(transactionPublicDto.amount());
            var senderNewBalance = senderWallet.getBalance() - Double.parseDouble(transactionPublicDto.amount());
            senderWallet.setBalance(senderNewBalance);
            senderWallet.setUpdatedAt(LocalDateTime.now());
            receiverWallet.setBalance(receiverNewBalance);
            receiverWallet.setUpdatedAt(LocalDateTime.now());
            walletRepository.saveAll(new Wallet[]{senderWallet, receiverWallet});
            currentTransaction.setStatus(TransactionStatus.SUCCEEDED);
        } else {
            if (transaction.isPresent()) {
                var currentTransaction = transaction.get();
                currentTransaction.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(currentTransaction);
            }
            throw new RuntimeException("Erro ao processar transação");
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
