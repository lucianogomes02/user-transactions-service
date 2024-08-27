package com.users.services;

import com.users.domain.aggregate.User;
import com.users.domain.value_objects.UserPublicDto;
import com.users.domain.value_objects.UserRecordDto;
import com.users.domain.value_objects.UserTransactionDto;
import com.users.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserPublicDto createUser(UserRecordDto userRecordDto) {
        var user = new User();
        BeanUtils.copyProperties(userRecordDto, user);
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
    public UserTransactionDto updateWalletFunds(UserTransactionDto userTransactionDto) {
        var senderUser = userRepository.findById(UUID.fromString(userTransactionDto.senderId()));
        var receiverUser = userRepository.findById(UUID.fromString(userTransactionDto.receiverId()));

        if (senderUser.isPresent() && receiverUser.isPresent()) {
            var sender = senderUser.get();
            var receiver = receiverUser.get();

            if (sender.getWalletFunds() >= userTransactionDto.amount()) {
                sender.setWalletFunds(sender.getWalletFunds() - userTransactionDto.amount());
                receiver.setWalletFunds(receiver.getWalletFunds() + userTransactionDto.amount());
                userRepository.save(sender);
                userRepository.save(receiver);
            }

            return userTransactionDto;
        } else {
            return null;
        }
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
}
