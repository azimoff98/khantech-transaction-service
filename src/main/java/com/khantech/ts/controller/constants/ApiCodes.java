package com.khantech.ts.controller.constants;

import com.khantech.ts.controller.helper.MessageCodeProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiCodes implements MessageCodeProvider {

    TRANSACTION_SUCCESSFUL(10, "Transaction Successfully Created"),
    TRANSACTION_APPROVED(11, "Transaction Approved"),
    SUCCESSFUL_RESPONSE(12, "Successful Response"),
    USER_CREATED(13, "User successfully created")
    ;

    private int code;
    private String message;
}
