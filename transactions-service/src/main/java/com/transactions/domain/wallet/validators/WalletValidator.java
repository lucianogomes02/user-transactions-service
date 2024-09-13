package com.transactions.domain.wallet.validators;

import com.transactions.application.wallet.exceptions.WalletValidationException;
import com.transactions.domain.wallet.entities.Wallet;
import com.transactions.domain.wallet.specifications.SenderUserHaveEnoughWalletBalance;
import com.transactions.domain.wallet.specifications.UsersHaveAWallet;
import com.transactions.domain.wallet.specifications.WalletSpecification;
import com.transactions.domain.wallet.strategies.SenderUserDontHaveEnoughWalletBalance;
import com.transactions.domain.wallet.strategies.UsersDontHaveAWallet;
import com.transactions.libs.ValidationMessageStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class WalletValidator implements AbstractWalletValidator<Wallet> {
    @Autowired
    protected List<WalletSpecification<Wallet>> walletSpecifications;

    @PostConstruct
    public void initSpecifications() {
        walletSpecifications.add(new SenderUserHaveEnoughWalletBalance());
        walletSpecifications.add(new UsersHaveAWallet());
    }

    @Override
    public void validate(Wallet wallet1, Wallet wallet2, Double amount) {
        walletSpecifications.forEach(
            walletSpecification -> {
                if (!walletSpecification.isSatisfiedBy(wallet1, wallet2, amount)) {
                    throw new WalletValidationException(
                            Objects.requireNonNull(
                                    getValidationMessageStrategy(walletSpecification)).getMessage()
                    );
                }
            }
        );
    }

    ValidationMessageStrategy getValidationMessageStrategy(WalletSpecification<Wallet> walletSpecification) {
        return switch (walletSpecification.getClass().getSimpleName()) {
            case "SenderUserHaveEnoughWalletBalance" -> new SenderUserDontHaveEnoughWalletBalance();
            case "UsersHaveAWallet" -> new UsersDontHaveAWallet();
            default -> null;
        };
    }
}
