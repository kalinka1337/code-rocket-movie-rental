package com.meawallet.usercrud.in.dto;

import java.math.BigDecimal;

public record CreateMovieInResponse(
        Integer id,
        String name,
        String genre,
        int ageRestriction,
        boolean isReleased,
        BigDecimal price
) {
}
