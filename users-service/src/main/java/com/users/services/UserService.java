package com.users.services;

import com.users.domain.aggregate.User;
import com.users.domain.value_objects.*;
import com.users.repositories.UserRepository;
import com.users.services.validators.UserValidator;
import jakarta.validation.Valid;
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
    private UserValidator userValidator;

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
            user.getCreatedAt().toString(),
            user.getUpdatedAt().toString()
        );
    }

    public List<UserPublicDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> new UserPublicDto(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
            ))
            .collect(Collectors.toList());
    }

    public UserCredentialsResponse verifyCredentials(@Valid LoginRequestDto loginRequestDto) {
        var user = userRepository.findByEmail(loginRequestDto.username());
        if (user != null && bCryptPasswordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            return new UserCredentialsResponse(
                user.getId().toString(),
                user.getEmail()
            );
        }
        return null;
    }
}
