package com.users.consumers;

import com.users.domain.value_objects.UserTransactionDto;
import com.users.services.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {
    @Autowired
    private UserService userService;

    @RabbitListener(queues = "${broker.queue.transaction.name}")
    public void listenTransactionQueue(UserTransactionDto message) {
        userService.updateWalletFunds(message);
    }
}
