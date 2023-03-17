package com.meawallet.usercrud.core.port.out;

import com.meawallet.usercrud.domain.Movie;

import java.util.Optional;

public interface FindMovieByIdPort {

    Optional<Movie> findById(Integer id);

}
