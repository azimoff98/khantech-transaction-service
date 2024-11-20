package com.khantech.ts.entity;

import com.khantech.ts.exception.ErrorCodes;
import com.khantech.ts.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
    PENDING(0),
    APPROVED(1),
    REJECTED(2),
    AWAITING_APPROVAL(3),
    AWAITING_PROCESSING(4);

    int code;

    public static TransactionStatus fromCode(int code) {
        return Arrays.stream(TransactionStatus.values())
                .filter(e -> e.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.RESOURCE_NOT_FOUND));
    }

}
