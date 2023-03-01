package com.meawallet.usercrud.database.converter;

import com.meawallet.usercrud.database.MovieEntity;
import com.meawallet.usercrud.domain.Movie;
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

