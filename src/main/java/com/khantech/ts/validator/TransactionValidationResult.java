package com.khantech.ts.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionValidationResult {
    VALID(0, "Validation is successful"),
    INSUFFICIENT_BALANCE(1, "Insufficient funds"),
    OTHER(2, "Other validation problem");

    private int code;
    private String message;

}
