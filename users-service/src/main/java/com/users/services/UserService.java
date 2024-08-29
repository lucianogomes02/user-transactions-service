package com.users.services;

import com.users.domain.aggregate.User;
import com.users.domain.entities.TransactionContext;
import com.users.domain.value_objects.*;
import com.users.services.producers.UserProducer;
import com.users.repositories.UserRepository;
import com.users.services.validators.UserTransactionValidator;
import com.users.services.validators.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProducer userProducer;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserTransactionValidator userTransactionValidator;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserPublicDto createUser(UserRecordDto userRecordDto) {
        var user = new User(
            UUID.randomUUID(),
            userRecordDto.name(),
            userRecordDto.email(),
            bCryptPasswordEncoder.encode(userRecordDto.password()),
            userRecordDto.cpf(),
            userRecordDto.walletFunds(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            true
        );
        userValidator.validate(user);
        user = userRepository.save(user);
        return new UserPublicDto(
            user.getId().toString(),
            user.getName(),
            user.getEmail(),
            user.getCpf(),
            user.getWalletFunds().toString(),
            user.getCreatedAt().toString(),
            user.getUpdatedAt().toString()
        );
    }

    @Transactional
    public void updateWalletFunds(UserTransactionDto userTransactionDto) {
        try {
            var userTransactionResponse = registerTransaction(userTransactionDto);
            userProducer.pulishUserTransactionMessage(userTransactionResponse);
        } catch (Exception e) {
            userTransactionDto = new UserTransactionDto(
                userTransactionDto.id(),
                userTransactionDto.senderId(),
                userTransactionDto.receiverId(),
                userTransactionDto.amount(),
                "FAILED",
                userTransactionDto.createdAt()
            );
            userProducer.pulishUserTransactionMessage(userTransactionDto);
        }
    }

    private UserTransactionDto registerTransaction(UserTransactionDto userTransactionDto) {
        var senderUser = userRepository.findById(UUID.fromString(userTransactionDto.senderId()));
        var receiverUser = userRepository.findById(UUID.fromString(userTransactionDto.receiverId()));

        var transactionContext = new TransactionContext();
        transactionContext.setUserSender(senderUser.orElse(null));
        transactionContext.setUserReceiver(receiverUser.orElse(null));
        transactionContext.setAmount(userTransactionDto.amount());

        userTransactionValidator.validate(transactionContext);

        if (senderUser.isPresent() && receiverUser.isPresent()) {
            var sender = senderUser.get();
            var receiver = receiverUser.get();

            if (sender.getWalletFunds() > userTransactionDto.amount()) {
                sender.setWalletFunds(sender.getWalletFunds() - userTransactionDto.amount());
                receiver.setWalletFunds(receiver.getWalletFunds() + userTransactionDto.amount());
                userRepository.save(sender);
                userRepository.save(receiver);
            }
        }
        return new UserTransactionDto(
            userTransactionDto.id(),
            userTransactionDto.senderId(),
            userTransactionDto.receiverId(),
            userTransactionDto.amount(),
            "SUCCEEDED",
            userTransactionDto.createdAt()
        );
    }

    public List<UserPublicDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> new UserPublicDto(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getWalletFunds().toString(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
            ))
            .collect(Collectors.toList());
    }

    public UserCredentialsResponse verifyCredentials(@Valid UserCredentialsDto userCredentialsDto) {
        var user = userRepository.findByEmail(userCredentialsDto.username());
        if (user != null && bCryptPasswordEncoder.matches(userCredentialsDto.password(), user.getPassword())) {
            return new UserCredentialsResponse(
                user.getId().toString(),
                user.getEmail()
            );
        }
        return null;
    }
}
