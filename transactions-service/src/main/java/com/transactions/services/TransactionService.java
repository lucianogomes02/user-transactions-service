package com.transactions.services;

import com.transactions.domain.transactions.aggregate.WalletsTransaction;
import com.transactions.domain.transactions.value_objects.transactions.TransactionPublicDto;
import com.transactions.domain.transactions.value_objects.transactions.TransactionRecordDto;
import com.transactions.repositories.transaction.TransactionDomainRepository;
import com.transactions.services.producers.TransactionProducer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionDomainRepository transactionDomainRepository;

    @Autowired
    private TransactionProducer transactionProducer;

    @Transactional
    public TransactionPublicDto createTransaction(TransactionRecordDto transactionRecordDto, String currentUserId) {
        WalletsTransaction walletsTransaction = new WalletsTransaction();
        walletsTransaction.transaction = walletsTransaction.initiateTransaction(
            currentUserId,
            transactionRecordDto.receiverId(),
            Double.valueOf(transactionRecordDto.amount())
        );

        transactionDomainRepository.save(walletsTransaction);

        var transactionDto = new TransactionPublicDto(
            walletsTransaction.transaction.id.toString(),
            walletsTransaction.transaction.senderId,
            walletsTransaction.transaction.receiverId,
            walletsTransaction.transaction.amount.toString(),
            walletsTransaction.transaction.status.toString(),
            walletsTransaction.transaction.createdAt.toString()
        );
        transactionProducer.publishTransactionMessage(transactionDto);
        return transactionDto;
    }

    public List<TransactionPublicDto> getTransactions() {
        return transactionDomainRepository.findAll().stream()
            .map(transaction -> new TransactionPublicDto(
                transaction.id.toString(),
                transaction.senderId,
                transaction.receiverId,
                transaction.amount.toString(),
                transaction.createdAt.toString(),
                transaction.updatedAt.toString()
            ))
            .collect(Collectors.toList());
    }
}
