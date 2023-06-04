package com.morev.movies.repository.user;

import com.morev.movies.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String username);
    Optional<User> findByVerificationCode(String code);
    Optional<User> findByForgotPasswordCode(String code);
}

