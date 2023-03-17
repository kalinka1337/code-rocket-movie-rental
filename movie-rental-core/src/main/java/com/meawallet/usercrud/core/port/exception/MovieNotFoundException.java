package com.meawallet.usercrud.core.port.exception;


public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String message) {
        super(message);
    }
}

