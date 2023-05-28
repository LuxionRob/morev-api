package com.morev.movies.service.user;

import com.morev.movies.dto.user.UserDTO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    boolean isExisted(ObjectId id);
    UserDTO getUserByEmail(String email);

    UserDTO getUserById(ObjectId id);

    void updateUser(String email, UserDTO userDto);

    void deleteUser(ObjectId id);

    void deleteUser(String email);

    List<UserDTO> getAllUsers();
}
