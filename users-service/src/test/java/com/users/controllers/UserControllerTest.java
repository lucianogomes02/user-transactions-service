package com.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.domain.value_objects.UserPublicDto;
import com.users.domain.value_objects.UserRecordDto;
import com.users.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateUser() throws Exception {
        UserRecordDto userRecordDto = new UserRecordDto(
                "John Doe",
                "john.doe@test.com",
                "12345",
                "000.000.000-00",
                1000.00
        );
        UserPublicDto userPublicDto = new UserPublicDto(
                UUID.randomUUID().toString(),
                "Jane Doe",
                "jane.doe@test.com",
                "000.000.000-00",
                "1000.00",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );

        when(userService.createUser(userRecordDto)).thenReturn(userPublicDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRecordDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userPublicDto.id()))
                .andExpect(jsonPath("$.name").value(userPublicDto.name()));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserPublicDto user1 = new UserPublicDto(
                UUID.randomUUID().toString(),
                "John",
                "john.doe@test.com",
                "000.000.000-00",
                "1000.00",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );
        UserPublicDto user2 = new UserPublicDto(
                UUID.randomUUID().toString(),
                "Jane",
                "jane.doe@test.com",
                "000.000.000-00",
                "1000.00",
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user1.id()))
                .andExpect(jsonPath("$[1].id").value(user2.id()));
    }
}
