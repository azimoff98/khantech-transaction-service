package com.khantech.ts.dto.request;

import com.khantech.ts.entity.Transaction;
import com.khantech.ts.entity.TransactionStatus;
import com.khantech.ts.entity.User;
import com.khantech.ts.exception.ErrorCodes;
import com.khantech.ts.exception.ResourceNotFoundException;
import com.khantech.ts.validator.BaseUserTransactionValidator;
import com.khantech.ts.validator.Validatable;
import com.khantech.ts.validator.logic.CreditUserTransactionValidator;
import com.khantech.ts.validator.logic.DebitUserTransactionValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TransactionType implements Validatable<BaseUserTransactionValidator<Transaction, User>> {
    CREDIT(0) {
        @Override
        public BaseUserTransactionValidator<Transaction, User> getValidator() {
            return new CreditUserTransactionValidator();
        }
    },
    DEBIT(1) {
        @Override
        public BaseUserTransactionValidator<Transaction, User> getValidator() {
            return new DebitUserTransactionValidator();
        }
    };

    private int id;


    public static TransactionType findById(int id) {
        return  Arrays.stream(TransactionType.values())
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.RESOURCE_NOT_FOUND));
    }
}
