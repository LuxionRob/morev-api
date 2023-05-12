package com.morev.movies.dto.user;

import com.morev.movies.enumeration.Role;
import com.morev.movies.model.User;
import com.morev.movies.utils.requestInstance.OnCreate;
import com.morev.movies.utils.requestInstance.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Id
    private ObjectId id;
    @Null(groups = OnCreate.class)
    private String displayName;

    @Null(groups = OnCreate.class)
    private String fullName;
    @Email(message = "Email not valid")
    private String email;

    @Min(groups = OnUpdate.class, value = 6)
    @Max(groups = OnUpdate.class, value = 200)
    @Null(groups = OnCreate.class)
    private int age;

    @Null(groups = OnCreate.class)
    private String avatarUrl;

    @Null(groups = OnCreate.class)
    private Role role;


    public UserDTO(User user) {
        this.id = user.getId();
        this.displayName = user.getDisplayName();
        this.age = user.getAge();
        this.fullName = user.getFullName();
        this.avatarUrl = user.getAvatarUrl();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
