package com.users.controllers;

import com.users.domain.value_objects.UserPublicDto;
import com.users.domain.value_objects.UserRecordDto;
import com.users.domain.value_objects.UserTransactionDto;
import com.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "Endpoints for users")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @PostMapping("/users")
    public ResponseEntity<UserPublicDto> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        UserPublicDto user = userService.createUser(userRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Get all users")
    @GetMapping("/users")
    public ResponseEntity<Iterable<UserPublicDto>> getAllUsers() {
        Iterable<UserPublicDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
