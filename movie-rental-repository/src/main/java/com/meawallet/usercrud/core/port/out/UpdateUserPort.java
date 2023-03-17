package com.meawallet.usercrud.core.port.out;

import com.meawallet.usercrud.domain.User;

import java.math.BigDecimal;

public interface UpdateUserPort {

    void updateCredits(Integer userId, BigDecimal credits);

    void updateUser(User user);

    void update(User user);
}
