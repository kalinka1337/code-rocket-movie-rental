package com.meawallet.usercrud.repository.repository;

import com.meawallet.usercrud.domain.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findUserById(Integer id);

//    List<User> findAllUsers();

//    List<User> findAll();

    List<User> findAllUsers();

    void updateCredits(Integer userId, BigDecimal newCredits);
    void updateUser(User user);
}
