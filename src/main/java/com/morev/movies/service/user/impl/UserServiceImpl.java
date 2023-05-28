package com.morev.movies.service.user.impl;

import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.model.User;
import com.morev.movies.repository.user.UserRepository;
import com.morev.movies.service.image.ImageService;
import com.morev.movies.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;

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
    public UserDTO getUserById(ObjectId id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserDTO::new).orElse(null);
    }

    @Override
    public void updateUser(String email, UserDTO userDto) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            User newUser = user.get();
            newUser.updateUserByDTO(userDto);
            userRepository.save(newUser);
        }
    }

    @Override
    public void deleteUser(ObjectId id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            String[] path = user.get().getAvatarUrl().split("images/");
            ObjectId newId = new ObjectId(path[1]);
            imageService.deleteImage(newId);
            user.ifPresent(userRepository::delete);
        }
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(userRepository::delete);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDtos = new ArrayList<>();
        users.forEach(user -> {
            UserDTO newUser = new UserDTO(user);
            userDtos.add(newUser);
        });
        return userDtos;
    }
}
