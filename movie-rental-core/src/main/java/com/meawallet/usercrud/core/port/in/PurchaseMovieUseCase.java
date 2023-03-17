package com.meawallet.usercrud.core.port.in;


import com.meawallet.usercrud.core.port.exception.MovieNotFoundException;
import com.meawallet.usercrud.core.port.exception.UserNotFoundException;

public interface PurchaseMovieUseCase {

    void purchaseMovie(Integer userId, Integer movieId) throws UserNotFoundException, MovieNotFoundException;

}
