package com.users.producers;

import com.users.domain.value_objects.UserTransactionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.user.name}")
    private String routingKey;

    public void pulishUserTransactionMessage(UserTransactionDto userTransactionDto) {
        rabbitTemplate.convertAndSend("", routingKey, userTransactionDto);
    }
}
