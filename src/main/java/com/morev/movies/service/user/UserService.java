package com.morev.movies.service.user;

import com.morev.movies.dto.user.UserDTO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    boolean isExisted(ObjectId id);

    UserDTO create(UserDTO userDTO);

    Object getUserById(ObjectId id);

    void updateUser(ObjectId id, UserDTO userDto);

    void deleteUser(ObjectId id);

}
