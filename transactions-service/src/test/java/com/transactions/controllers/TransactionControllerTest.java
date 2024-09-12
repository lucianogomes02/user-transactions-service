package com.transactions.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactions.TestSecurityConfig;
import com.transactions.application.transaction.controllers.TransactionController;
import com.transactions.domain.value_objects.TransactionPublicDto;
import com.transactions.domain.value_objects.TransactionRecordDto;
import com.transactions.domain.value_objects.TransactionStatus;
import com.transactions.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import(TestSecurityConfig.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateTransaction() throws Exception {
        TransactionRecordDto transactionRecordDto = new TransactionRecordDto(
            "00000000-0000-0000-0000-000000000001",
            "1000.00"
        );

        TransactionPublicDto response = new TransactionPublicDto(
            "00000000-0000-0000-0000-000000000002",
            "00000000-0000-0000-0000-000000000000",
            "00000000-0000-0000-0000-000000000001",
            "1000.00",
            TransactionStatus.SUCCEEDED.toString(),
            "2021-01-01T00:00:00"
        );
        when(transactionService.createTransaction(
                transactionRecordDto,
                "00000000-0000-0000-0000-000000000000")
        ).thenReturn(response);

        mockMvc.perform(post("/transactions")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRecordDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        TransactionPublicDto transaction1 = new TransactionPublicDto(
            "00000000-0000-0000-0000-000000000002",
            "00000000-0000-0000-0000-000000000000",
            "00000000-0000-0000-0000-000000000001",
            "1000.00",
            TransactionStatus.SUCCEEDED.toString(),
            "2021-01-01T00:00:00"
        );

        TransactionPublicDto transaction2 = new TransactionPublicDto(
            "00000000-0000-0000-0000-000000000003",
            "00000000-0000-0000-0000-000000000005",
            "00000000-0000-0000-0000-000000000005",
            "1000.00",
            TransactionStatus.FAILED.toString(),
            "2021-01-01T00:00:00"
        );

        when(transactionService.getTransactions()).thenReturn(List.of(transaction1, transaction2));
        mockMvc.perform(get("/transactions")
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(transaction1.id()))
                .andExpect(jsonPath("$[0].status").value(TransactionStatus.SUCCEEDED.toString()))
                .andExpect(jsonPath("$[1].id").value(transaction2.id()))
                .andExpect(jsonPath("$[1].status").value(TransactionStatus.FAILED.toString()));
    }
}
