package com.morev.movies.service.movie;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.model.Review;

import java.util.List;
import java.util.Optional;
public interface MovieService {
    List<MovieDTO> findAll();
    Optional<MovieDTO> findById(String id);
    MovieDTO create(MovieDTO movieDto);
    void delete(String id);
    MovieDTO update(MovieDTO movieDto);

    List<Review> getAllReviews(String movieId);

    void deleteReviewInMovie(String movieId, String reviewId);

    double getRating(String id);

    boolean rate(String id, int rating);

    boolean changeRate(String id, int rating);
}
