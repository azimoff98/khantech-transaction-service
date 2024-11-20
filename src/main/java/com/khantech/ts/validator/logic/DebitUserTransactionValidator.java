package com.khantech.ts.validator.logic;

import com.khantech.ts.dto.request.TransactionRequest;
import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.User;
import com.khantech.ts.validator.BaseUserTransactionValidator;
import com.khantech.ts.validator.TransactionValidationResult;

import java.math.BigDecimal;

public class DebitUserTransactionValidator implements BaseUserTransactionValidator<Transaction, User> {

    @Override
    public TransactionValidationResult isValid(Transaction transaction, User user) {
        BigDecimal userBalance = user.getBalance();
        BigDecimal amount = transaction.getAmount();
        return userBalance.compareTo(amount) >= 0 ? TransactionValidationResult.VALID : TransactionValidationResult.INSUFFICIENT_BALANCE;
    }
}
