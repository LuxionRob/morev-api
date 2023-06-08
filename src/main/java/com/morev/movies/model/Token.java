package com.morev.movies.model;

import com.morev.movies.enumeration.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @Id

    public String token;

    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @DocumentReference
    public User user;
}
