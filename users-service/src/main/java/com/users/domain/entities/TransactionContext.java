package com.users.domain.entities;

import com.users.domain.aggregate.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionContext {
    private User userSender;
    private User userReceiver;
    private double amount;
}
