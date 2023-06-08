package com.morev.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
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
    private ObjectId id;
    private ObjectId userId;
    private ObjectId movieId;
    private String content;
    private int like;
    private Date timestamp;
    private List<ObjectId> upvotedUserIds;
    private List<ObjectId> downvotedUserIds;
}
