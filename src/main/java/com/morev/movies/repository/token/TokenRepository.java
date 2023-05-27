package com.morev.movies.repository.token;
import com.morev.movies.model.Token;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, ObjectId> {

    @Query("{'id': :#{#id}, 'expired': false, 'revoked': false}")
    List<Token> findAllValidTokenByUser(@Param("id") ObjectId id);

    Optional<Token> findByToken(String token);
}

