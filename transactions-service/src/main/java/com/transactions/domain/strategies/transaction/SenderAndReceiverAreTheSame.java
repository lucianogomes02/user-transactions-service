package com.transactions.domain.strategies.transaction;

import com.transactions.domain.strategies.ValidationMessageStrategy;

public class SenderAndReceiverAreTheSame implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "O remetente e o destinatário não podem ser a mesma pessoa";
    }
}
