package com.meawallet.usercrud.repository.converter;

import com.meawallet.usercrud.domain.Movie;
import com.meawallet.usercrud.repository.entity.MovieEntity;
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

