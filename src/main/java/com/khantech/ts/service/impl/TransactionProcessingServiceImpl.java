package com.khantech.ts.service.impl;

import com.khantech.ts.dto.request.TransactionRequest;
import com.khantech.ts.dto.request.TransactionType;
import com.khantech.ts.dto.response.TransactionResponse;
import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.TransactionStatus;
import com.khantech.ts.entity.User;
import com.khantech.ts.exception.ErrorCodes;
import com.khantech.ts.exception.TransactionProcessingException;
import com.khantech.ts.mapper.TransactionMapper;
import com.khantech.ts.service.TransactionProcessingService;
import com.khantech.ts.service.TransactionService;
import com.khantech.ts.service.UserService;
import com.khantech.ts.validator.BaseUserTransactionValidator;
import com.khantech.ts.validator.TransactionValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionProcessingServiceImpl implements TransactionProcessingService {

    @Value("${transaction-service.transactions.manual-approval-limit}")
    private BigDecimal manualApproveLimit;

    private final UserService userService;
    private final TransactionService transactionService;


    @Override
    public Transaction approveTransaction(Transaction transaction) {
        transaction.setTransactionStatus(TransactionStatus.AWAITING_PROCESSING.getCode());
        transaction.setManuallyApproved(true);
        Transaction approved = transactionService.save(transaction);
        log.info("transaction approved successfully: {}", approved);
        return approved;
    }

    @Override
    public Transaction processTransaction(Transaction transaction) {
        User user = userService.findById(transaction.getUserId());

        synchronized (user) {
            transaction = initTransaction(transaction);
            return processTransaction(transaction, user, validateTransaction(transaction, user));
        }
    }

    @Transactional
    public Transaction processTransaction(Transaction transaction, User user, TransactionValidationResult validationResult) {
        log.debug("pending transaction created : {}", transaction);
        TransactionStatus transactionStatus = createTransactionStatus(transaction, validationResult);
        log.debug("transaction status created: {}", transactionStatus);
        if (transactionStatus == TransactionStatus.APPROVED) {
            if (transaction.getTransactionType() == TransactionType.CREDIT.getId()) {
                user.setBalance(user.getBalance().add(transaction.getAmount()));
            } else {
                user.setBalance(user.getBalance().subtract(transaction.getAmount()));
            }
            transaction.setTransactionStatus(TransactionStatus.APPROVED.getCode());
            transaction = transactionService.save(transaction);
            userService.save(user);
            log.info("transaction approved and processed successfully: {}", transaction);
        } else {
            transaction.setTransactionStatus(transactionStatus.getCode());
            transaction = transactionService.save(transaction);
            log.info("transaction processed : {}", transaction);
        }

        return transaction;
    }

    public TransactionValidationResult validateTransaction(Transaction transaction, User user) {
        TransactionType type = TransactionType.findById(transaction.getTransactionType());
        BaseUserTransactionValidator<Transaction, User> validator = type.getValidator();

        TransactionValidationResult validationResult = validator.isValid(transaction, user);
        if (validationResult == TransactionValidationResult.INSUFFICIENT_BALANCE) {
            throw new TransactionProcessingException(ErrorCodes.INSUFFICIENT_FUNDS);
        }
        return validationResult;
    }

    private Transaction initTransaction(Transaction transaction) {
        transaction.setTransactionStatus(TransactionStatus.PENDING.getCode());
        transaction.setCreatedAt(System.currentTimeMillis());
        transaction.setUpdatedAt(System.currentTimeMillis());
        transaction = transactionService.saveAndFlush(transaction);
        return transaction;
    }

    private TransactionStatus createTransactionStatus(Transaction transaction, TransactionValidationResult validationResult) {
        if (validationResult == TransactionValidationResult.VALID) {
            boolean isManuallyApproved = transaction.isManuallyApproved();
            boolean exceeding = transaction.getAmount().compareTo(manualApproveLimit) > 0 && !isManuallyApproved;
            return exceeding ? TransactionStatus.AWAITING_APPROVAL : TransactionStatus.APPROVED;
        } else {
            return TransactionStatus.REJECTED;
        }
    }

}
