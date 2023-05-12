package com.morev.movies.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Input must be a valid email address.")
    private String email;
    private String password;
}
