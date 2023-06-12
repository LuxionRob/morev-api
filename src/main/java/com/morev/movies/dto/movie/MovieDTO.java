package com.morev.movies.dto.movie;

import com.morev.movies.model.Movie;
import com.morev.movies.model.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    @Id
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private LocalDate releaseDate;
    private String trailerLink;
    @NotBlank
    private String poster;
    @Min(1)
    @Max(5)
    private double rating;
    @NotBlank
    private List<String> genres;
    private List<String> backdrops;
    private List<Review> reviewIds;
    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.releaseDate = movie.getReleaseDate();
        this.trailerLink = movie.getTrailerLink();
        this.poster = movie.getPoster();
        this.genres = movie.getGenres();
        this.backdrops = movie.getBackdrops();
        this.reviewIds = movie.getReviewIds();
        this.rating = movie.getRating();
    }
}
