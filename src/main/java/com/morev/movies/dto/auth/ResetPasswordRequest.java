package com.morev.movies.dto.auth;

import com.morev.movies.validation.password.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResetPasswordRequest {
    @ValidPassword
    private String password;
    @ValidPassword
    private String retypePassword;

    public boolean isMatches() {
        return Objects.equals(password, retypePassword);
    }
}
