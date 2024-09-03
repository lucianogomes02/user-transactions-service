package com.transactions.services;

import com.transactions.domain.aggregate.Transaction;
import com.transactions.domain.value_objects.TransactionPublicDto;
import com.transactions.domain.value_objects.TransactionRecordDto;
import com.transactions.domain.value_objects.TransactionStatus;
import com.transactions.services.producers.TransactionProducer;
import com.transactions.repositories.TransactionRepository;
import com.transactions.services.validators.transaction.TransactionValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionProducer transactionProducer;

    @Autowired
    private TransactionValidator transactionValidator;

    @Transactional
    public TransactionPublicDto createTransaction(TransactionRecordDto transactionRecordDto, String currentUserId) {
        var transaction = new Transaction();
        transaction.setSenderId(UUID.fromString(currentUserId));
        transaction.setReceiverId(UUID.fromString(transactionRecordDto.receiverId()));
        transaction.setAmount(Double.valueOf(transactionRecordDto.amount()));
        transactionValidator.validate(transaction);

        transaction = transactionRepository.save(transaction);
        var transactionDto = new TransactionPublicDto(
            transaction.getId().toString(),
            transaction.getSenderId().toString(),
            transaction.getReceiverId().toString(),
            transaction.getAmount().toString(),
            transaction.getStatus().toString(),
            transaction.getCreatedAt().toString()
        );
        transactionProducer.publishTransactionMessage(transactionDto);
        return transactionDto;
    }

    @Transactional
    public void updateTransactionStatus(TransactionStatus transactionStatus, String transactionId) {
        var transaction = transactionRepository.findById(UUID.fromString(transactionId)).orElseThrow();
        transaction.setStatus(transactionStatus);
        transactionRepository.save(transaction);
    }

    public List<TransactionPublicDto> getTransactions() {
        return transactionRepository.findAll().stream()
            .map(transaction -> new TransactionPublicDto(
                transaction.getId().toString(),
                transaction.getSenderId().toString(),
                transaction.getReceiverId().toString(),
                transaction.getAmount().toString(),
                transaction.getStatus().toString(),
                transaction.getCreatedAt().toString()
            ))
            .collect(Collectors.toList());
    }
}
