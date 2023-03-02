package com.meawallet.usercrud.repository.converter;

import com.meawallet.usercrud.domain.Movie;
import com.meawallet.usercrud.repository.entity.MovieEntity;
import org.springframework.stereotype.Component;
@Component
public class MovieDomainToMovieEntityConverter {

    public MovieEntity convert(Movie movie) {
        return MovieEntity.builder()
                    .name(movie.getName())
                    .genre(movie.getGenre())
                    .ageRestriction(movie.getAgeRestriction())
                    .isReleased(movie.isReleased())
                    .price(movie.getPrice())
                    .build();
    }
}

