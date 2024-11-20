package com.khantech.ts.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record UserCreateRequest (
        @NonNull @NotBlank String username
) {
}
