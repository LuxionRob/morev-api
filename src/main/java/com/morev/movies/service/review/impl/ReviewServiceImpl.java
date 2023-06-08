package com.morev.movies.service.review.impl;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.model.Movie;
import com.morev.movies.model.Review;
import com.morev.movies.repository.movie.MovieRepository;
import com.morev.movies.repository.review.ReviewRepository;
import com.morev.movies.service.movie.MovieService;
import com.morev.movies.service.review.ReviewService;
import com.morev.movies.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final MovieService movieService;

    @Override
    public void updateMessage(String id, String message) {
        Optional<Review> curReview = reviewRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        if (curReview.isPresent()) {
            UserDTO user = userService.getUserByEmail(principal.getUsername());
            if (user != null) {
                if (user.getId().equals(curReview.get().getUserId())) {
                    curReview.get().setContent(message);
                    reviewRepository.save(curReview.get());
                }
            }
        }
    }

    @Override
    public void deleteReviewById(String id) {
        Optional<Review> curReview = reviewRepository.findById(id);
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) user.getPrincipal();
        if (curReview.isPresent()) {
            if (userService.getUserByEmail(principal.getUsername()) != null
                    || principal.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin:delete"))) {
                reviewRepository.deleteById(id);
                movieService.deleteReviewInMovie(curReview.get().getMovieId(), id);
            }
        }
    }

    @Override
    public boolean postReview(String movieId, String message) {
        Optional<MovieDTO> movie = movieService.findById(movieId);
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) user.getPrincipal();
        if (movie.isPresent()) {
            UserDTO auth = userService.getUserByEmail(principal.getUsername());
            if (auth.getEmail() != null) {
                List<Review> reviewList = movie.get().getReviewIds();
                Review newReview = new Review(
                        new ObjectId().toHexString(),
                        auth.getId(),
                        movieId,
                        message,
                        0,
                        new Date(System.currentTimeMillis()),
                        new ArrayList<>(),
                        new ArrayList<>()
                );
                reviewList.add(newReview);
                movie.get().setReviewIds(reviewList);
                movieRepository.save(new Movie(movie.get()));
                reviewRepository.save(newReview);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void upvote(String id, String action) {
        Optional<Review> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            Authentication user = SecurityContextHolder.getContext().getAuthentication();
            UserDetails principal = (UserDetails) user.getPrincipal();
            UserDTO authenticatedUser = userService.getUserByEmail(principal.getUsername());

            if (!authenticatedUser.getId().equals(review.get().getUserId())) {
                List<String> upvotedUserIds = review.get().getUpvotedUserIds();
                List<String> downvotedUserIds = review.get().getDownvotedUserIds();

                boolean upvoteContain = upvotedUserIds.contains(authenticatedUser.getId());
                boolean downvoteContain = downvotedUserIds.contains(authenticatedUser.getId());
                boolean isUpvote = action.equals("upvote");
                boolean isDownvote = action.equals("downvote");

                if (upvoteContain && isUpvote) {
                    upvotedUserIds.remove(authenticatedUser.getId());
                } else if (downvoteContain && isDownvote) {
                    downvotedUserIds.remove(authenticatedUser.getId());
                } else if (isUpvote && downvoteContain) {
                    downvotedUserIds.remove(authenticatedUser.getId());
                    upvotedUserIds.add(authenticatedUser.getId());
                } else if (isDownvote && upvoteContain) {
                    upvotedUserIds.remove(authenticatedUser.getId());
                    downvotedUserIds.add(authenticatedUser.getId());
                } else if (!upvoteContain && !downvoteContain) {
                    if (isUpvote) {
                        upvotedUserIds.add(authenticatedUser.getId());
                    } else if (isDownvote) {
                        downvotedUserIds.add(authenticatedUser.getId());
                    }
                }
                review.get().setDownvotedUserIds(downvotedUserIds);
                review.get().setUpvotedUserIds(upvotedUserIds);
                review.get().setLike(upvotedUserIds.size() - downvotedUserIds.size());
                reviewRepository.save(review.get());
            }
        }
    }
}
