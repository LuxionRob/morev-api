package com.morev.movies.repository.review;

import com.morev.movies.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
    Optional<Review> findById(ObjectId id);

    Optional<List<Review>> findAllByMovieId(ObjectId id);

    void deleteById(ObjectId id);
}
