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
    public UserDTO create(UserDTO userDTO) {
        if (isExisted(userDTO.getId())) {
            return null;
        }
        User user = new User(userDTO);
        userRepository.save(user);
        return userDTO;
    }

    @Override
    public Object getUserById(ObjectId id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new UserDTO(user.get());
        }

        return Optional.empty();
    }

    @Override
    public void updateUser(ObjectId id, UserDTO userDto) {

    }

    @Override
    public void deleteUser(ObjectId id) {

    }
}
