package com.users.controllers;

import com.users.domain.value_objects.UserPublicDto;
import com.users.domain.value_objects.UserRecordDto;
import com.users.domain.value_objects.UserTransactionDto;
import com.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserPublicDto> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        UserPublicDto user = userService.createUser(userRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<UserPublicDto>> getAllUsers() {
        Iterable<UserPublicDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
