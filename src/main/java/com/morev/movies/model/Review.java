package com.morev.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private String id;
    private String userId;
    private String movieId;
    private String content;
    private int like;
    private Date timestamp;
    private List<String> upvotedUserIds;
    private List<String> downvotedUserIds;
}
