package com.meawallet.usercrud.repository.repository;

import com.meawallet.usercrud.domain.Movie;
import java.util.Optional;

public interface MovieRepository {

    Optional<Movie> findById(Integer id);

    Movie save(Movie movie);

}
