package com.meawallet.usercrud.in.controller;


import com.meawallet.usercrud.core.port.in.GetAllUsersUseCase;
import com.meawallet.usercrud.core.port.in.GetUserUseCase;
import com.meawallet.usercrud.core.port.in.PurchaseMovieUseCase;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meawallet.usercrud.domain.Movie.getMovies;

@RestController
@AllArgsConstructor
public class UserController {

    private final SaveUserUseCase saveUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final PurchaseMovieUseCase purchaseMovieUseCase;
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

    @GetMapping(value = "/users")
    public List<GetUserInResponse> findAll() {
//        log.debug("Received find all users request");
        return getAllUsersUseCase.getAll().stream()
                .map(userToGetUserInResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{id}")
    public GetUserInResponse findUserById(@PathVariable Integer id) {
        var user = getUserUseCase.getUser(id);
        return userToGetUserInResponseConverter.convert(user);
    }

    private final UserRepository userRepository;
    @PostMapping("/buy-movie")
    public ResponseEntity<String> buyMovie(@RequestParam Integer id, @RequestParam String movieName) {
        User user = getUserUseCase.getUser(id);
        Optional<Movie> movie = Movie.getMovies().stream().filter(m -> m.getName().equalsIgnoreCase(movieName)).findFirst();

        if (!movie.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }

        Movie selectedMovie = movie.get();

        if (selectedMovie.getAgeRestriction() > user.getAge()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not old enough to watch this movie");
        }

        if (!selectedMovie.isReleased()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Movie is not released yet");
        }

        BigDecimal price = selectedMovie.getPrice();
        if (user.getCredits().compareTo(price) < 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not have enough credits to buy this movie");
        }

        user.setCredits(user.getCredits().subtract(price));
        user.addMovie(selectedMovie);
        saveUserUseCase.saveUser(user);

        return ResponseEntity.ok("Movie bought successfully");
    }

    @PostMapping("/users/{userId}/movies/{movieId}")
    public ResponseEntity<Void> purchaseMovie(@PathVariable Integer userId, @PathVariable Integer movieId) {
        User user = getUserUseCase.getUser(userId);
        Optional<Movie> optionalMovie = getMovies().stream().filter(movie -> movie.getId().equals(movieId)).findFirst();

        if (!optionalMovie.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Movie movie = optionalMovie.get();

        if (!movie.isReleased()) {
            return ResponseEntity.badRequest().build();
        }

        if (movie.getAgeRestriction() > user.getAge()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (user.getCredits().compareTo(movie.getPrice()) < 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        user.deductCredits(movie.getPrice());
        user.addMovie(movie);
        saveUserUseCase.saveUser(user);

        return ResponseEntity.ok().build();
    }

}


//    @GetMapping(value = "/test")
//    public String test() {
//        return "Test";
//    }

