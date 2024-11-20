package com.khantech.ts.dto.response;

import java.math.BigDecimal;

public record UserResponse (
        long id,
        String username,
        BigDecimal balance
) {
}
