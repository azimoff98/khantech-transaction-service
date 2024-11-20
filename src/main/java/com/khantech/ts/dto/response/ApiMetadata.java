package com.khantech.ts.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiMetadata {
    private long timestamp;
    private String path;
    private Integer from;
    private Integer to;

}
