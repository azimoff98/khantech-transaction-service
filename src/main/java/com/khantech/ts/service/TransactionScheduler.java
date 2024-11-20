package com.khantech.ts.service;

import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.TransactionStatus;
import com.khantech.ts.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionScheduler {

    private final TransactionService transactionService;
    private final TransactionProcessingService transactionProcessingService;

    @Scheduled(cron = "${transaction-service.transactions.auto-approve-pending}")
    public void approvePending() {
        log.info("Starting to approve pending transactions");

        List<Transaction> pendingTransactions = transactionService.findAllByStatus(TransactionStatus.PENDING);
        log.info("Total {} pending transactions found", pendingTransactions.size());

        pendingTransactions.forEach(transactionProcessingService::approveTransaction);
    }

    @Scheduled(cron = "${transaction-service.transactions.auto-process-approved}")
    public void processApproved() {
        log.info("Starting to process approved transactions");

        List<Transaction> awaitingTransactions = transactionService.findAllByStatus(TransactionStatus.AWAITING_PROCESSING);
        log.info("Total {} awaiting transactions found", awaitingTransactions.size());

        awaitingTransactions.forEach(transactionProcessingService::processTransaction);
    }
}
