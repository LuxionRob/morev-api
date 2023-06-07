package com.morev.movies.service.movie;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.model.Review;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
public interface MovieService {
    List<MovieDTO> findAll();
    Optional<MovieDTO> findById(ObjectId id);
    MovieDTO create(MovieDTO movieDto);
    void delete(ObjectId id);
    MovieDTO update(MovieDTO movieDto);

    List<Review> getAllReviews(ObjectId movieId);

    void deleteReviewInMovie(ObjectId movieId, ObjectId reviewId);
}
