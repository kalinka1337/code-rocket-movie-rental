package com.meawallet.usercrud.database.converter;

import com.meawallet.usercrud.database.MovieEntity;
import com.meawallet.usercrud.domain.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieEntityToMovieDomainConverter {

    public Movie convert(MovieEntity entity) {
        return Movie.builder()
                .name(entity.getName())
                .genre(entity.getGenre())
                .ageRestriction(entity.getAgeRestriction())
                .released(entity.isReleased())
                .price(entity.getPrice())
                .build();
    }
}

