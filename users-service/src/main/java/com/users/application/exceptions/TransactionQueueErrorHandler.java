package com.users.application.exceptions;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;

public class TransactionQueueErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        String queue = ((ListenerExecutionFailedException) t).getFailedMessage().getMessageProperties().getConsumerQueue();
        String message = new String(((ListenerExecutionFailedException) t).getFailedMessage().getBody());
        String error = t.getMessage();

        System.out.println("=========== FAILED QUEUE MESSAGE ===========");
        System.out.println("Queue: " + queue);
        System.out.println("Message: " + message);
        System.out.println("Error: " + error);
        System.out.println("===========================================");

        // Logar no Google Cloud Observability ou no Datadog por exemplo.
        throw new AmqpRejectAndDontRequeueException(error);
    }
}
