package com.morev.movies.service.review;

import org.bson.types.ObjectId;

public interface ReviewService {
    void updateMessage(ObjectId id, String message);

    void deleteReviewById(ObjectId id);

    boolean postReview(ObjectId movieId, String message);
    void upvote(ObjectId id, String action);
}
