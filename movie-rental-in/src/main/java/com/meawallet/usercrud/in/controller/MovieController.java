package com.meawallet.usercrud.in.controller;
import com.meawallet.usercrud.domain.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieController {

    @GetMapping("/movies")
    public List<Movie> getMovies() {
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

