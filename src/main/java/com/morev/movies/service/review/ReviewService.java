package com.morev.movies.service.review;

public interface ReviewService {
    void updateMessage(String id, String message);

    void deleteReviewById(String id);

    boolean postReview(String movieId, String message);

    void upvote(String id, String action);
}
