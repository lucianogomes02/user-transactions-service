package com.transactions.services;

import com.transactions.domain.aggregate.Transaction;
import com.transactions.domain.value_objects.TransactionPublicDto;
import com.transactions.domain.value_objects.TransactionRecordDto;
import com.transactions.producers.TransactionProducer;
import com.transactions.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        BeanUtils.copyProperties(transactionRecordDto, transaction);
        transactionProducer.publicTransactionMessage(transactionRecordDto);
        transaction = transactionRepository.save(transaction);
        return new TransactionPublicDto(
            transaction.getId().toString(),
            transaction.getSenderId().toString(),
            transaction.getReceiverId().toString(),
            transaction.getAmount().toString(),
            transaction.getCreatedAt().toString()
        );
    }

    public List<TransactionPublicDto> getTransactions() {
        return transactionRepository.findAll().stream()
            .map(transaction -> new TransactionPublicDto(
                transaction.getId().toString(),
                transaction.getSenderId().toString(),
                transaction.getReceiverId().toString(),
                transaction.getAmount().toString(),
                transaction.getCreatedAt().toString()
            ))
            .collect(Collectors.toList());
    }
}
