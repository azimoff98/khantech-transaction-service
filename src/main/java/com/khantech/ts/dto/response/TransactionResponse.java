package com.khantech.ts.dto.response;

import com.khantech.ts.entity.TransactionStatus;

import java.math.BigDecimal;

public record TransactionResponse(
        long userId,
        BigDecimal amount,
        TransactionStatus status,
        long createTime
) {
}
