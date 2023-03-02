package com.meawallet.usercrud.repository;


import com.meawallet.usercrud.domain.Movie;
import com.meawallet.usercrud.repository.converter.MovieDomainToMovieEntityConverter;
import com.meawallet.usercrud.repository.converter.MovieEntityToMovieDomainConverter;
import com.meawallet.usercrud.repository.entity.MovieEntity;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Component
@AllArgsConstructor
public class MovieRepositoryHibernate implements MovieRepository {

    private SessionFactory sessionFactory;
    private final MovieDomainToMovieEntityConverter movieDomainToMovieEntityConverter;
    private final MovieEntityToMovieDomainConverter movieEntityToMovieDomainConverter;
    @Override
    public List<Movie> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Movie", Movie.class).getResultList();
    }

    @Override
    public Movie findById(Integer id) {
        return null;
    }

    @Override
    public Optional<Movie> findById(Long id) {
        var entity = sessionFactory.getCurrentSession().find(MovieEntity.class, id);
        return Optional.ofNullable(entity)
                .map(movieEntityToMovieDomainConverter::convert);
    }

    @Override
    public void save(Movie movie) {
        var entity = movieDomainToMovieEntityConverter.convert(movie);
        sessionFactory.getCurrentSession().persist(entity);
    }

    @Override
    public void update(Movie movie) {
        Session session = sessionFactory.getCurrentSession();
        session.update(movie);
    }

    @Override
    public void delete(Movie movie) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(movie);
    }
}
