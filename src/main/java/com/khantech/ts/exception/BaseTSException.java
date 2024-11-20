package com.khantech.ts.exception;

import com.khantech.ts.controller.helper.MessageCodeProvider;
import lombok.Getter;

@Getter
public class BaseTSException extends RuntimeException {
    private MessageCodeProvider messageCodeProvider;

    public BaseTSException(MessageCodeProvider messageCodeProvider) {
        super(messageCodeProvider.getMessage());
        this.messageCodeProvider = messageCodeProvider;
    }
}
