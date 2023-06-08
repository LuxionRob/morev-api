package com.morev.movies.service.user;

import com.morev.movies.dto.user.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    boolean isExisted(String id);
    UserDTO getUserByEmail(String email);

    UserDTO getUserById(String id);

    void updateUser(String email, UserDTO userDto);

    void deleteUserById(String id);

    void deleteUserByEmail(String email);

    List<UserDTO> getAllUsers();
}
