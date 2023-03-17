package com.meawallet.usercrud.repository.repository;


import com.meawallet.usercrud.domain.User;
import com.meawallet.usercrud.repository.converter.UserDomainToUserEntityConverter;
import com.meawallet.usercrud.repository.converter.UserEntityToUserDomainConverter;
import com.meawallet.usercrud.repository.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
@AllArgsConstructor
public class UserHibernateRepository implements UserRepository {

    private final SessionFactory sessionFactory;
    private final UserDomainToUserEntityConverter domainToUserEntityConverter;
    private final UserEntityToUserDomainConverter userEntityToUserDomainConverter;

    @Override
    public User save(User user) {
        var entity = domainToUserEntityConverter.convert(user);
        sessionFactory.getCurrentSession().persist(entity);
        return userEntityToUserDomainConverter.convert(entity);
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        var entity = sessionFactory.getCurrentSession().find(UserEntity.class, id);
        return Optional.ofNullable(entity)
                       .map(userEntityToUserDomainConverter::convert);
    }

    @Override
    public List<User> findAllUsers() {
        var session = sessionFactory.getCurrentSession();
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        var root = criteriaQuery.from(UserEntity.class);
        criteriaQuery.select(root);
        var query = session.createQuery(criteriaQuery);
        var userEntities = query.getResultList();
        return userEntities.stream()
                .map(userEntityToUserDomainConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCredits(Integer userId, BigDecimal credits) {
        var session = sessionFactory.getCurrentSession();
        var userEntity = session.find(UserEntity.class, userId);
        if (userEntity != null) {
            userEntity.setCredits(credits);
        }
    }

    @Override
    public void updateUser(User user) {
        var entity = domainToUserEntityConverter.convert(user);
        sessionFactory.getCurrentSession().merge(entity);
    }


}
