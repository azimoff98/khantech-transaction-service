package com.khantech.ts.validator.input;

import com.khantech.ts.dto.request.TransactionType;
import com.khantech.ts.validator.input.annotation.ValidTransactionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransactionTypeValidator implements ConstraintValidator<ValidTransactionType, TransactionType> {

    @Override
    public boolean isValid(TransactionType value, ConstraintValidatorContext constraintValidatorContext) {
        return value == TransactionType.CREDIT || value == TransactionType.DEBIT;
    }
}
