package com.morev.movies.controller;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.service.movie.MovieService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> findAllMovies() {
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MovieDTO>> findMovieById(@PathVariable ObjectId id) {
        return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> create(@RequestBody MovieDTO movieDto) {
        MovieDTO newMovie = movieService.create(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovie);
    }

    @PutMapping
    public ResponseEntity<MovieDTO> update(@RequestBody MovieDTO movieDto) {
        MovieDTO updatedMovie = movieService.update(movieDto);
        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieDTO> delete(@PathVariable("id") ObjectId id) {
        boolean isSuccess = movieService.delete(id);
        if (isSuccess) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
