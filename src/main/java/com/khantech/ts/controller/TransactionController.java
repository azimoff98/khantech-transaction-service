package com.khantech.ts.controller;

import com.khantech.ts.controller.constants.ApiCodes;
import com.khantech.ts.controller.helper.TransactionApiBuilder;
import com.khantech.ts.dto.request.TransactionRequest;
import com.khantech.ts.dto.response.TransactionApiResponse;
import com.khantech.ts.dto.response.TransactionResponse;
import com.khantech.ts.entity.Transaction;
import com.khantech.ts.mapper.TransactionMapper;
import com.khantech.ts.service.TransactionProcessingService;
import com.khantech.ts.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController implements TransactionApiBuilder {

    private final TransactionProcessingService transactionProcessingService;
    private final TransactionService transactionService;
    private final TransactionMapper mapper;

    @PostMapping
    public ResponseEntity<TransactionApiResponse<TransactionResponse>> process(@RequestBody @Valid TransactionRequest request) {
        log.info("accepted transaction: {}", request);
        var response = mapper.toResponse(
                transactionProcessingService.processTransaction(
                        mapper.toEntity(request)
                )
        );
        return ResponseEntity.ok(
                build(response, ApiCodes.TRANSACTION_SUCCESSFUL)
        );
    }

    @PutMapping("/manual-approve/{transactionId}")
    public ResponseEntity<TransactionApiResponse<TransactionResponse>> manualApprove(@PathVariable("transactionId") Long transactionId) {
        log.info("accepted manual approval request for transaction with id: {}", transactionId);
        var transaction = transactionService.findById(transactionId);
        var response = mapper.toResponse(transactionProcessingService.approveTransaction(transaction));
        return ResponseEntity.ok(
                build(response, ApiCodes.TRANSACTION_APPROVED)
        );
    }

}
