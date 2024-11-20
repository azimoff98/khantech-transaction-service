package com.khantech.ts.dto.request;

import com.khantech.ts.validator.input.annotation.ValidTransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequest(
        @Positive
        long userId,
        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,
        @ValidTransactionType
        TransactionType transactionType
) {

}
