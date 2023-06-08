package com.morev.movies.service.movie.impl;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.model.Movie;
import com.morev.movies.model.Review;
import com.morev.movies.repository.movie.MovieRepository;
import com.morev.movies.repository.review.ReviewRepository;
import com.morev.movies.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<MovieDTO> findAll() {
        List<Movie> list = movieRepository.findAll();
        List<MovieDTO> dtoList = new ArrayList<>();
        list.forEach(movie -> dtoList.add(new MovieDTO(movie)));
        return dtoList;
    }

    @Override
    public Optional<MovieDTO> findById(ObjectId id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            MovieDTO dto = new MovieDTO(movie.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public MovieDTO create(@RequestBody MovieDTO movieDto) {
        movieDto.setId(new ObjectId());
        Movie newMovie = new Movie(movieDto);
        return new MovieDTO(movieRepository.save(newMovie));
    }

    @Override
    public void delete(ObjectId id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        optionalMovie.ifPresent(movieRepository::delete);
    }

    @Override
    public MovieDTO update(MovieDTO movieDto) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieDto.getId());

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            movie.setTitle(movieDto.getTitle());
            movie.setReleaseDate(movieDto.getReleaseDate());
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
    public List<Review> getAllReviews(ObjectId movieId) {
        Optional<List<Review>> reviewIdList = reviewRepository.findAllByMovieId(movieId);
        return reviewIdList.orElse(null);
    }

    @Override
    public void deleteReviewInMovie(ObjectId movieId, ObjectId reviewId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            List<Review> reviews = movie.get().getReviewIds();
            reviews.removeIf(review -> review.getId().equals(reviewId));
        }
    }
}
