package com.morev.movies.controller;

import com.morev.movies.model.Movie;
import com.morev.movies.service.movie.impl.MovieServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private MovieServiceImpl movieServiceImpl;
    @Autowired
    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAllMovies() {
        return new ResponseEntity<List<Movie>>(movieServiceImpl.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> findMovieById(@PathVariable  ObjectId id) {
        return new ResponseEntity<Optional<Movie>>(movieServiceImpl.findById(id), HttpStatus.OK);
    }
}
