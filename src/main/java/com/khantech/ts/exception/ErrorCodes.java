package com.khantech.ts.exception;

import com.khantech.ts.controller.helper.MessageCodeProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes implements MessageCodeProvider {
    RESOURCE_NOT_FOUND("Requested resource not found", 404),
    BAD_TRANSACTION_REQUEST("Requested transaction is not correct", 400),
    INSUFFICIENT_FUNDS("Insufficient fund", 900),
    TRANSACTION_PROCESSING_ERROR("Transaction processing is unsuccessful", 901),
    UNEXPECTED_EXCEPTION("Unexpected exception happened", 999)
    ;

    private final String message;
    private final int code;

}
