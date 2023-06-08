package com.morev.movies.model;

import com.morev.movies.dto.movie.MovieDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String releaseDate;
    private String trailerLink;
    @NotBlank(message = "Poster is required")
    private String poster;
    @NotBlank(message = "Genre is required")
    private List<String> genres;
    private List<String> backdrops;
    @DBRef
    private List<Review> reviewIds;

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.title = movieDTO.getTitle();
        this.releaseDate = movieDTO.getReleaseDate();
        this.trailerLink = movieDTO.getTrailerLink();
        this.poster = movieDTO.getPoster();
        this.genres = movieDTO.getGenres();
        this.backdrops = movieDTO.getBackdrops();
        this.reviewIds = movieDTO.getReviewIds();
    }
}
