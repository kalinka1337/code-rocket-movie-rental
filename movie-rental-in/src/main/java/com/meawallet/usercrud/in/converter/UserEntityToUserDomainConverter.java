package com.meawallet.usercrud.in.converter;


import com.meawallet.usercrud.domain.User;
import com.meawallet.usercrud.repository.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDomainConverter {

    public User convert(UserEntity entity) {
        return User.builder()
                   .age(entity.getAge())
                   .name(entity.getName())
                    .credits(entity.getCredits())
                   .id(entity.getId())
                   .build();
    }
}
