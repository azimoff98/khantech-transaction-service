package com.khantech.ts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khantech.ts.controller.TransactionController;
import com.khantech.ts.dto.request.TransactionRequest;
import com.khantech.ts.dto.request.TransactionType;
import com.khantech.ts.dto.response.TransactionResponse;
import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.TransactionStatus;
import com.khantech.ts.mapper.TransactionMapper;
import com.khantech.ts.service.TransactionProcessingService;
import com.khantech.ts.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TransactionController.class)
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionProcessingService transactionProcessingService;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionMapper transactionMapper;

    @Test
    void shouldProcessTransactionSuccessfully() throws Exception {
        // Arrange
        var request = new TransactionRequest(1L, new BigDecimal("1500"), TransactionType.CREDIT);
        var transaction = new Transaction();
        transaction.setId(1L);
        transaction.setUserId(1L);
        transaction.setAmount(new BigDecimal("1500"));
        transaction.setTransactionType(1);
        transaction.setTransactionStatus(TransactionStatus.APPROVED.getCode());

        var response = new TransactionResponse(1L, new BigDecimal("1500"), TransactionStatus.APPROVED, System.currentTimeMillis());

        Mockito.when(transactionMapper.toEntity(any(TransactionRequest.class))).thenReturn(transaction);
        Mockito.when(transactionProcessingService.processTransaction(any(Transaction.class))).thenReturn(transaction);
        Mockito.when(transactionMapper.toResponse(any(Transaction.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1L))
                .andExpect(jsonPath("$.data.status").value("APPROVED"))
                .andExpect(jsonPath("$.data.amount").value("1500"))
                .andExpect(jsonPath("$.code").value(10))
                .andExpect(jsonPath("$.message").value("Transaction Successfully Created"));
    }
}
