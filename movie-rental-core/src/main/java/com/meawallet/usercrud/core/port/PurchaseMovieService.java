package com.meawallet.usercrud.core.port;

import com.meawallet.usercrud.core.port.exception.MovieNotFoundException;
import com.meawallet.usercrud.core.port.exception.UserNotFoundException;
import com.meawallet.usercrud.core.port.in.PurchaseMovieUseCase;
import com.meawallet.usercrud.core.port.out.FindMovieByIdPort;
import com.meawallet.usercrud.core.port.out.FindUserByIdPort;
import com.meawallet.usercrud.core.port.out.UpdateUserPort;
import com.meawallet.usercrud.domain.Movie;
import com.meawallet.usercrud.domain.User;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Component
@Service
@AllArgsConstructor
public class PurchaseMovieService implements PurchaseMovieUseCase {

    private final FindUserByIdPort findUserByIdPort;
    private final FindMovieByIdPort findMovieByIdPort;
    private final UpdateUserPort updateUserPort;

    @Override
    public void purchaseMovie(Integer userId, Integer movieId) throws UserNotFoundException, MovieNotFoundException {
        User user = findUserByIdPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Movie movie = findMovieByIdPort.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        if (user.getCredits().compareTo(movie.getPrice()) < 0) {
            throw new IllegalArgumentException("User does not have enough credits to purchase the movie");
        }

        User updatedUser = user.setCredits(user.getCredits().subtract(movie.getPrice()))
                .addMovie(movie);
        updateUserPort.updateUser(updatedUser);
    }

}
