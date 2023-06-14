package com.morev.movies.service.movie.impl;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.model.Movie;
import com.morev.movies.model.Rating;
import com.morev.movies.model.Review;
import com.morev.movies.repository.movie.MovieRepository;
import com.morev.movies.repository.rating.RatingRepository;
import com.morev.movies.repository.review.ReviewRepository;
import com.morev.movies.service.movie.MovieService;
import com.morev.movies.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final RatingRepository ratingRepository;
    private final UserService userService;

    @Override
    public List<MovieDTO> findAll() {
        List<Movie> list = movieRepository.findAll();
        List<MovieDTO> dtoList = new ArrayList<>();
        list.forEach(movie -> dtoList.add(new MovieDTO(movie)));
        return dtoList;
    }

    @Override
    public Optional<MovieDTO> findById(String id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            MovieDTO dto = new MovieDTO(movie.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public MovieDTO create(MovieDTO movieDto) {
        movieDto.setId(new ObjectId().toHexString());
        Movie newMovie = new Movie(movieDto);
        return new MovieDTO(movieRepository.save(newMovie));
    }

    @Override
    public void delete(String id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        optionalMovie.ifPresent(movieRepository::delete);
    }

    @Override
    public MovieDTO update(MovieDTO movieDto) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieDto.getId());

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            String[] splitedDateString = movieDto.getReleaseDate().split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(splitedDateString[0]), Integer.parseInt(splitedDateString[1]), Integer.parseInt(splitedDateString[2]));
            movie.setTitle(movieDto.getTitle());
            movie.setReleaseDate(date);
            movie.setTrailerLink(movieDto.getTrailerLink());
            movie.setPoster(movieDto.getPoster());
            movie.setGenres(movieDto.getGenres());
            movie.setBackdrops(movieDto.getBackdrops());
            movie.setReviewIds(movieDto.getReviewIds());

            movieRepository.save(movie);
            return movieDto;
        } else {
            return null;
        }
    }

    @Override
    public List<Review> getAllReviews(String movieId) {
        Optional<List<Review>> reviewIdList = reviewRepository.findAllByMovieId(movieId);
        return reviewIdList.orElse(null);
    }

    @Override
    public void deleteReviewInMovie(String movieId, String reviewId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            List<Review> reviews = movie.get().getReviewIds();
            reviews.removeIf(review -> review.getId().equals(reviewId));
        }
    }

    @Override
    public double getRating(String id) {
        Optional<List<Rating>> ratingList = ratingRepository.findAllByMovieId(id);
        double rate = 0.0;
        if (ratingList.isPresent() && ratingList.get().size() > 0) {

            for (Rating rating : ratingList.get()) {
                rate += rating.getRating();
            }

            return rate / ratingList.get().size();
        }
        return 0.0;
    }

    @Override
    public boolean rate(String id, int rating) {
        if (rating > 5 || rating < 1) {
            return false;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        UserDTO user = userService.getUserByEmail(principal.getUsername());
        if (ratingRepository.findByUserIdAndMovieId(user.getId(), id).isEmpty()) {
            Optional<Movie> movie = movieRepository.findById(id);
            ratingRepository.save(new Rating(new ObjectId().toHexString(), id, user.getId(), rating));
            movie.get().setRating(this.getRating(id));
            movieRepository.save(movie.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean changeRate(String id, int rating) {
        if (rating > 5 || rating < 1) {
            return false;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();

        UserDTO user = userService.getUserByEmail(principal.getUsername());
        Optional<Rating> userRating = ratingRepository.findByUserIdAndMovieId(user.getId(), id);

        if (userRating.isPresent()) {
            Optional<Movie> movie = movieRepository.findById(id);
            userRating.get().setRating(rating);
            ratingRepository.save(userRating.get());
            movie.get().setRating(this.getRating(id));
            movieRepository.save(movie.get());
            return true;
        }
        return false;
    }
}
