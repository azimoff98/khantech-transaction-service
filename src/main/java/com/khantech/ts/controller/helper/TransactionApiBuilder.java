package com.khantech.ts.controller.helper;

import com.khantech.ts.controller.constants.ApiCodes;
import com.khantech.ts.dto.response.ApiError;
import com.khantech.ts.dto.response.ApiMetadata;
import com.khantech.ts.dto.response.TransactionApiResponse;
import com.khantech.ts.exception.CustomErrorCodeHolder;
import com.khantech.ts.exception.ErrorCodes;

public interface TransactionApiBuilder {

    default <T> TransactionApiResponse<T> build(T t, MessageCodeProvider messageCodeProvider) {
        TransactionApiResponse<T> response = new TransactionApiResponse<>();
        if(messageCodeProvider instanceof ErrorCodes || messageCodeProvider instanceof CustomErrorCodeHolder) {
            response.setError(buildError(messageCodeProvider));
        }
        if(messageCodeProvider instanceof ApiCodes apiCode) {
            response.setCode(apiCode.getCode());
            response.setMessage(apiCode.getMessage());
        }
        response.setMetadata(buildMetadata());
        response.setData(t);

        return  response;
    }

    default ApiMetadata buildMetadata() {
        return ApiMetadata.builder()
                .timestamp(System.currentTimeMillis())
                .build();
    }

    default ApiError buildError(MessageCodeProvider element) {
        return ApiError.builder()
                .errorCode(element.getCode())
                .message(element.getMessage())
                .build();
    }
}
