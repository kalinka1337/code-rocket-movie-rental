package com.meawallet.usercrud.in.controller;


import com.meawallet.usercrud.core.port.in.GetUserUseCase;
import com.meawallet.usercrud.core.port.in.SaveUserUseCase;
import com.meawallet.usercrud.domain.Movie;
import com.meawallet.usercrud.domain.User;
import com.meawallet.usercrud.in.converter.CreateUserInRequestToDomainConverter;
import com.meawallet.usercrud.in.converter.UserToCreateUserInResponseConverter;
import com.meawallet.usercrud.in.converter.UserToGetUserInResponseConverter;
import com.meawallet.usercrud.in.dto.CreateUserInRequest;
import com.meawallet.usercrud.in.dto.CreateUserInResponse;
import com.meawallet.usercrud.in.dto.GetUserInResponse;
import com.meawallet.usercrud.repository.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static com.meawallet.usercrud.domain.Movie.getMovies;

@RestController
@AllArgsConstructor
public class UserController {

    private final SaveUserUseCase saveUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final CreateUserInRequestToDomainConverter createUserInRequestToDomainConverter;
    private final UserToGetUserInResponseConverter userToGetUserInResponseConverter;
    private final UserToCreateUserInResponseConverter userToCreateUserInResponseConverter;

    //    @RequestMapping(method = RequestMethod.POST, path = "/users")
    @PostMapping(value = "/users")
    public ResponseEntity<CreateUserInResponse> create(@RequestBody CreateUserInRequest request) {
        var user = createUserInRequestToDomainConverter.convert(request);
        var createdUser = saveUserUseCase.saveUser(user);
        var responseBody = userToCreateUserInResponseConverter.convert(createdUser);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseBody.id())
                .toUri();

        return ResponseEntity.created(location)
                             .body(responseBody);
    }

    @GetMapping(value = "/users/{id}")
    public GetUserInResponse findUserById(@PathVariable Integer id) {
        var user = getUserUseCase.getUser(id);
        return userToGetUserInResponseConverter.convert(user);
    }

    private final UserRepository userRepository;
    @PostMapping("/buy-movie")
    public ResponseEntity<String> buyMovie(@RequestParam Integer userId, @RequestParam String movieName) {
        User user = userRepository.findUserById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        Optional<Movie> movie = getMovies().stream()
                .filter(m -> m.getName().equalsIgnoreCase(movieName))
                .findFirst();
        if (!movie.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found");
        }

        if (movie.get().getAgeRestriction() > user.getAge()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not old enough to watch this movie");
        }

        if (!movie.get().isReleased()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Movie not released yet");
        }

        BigDecimal price = movie.get().getPrice();
        if (user.getCredits().compareTo(price) < 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have enough credits to buy this movie");
        }

        user.setCredits(user.getCredits().subtract(price));
        user.addMovie(movie.get());
        userRepository.save(user);

        return ResponseEntity.ok("Movie bought successfully");
    }


//    @GetMapping(value = "/test")
//    public String test() {
//        return "Test";
//    }
}
