package com.morev.movies.service.auth;

import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.dto.auth.AuthenticationRequest;
import com.morev.movies.dto.auth.ChangePasswordRequest;

public interface AuthenticationService {
    String register(AuthenticationRequest request) throws Exception;

    UserDTO authenticate(AuthenticationRequest request) throws Exception;

    String changePassword(ChangePasswordRequest request);
}
