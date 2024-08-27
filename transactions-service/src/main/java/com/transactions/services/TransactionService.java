package com.transactions.services;

import com.transactions.domain.aggregate.Transaction;
import com.transactions.domain.value_objects.TransactionPublicDto;
import com.transactions.domain.value_objects.TransactionRecordDto;
import com.transactions.domain.value_objects.TransactionStatus;
import com.transactions.producers.TransactionProducer;
import com.transactions.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionProducer transactionProducer;

    @Transactional
    public TransactionPublicDto createTransaction(TransactionRecordDto transactionRecordDto) {
        var transaction = new Transaction();
        transaction.setSenderId(UUID.fromString(transactionRecordDto.senderId()));
        transaction.setReceiverId(UUID.fromString(transactionRecordDto.receiverId()));
        transaction.setAmount(Double.valueOf(transactionRecordDto.amount()));
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
    public void proccessTransaction(TransactionPublicDto transactionPublicDto) {
        var transaction = transactionRepository.findById(UUID.fromString(transactionPublicDto.id()));

        if (transaction.isPresent()) {
            var onGoingTransaction = transaction.get();
            onGoingTransaction.setStatus(TransactionStatus.SUCCEEDED);
        }
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
