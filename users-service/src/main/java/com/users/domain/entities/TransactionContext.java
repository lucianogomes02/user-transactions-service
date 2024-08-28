package com.users.domain.entities;

import com.users.domain.aggregate.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionContext {
    private User userSender;
    private User userReceiver;
    private double amount;
}
