package com.khantech.ts.exception;

import com.khantech.ts.controller.helper.MessageCodeProvider;

public class ResourceNotFoundException extends BaseTSException {

    public ResourceNotFoundException(MessageCodeProvider error, Object...resource) {
        super(error);
    }
}
