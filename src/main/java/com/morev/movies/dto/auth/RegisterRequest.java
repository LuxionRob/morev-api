package com.morev.movies.dto.auth;

import com.morev.movies.validation.password.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Input must be a valid email address.")
    private String email;
    @NotBlank(message = "Full name cannot be blank.")
    @Size.List({
            @Size(min = 2, message = "Full name is too short"),
            @Size(max = 10, message = "Full name is too long")
    })
    private String fullName;
    @ValidPassword
    private String password;
}
