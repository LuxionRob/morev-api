package com.morev.movies.service.auth;

import com.morev.movies.dto.auth.AuthenticationResponse;
import com.morev.movies.dto.auth.RegisterRequest;
import com.morev.movies.dto.auth.AuthenticationRequest;
import com.morev.movies.dto.auth.ChangePasswordRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request) throws Exception;

    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;

    AuthenticationResponse changePassword(ChangePasswordRequest request);
}
