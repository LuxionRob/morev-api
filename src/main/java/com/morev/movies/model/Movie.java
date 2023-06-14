package com.morev.movies.model;

import com.morev.movies.dto.movie.MovieDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    private String id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Release date is required")
    private LocalDate releaseDate;
    private String trailerLink;
    @NotBlank(message = "Poster is required")
    private String poster;
    @NotBlank(message = "Genre is required")
    private List<String> genres;
    private List<String> backdrops;
    @Min(1)
    @Max(5)
    private double rating;
    @DBRef
    private List<Review> reviewIds;

    public Movie(MovieDTO movieDTO) {
        String[] splitedDateString = movieDTO.getReleaseDate().split(" ");
        LocalDate date = LocalDate.of(Integer.parseInt(splitedDateString[0]), Integer.parseInt(splitedDateString[1]) + 1, Integer.parseInt(splitedDateString[2]));

        this.id = movieDTO.getId();
        this.title = movieDTO.getTitle();
        this.releaseDate = date;
        this.trailerLink = movieDTO.getTrailerLink();
        this.poster = movieDTO.getPoster();
        this.genres = movieDTO.getGenres();
        this.backdrops = movieDTO.getBackdrops();
        this.reviewIds = movieDTO.getReviewIds();
        this.rating = movieDTO.getRating();
    }
}
