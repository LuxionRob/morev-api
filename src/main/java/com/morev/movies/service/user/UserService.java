package com.morev.movies.service.user;

import com.morev.movies.dto.user.UserDTO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    boolean isExisted(ObjectId id);
    UserDTO getUserByEmail(String email);

    void updateUser(String email, UserDTO userDto);

    void deleteUser(String email);

}
