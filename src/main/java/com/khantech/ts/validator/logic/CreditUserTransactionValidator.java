package com.khantech.ts.validator.logic;

import com.khantech.ts.dto.request.TransactionRequest;
import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.User;
import com.khantech.ts.validator.BaseUserTransactionValidator;
import com.khantech.ts.validator.TransactionValidationResult;

public class CreditUserTransactionValidator implements BaseUserTransactionValidator<Transaction, User> {

    @Override
    public TransactionValidationResult isValid(Transaction transaction, User user) {

        //For now all credit transactions are valid.
        //Later can be validated for monthly maximum income or something.

        return TransactionValidationResult.VALID;
    }
}
