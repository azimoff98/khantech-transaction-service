package com.khantech.ts.validator;

public interface BaseUserTransactionValidator<T, U> {

    TransactionValidationResult isValid(T t, U u);
}
