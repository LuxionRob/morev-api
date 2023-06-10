package com.morev.movies.repository.rating;

import com.morev.movies.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends MongoRepository<Rating, String> {
    Optional<List<Rating>> findAllByMovieId(String movieId);
    Optional<Rating> findByUserIdAndMovieId(String userId, String movieId);
}
