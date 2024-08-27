package com.transactions.producers;

import com.transactions.domain.value_objects.TransactionRecordDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class TransactionProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.transaction.name}")
    private String routingKey;

    public void publishTransactionMessage(TransactionRecordDto transactionRecordDto) {
        rabbitTemplate.convertAndSend("", routingKey, transactionRecordDto);
    }
}
