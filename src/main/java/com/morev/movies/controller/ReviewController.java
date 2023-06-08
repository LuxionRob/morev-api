package com.morev.movies.controller;

import com.morev.movies.model.Review;
import com.morev.movies.service.movie.MovieService;
import com.morev.movies.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final MovieService movieService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Review> findReviewOfFilm(@PathVariable String id) {
        return movieService.getAllReviews(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String postReview(@PathVariable String id, @RequestBody String message) {
        if (reviewService.postReview(id, message)) {
            return "post_success";
        } else return "post_error";
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleteReviewById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateMessage(@PathVariable String id,
                              @RequestBody String message) {
        reviewService.updateMessage(id, message);
    }

    @PutMapping("/{id}/{action}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void upvote(@PathVariable String id, @PathVariable String action) {
        reviewService.upvote(id, action);
    }
}
