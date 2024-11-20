package com.khantech.ts.exception;

import com.khantech.ts.controller.helper.MessageCodeProvider;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomErrorCodeHolder implements MessageCodeProvider {
    private int code;
    private String message;
}
