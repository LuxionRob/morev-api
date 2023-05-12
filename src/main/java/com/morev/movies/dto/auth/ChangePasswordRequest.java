package com.morev.movies.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ChangePasswordRequest {
    private String email;
    private String password;
    private String newPassword;
}
