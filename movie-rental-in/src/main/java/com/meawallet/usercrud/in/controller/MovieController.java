package com.meawallet.usercrud.in.controller;
import org.springframework.web.bind.annotation.GetMapping;
public class MovieController
{

    @GetMapping(value = "/movies")
    public String test() {
        return "Movie Test";
    }
}
