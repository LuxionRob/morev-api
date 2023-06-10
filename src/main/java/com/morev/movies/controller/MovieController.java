package com.morev.movies.controller;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> findAllMovies() {
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MovieDTO>> findMovieById(@PathVariable String id) {
        return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public double getRating(@PathVariable String id) {
        return movieService.getRating(id);
    }

    @PostMapping("/{id}/rating/{rating}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String rate(@PathVariable String id, @PathVariable int rating) {
        if (movieService.rate(id, rating)) {
            return "rating_success";
        }
        return "rating_error";

    }

    @PutMapping("/{id}/rating/{rating}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String changeRate(@PathVariable String id, @PathVariable int rating) {
        if (movieService.changeRate(id, rating)) {
            return "rating_success";
        }
        return "rating_error";    }
}
