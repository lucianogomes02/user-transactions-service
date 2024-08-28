package com.transactions.domain.strategies.transaction;

public class SenderAndReceiverAreTheSame implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "O remetente e o destinatário não podem ser a mesma pessoa";
    }
}
