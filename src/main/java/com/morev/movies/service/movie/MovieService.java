package com.morev.movies.service.movie;

import com.morev.movies.model.Movie;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    public List<Movie> findAll();
    public Optional<Movie> findById(ObjectId id);
}
