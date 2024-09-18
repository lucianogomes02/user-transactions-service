package com.transactions.services;

import com.transactions.domain.transactions.aggregate.WalletsTransaction;
import com.transactions.domain.transactions.value_objects.transactions.TransactionPublicDto;
import com.transactions.domain.transactions.value_objects.wallet.WalletPublicDto;
import com.transactions.domain.transactions.value_objects.wallet.WalletRecordDto;
import com.transactions.repositories.transaction.TransactionDomainRepository;
import com.transactions.repositories.wallet.WalletDomainRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {
    @Autowired
    private WalletDomainRepository walletDomainRepository;

    @Autowired
    private TransactionDomainRepository transactionDomainRepository;

    @Transactional
    public WalletPublicDto createWallet(WalletRecordDto walletRecordDto, String currentUserId) {
        var wallet = WalletsTransaction.registerWallet(
            currentUserId,
            Double.parseDouble(walletRecordDto.balance())
        );
        walletDomainRepository.save(wallet);
        return new WalletPublicDto(
            wallet.id.toString(),
            wallet.userId,
            wallet.balance.toString(),
            wallet.createdAt.toString()
        );
    }

    @Transactional
    public void updateUserWalletBalance(TransactionPublicDto transactionPublicDto) {
        WalletsTransaction walletsTransaction = transactionDomainRepository.findWalletTransactionById(
            UUID.fromString(transactionPublicDto.id())
        );
        try {
            walletsTransaction.processTransaction();
            walletDomainRepository.updateWalletBalance(
                walletsTransaction.senderWallet.id,
                walletsTransaction.senderWallet.balance
            );
            walletDomainRepository.updateWalletBalance(
                walletsTransaction.receiverWallet.id,
                walletsTransaction.receiverWallet.balance
            );
        } catch (Exception e) {
            walletsTransaction.registerFailedTransaction();
        } finally {
            transactionDomainRepository.updateTransactionStatus(
                walletsTransaction.transaction.id,
                walletsTransaction.transaction.status
            );
        }
    }

    public WalletPublicDto getWallet(String currentUserId) {
        var wallet = walletDomainRepository.findByUserId(currentUserId);
        return new WalletPublicDto(
            wallet.id.toString(),
            wallet.userId,
            wallet.balance.toString(),
            wallet.createdAt.toString()
        );
    }
}
