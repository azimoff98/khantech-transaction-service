package com.khantech.ts.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionApiResponse<T> {

    private Integer code;
    private String message;
    private T data;
    private ApiMetadata metadata;
    private ApiError error;
}
