package com.meawallet.usercrud.in.dto;

import java.math.BigDecimal;

public record GetUserInResponse(
        Integer id,
        String name,
        Integer age,
        BigDecimal credits
) {
}
