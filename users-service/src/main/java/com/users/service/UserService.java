package com.users.service;

import com.users.domain.aggregate.User;
import com.users.domain.value_objects.UserPublicDto;
import com.users.domain.value_objects.UserRecordDto;
import com.users.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
