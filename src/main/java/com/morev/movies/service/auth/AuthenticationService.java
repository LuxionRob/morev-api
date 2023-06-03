package com.morev.movies.service.auth;

import com.morev.movies.dto.auth.AuthenticationResponse;
import com.morev.movies.dto.auth.RegisterRequest;
import com.morev.movies.dto.auth.AuthenticationRequest;
import com.morev.movies.dto.auth.ChangePasswordRequest;
import com.morev.movies.model.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request) throws Exception;

    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;

    AuthenticationResponse changePassword(ChangePasswordRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String verificationCode);
}
