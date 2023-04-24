package com.morev.movies.service.movie.impl;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.model.Movie;
import com.morev.movies.repository.movie.MovieRepository;
import com.morev.movies.service.movie.MovieService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

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
        boolean exists = movieRepository.existsById(movieDto.getId());
        if (exists) {
            ObjectId newId = new ObjectId();
            movieDto.setId(newId);
        }
        Movie newMovie = new Movie(movieDto);
        System.out.println(newMovie);
        return new MovieDTO(movieRepository.save(newMovie));
    }

    @Override
    public boolean delete(ObjectId id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            movieRepository.delete(optionalMovie.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public MovieDTO update(ObjectId id, MovieDTO movieDto) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);

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
}
