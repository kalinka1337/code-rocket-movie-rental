package com.meawallet.usercrud.repository.repository;

import com.meawallet.usercrud.core.port.out.UpdateUserPort;
import com.meawallet.usercrud.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class UpdateUserAdapter implements UpdateUserPort {
    private final UserRepository userRepository;

//    @Override
//    public void updateCredits(Integer userId, BigDecimal credits) {
//        userRepository.updateCredits(userId, credits);
//    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

//    @Override
//    public void update(User user) {
//
//    }
}
