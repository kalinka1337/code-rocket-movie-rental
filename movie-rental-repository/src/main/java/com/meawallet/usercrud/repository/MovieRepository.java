package com.meawallet.usercrud.repository;

import com.meawallet.usercrud.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> findAll();
    Movie findById(Integer id);

    Optional<Movie> findById(Long id);

    void save(Movie movie);
    void update(Movie movie);
    void delete(Movie movie);
}
