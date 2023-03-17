package com.meawallet.usercrud.repository.repository;

import com.meawallet.usercrud.domain.Movie;
import com.meawallet.usercrud.core.port.out.FindMovieByIdPort;
import com.meawallet.usercrud.repository.converter.MovieEntityToMovieDomainConverter;
import com.meawallet.usercrud.repository.entity.MovieEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
@AllArgsConstructor
@Component
@Transactional
public class FindMovieByIdAdapter implements FindMovieByIdPort {
    private final SessionFactory sessionFactory;
    private final MovieEntityToMovieDomainConverter movieEntityToMovieDomainConverter;

    @Override
    public Optional<Movie> findById(Integer movieId) {
        var session = sessionFactory.getCurrentSession();
        var movieEntity = session.find(MovieEntity.class, movieId);
        return Optional.ofNullable(movieEntity)
                .map(movieEntityToMovieDomainConverter::convert);
    }
}
