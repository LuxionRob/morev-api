package com.morev.movies.dto.auth;

import com.morev.movies.validation.password.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @ValidPassword
    private String password;
}
