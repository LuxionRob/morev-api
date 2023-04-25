package com.morev.movies.service.movie;

import com.morev.movies.dto.movie.MovieDTO;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<MovieDTO> findAll();
    Optional<MovieDTO> findById(ObjectId id);
    MovieDTO create(MovieDTO movieDto);
    boolean delete(ObjectId id);
    MovieDTO update(MovieDTO movieDto);
}
