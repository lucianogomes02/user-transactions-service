package com.transactions.services.consumers;

import com.transactions.domain.value_objects.TransactionPublicDto;
import com.transactions.services.TransactionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {
    @Autowired
    private TransactionService transactionService;

    @RabbitListener(queues = "${broker.queue.user.name}")
    public void listenUserQueue(@Payload TransactionPublicDto message) {
        transactionService.proccessTransaction(message);
    }
}
