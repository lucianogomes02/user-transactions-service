package com.transactions.services.consumers;

import com.transactions.domain.transactions.value_objects.transactions.TransactionPublicDto;
import com.transactions.services.WalletService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {
    @Autowired
    private WalletService walletService;

    @RabbitListener(queues = "${broker.queue.transaction.name}")
    public void listenUserQueue(@Payload TransactionPublicDto message) {
        walletService.updateUserWalletBalance(message);
    }
}
