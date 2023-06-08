package com.morev.movies.repository.review;

import com.morev.movies.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findById(String id);

    Optional<List<Review>> findAllByMovieId(String id);

    void deleteById(String id);
}
