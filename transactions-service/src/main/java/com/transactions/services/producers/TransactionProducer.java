package com.transactions.services.producers;

import com.transactions.domain.value_objects.TransactionPublicDto;
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

    public void publishTransactionMessage(TransactionPublicDto transactionRecordDto) {
        rabbitTemplate.convertAndSend("", routingKey, transactionRecordDto);
    }
}
