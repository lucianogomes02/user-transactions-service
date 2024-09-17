package com.transactions.domain.transactions.validators.wallet;

import com.transactions.application.wallet.exceptions.WalletValidationException;
import com.transactions.domain.transactions.entities.Wallet;
import com.transactions.domain.transactions.specifications.wallet.SenderUserHaveEnoughWalletBalance;
import com.transactions.domain.transactions.specifications.wallet.UsersHaveAWallet;
import com.transactions.domain.transactions.specifications.wallet.WalletSpecification;
import com.transactions.domain.transactions.strategies.wallet.SenderUserDontHaveEnoughWalletBalance;
import com.transactions.domain.transactions.strategies.wallet.UsersDontHaveAWallet;
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
    public void validate(Wallet senderWallet, Wallet receiverWallet, Double amount) {
        walletSpecifications.forEach(
            walletSpecification -> {
                if (!walletSpecification.isSatisfiedBy(senderWallet, senderWallet, amount)) {
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
