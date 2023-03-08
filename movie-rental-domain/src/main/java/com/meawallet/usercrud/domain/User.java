package com.meawallet.usercrud.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class User {

    Integer id;
    String name;
    Integer age;
    BigDecimal credits;
    List<Movie> movies;

    public User addMovie(Movie movie) {
        List<Movie> updatedMovies = new ArrayList<>();
        if (movies != null) {
            updatedMovies.addAll(movies);
        }
        updatedMovies.add(movie);
        return this.toBuilder().movies(updatedMovies).build();
    }

    public User setCredits(BigDecimal credits) {
        return this.toBuilder().credits(credits).build();
    }
}
