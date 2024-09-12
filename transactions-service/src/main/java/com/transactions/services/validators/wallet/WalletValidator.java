package com.transactions.services.validators.wallet;

import com.transactions.application.wallet.exceptions.WalletValidationException;
import com.transactions.domain.aggregate.wallet.Wallet;
import com.transactions.domain.specifications.wallet.SenderUserHaveEnoughWalletBalance;
import com.transactions.domain.specifications.wallet.UsersHaveAWallet;
import com.transactions.domain.specifications.wallet.WalletSpecification;
import com.transactions.domain.strategies.ValidationMessageStrategy;
import com.transactions.domain.strategies.wallet.SenderUserDontHaveEnoughWalletBalance;
import com.transactions.domain.strategies.wallet.UsersDontHaveAWallet;
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
