package com.morev.movies.service.auth;

import com.morev.movies.dto.auth.*;
import com.morev.movies.exception.AccountNotFoundException;
import com.morev.movies.model.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface AuthenticationService {
    User createUserFromRequest(RegisterRequest request);

    AuthenticationResponse register(RegisterRequest request) throws Exception;

    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;

    AuthenticationResponse changePassword(ChangePasswordRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    boolean verify(String verificationCode);

    boolean resetPassword(String code, ResetPasswordRequest request);

    void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

    void sendForgotPasswordEmail(String email) throws MessagingException, UnsupportedEncodingException, AccountNotFoundException;
}
