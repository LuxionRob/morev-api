package com.morev.movies.model;

import com.morev.movies.dto.movie.MovieDTO;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    private ObjectId id;
    private String title;
    private String releaseDate;
    private String trailerLink;
    private String poster;
    private List<String> genres;
    private List<String> backdrops;
    @DocumentReference
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
