package com.transactions.domain.transaction.strategies;

import com.transactions.libs.ValidationMessageStrategy;

public class SenderAndReceiverAreTheSame implements ValidationMessageStrategy {
    @Override
    public String getMessage() {
        return "O remetente e o destinatário não podem ser a mesma pessoa";
    }
}
