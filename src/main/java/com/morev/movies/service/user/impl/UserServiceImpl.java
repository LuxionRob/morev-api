package com.morev.movies.service.user.impl;

import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.model.User;
import com.morev.movies.repository.user.UserRepository;
import com.morev.movies.service.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isExisted(ObjectId id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserDTO::new).orElse(null);
    }

    @Override
    public void updateUser(String email, UserDTO userDto) {

    }

    @Override
    public void deleteUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(userRepository::delete);
    }
}
