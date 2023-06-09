package com.morev.movies.dto.auth;

import com.morev.movies.validation.password.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Input must be a valid email address.")
    private String email;
    @ValidPassword
    private String password;
    @ValidPassword
    private String newPassword;
}
