package com.khantech.ts.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private int errorCode;
    private String message;
}
