package com.khantech.ts.exception;

import com.khantech.ts.controller.helper.MessageCodeProvider;

public class TransactionProcessingException extends BaseTSException {

    public TransactionProcessingException(MessageCodeProvider error, Object...args) {
        super(error);
    }
}
