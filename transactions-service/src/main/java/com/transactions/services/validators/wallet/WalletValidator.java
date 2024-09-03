package com.transactions.services.validators.wallet;

import com.transactions.application.exceptions.WalletValidationException;
import com.transactions.domain.aggregate.Wallet;
import com.transactions.domain.specifications.wallet.SenderUserHaveEnoughWalletBalance;
import com.transactions.domain.specifications.wallet.UsersHaveAWallet;
import com.transactions.domain.specifications.wallet.WalletSpecification;
import com.transactions.domain.strategies.ValidationMessageStrategy;
import com.transactions.domain.strategies.wallet.SenderUserDontHaveEnoughWalletBalance;
import com.transactions.domain.strategies.wallet.UsersDontHaveAWallet;
import com.transactions.services.validators.Validator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class WalletValidator implements Validator<Wallet> {
    @Autowired
    protected List<WalletSpecification<Wallet>> walletSpecifications;

    @PostConstruct
    public void initSpecifications() {
        walletSpecifications.add(new SenderUserHaveEnoughWalletBalance());
        walletSpecifications.add(new UsersHaveAWallet());
    }

    @Override
    public void validate(Wallet wallet) {
        walletSpecifications.forEach(
            walletSpecification -> {
                if (!walletSpecification.isSatisfiedBy(wallet)) {
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
