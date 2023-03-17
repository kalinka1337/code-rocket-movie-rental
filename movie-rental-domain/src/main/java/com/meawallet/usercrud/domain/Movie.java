package com.meawallet.usercrud.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Movie {
    Integer id;
    String name;
    String genre;
    Integer ageRestriction;
    boolean released;
    BigDecimal price;

    public static List<Movie> getMovies(){
        List<Movie> movies = new ArrayList<>();
        movies.add(Movie.builder()
                .id(1)
                .name("The Godfather")
                .genre("Drama")
                .ageRestriction(18)
                .released(true)
                .price(new BigDecimal("9.99"))
                .build());
        movies.add(Movie.builder()
                .id(2)
                .name("The Dark Knight")
                .genre("Action")
                .ageRestriction(16)
                .released(true)
                .price(new BigDecimal("14.99"))
                .build());
        movies.add(Movie.builder()
                .id(3)
                .name("Peppa Pig")
                .genre("Cartoon")
                .ageRestriction(0)
                .released(false)
                .price(new BigDecimal("4.99"))
                .build());
        return movies;
    }
}
