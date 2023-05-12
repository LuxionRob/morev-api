package com.morev.movies.model;

import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.enumeration.Role;
import com.morev.movies.validation.password.ValidPassword;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String displayName;
    private String fullName;
    @Email(message = "Email not valid")
    private String email;
    private int age;
    private String avatarUrl;
    @ValidPassword
    private String password;
    private Role role;

    public User(UserDTO dto) {
        this.id = dto.getId();
        this.displayName = dto.getDisplayName();
        this.fullName = dto.getFullName();
        this.age = dto.getAge();
        this.avatarUrl = dto.getAvatarUrl();
    }

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
