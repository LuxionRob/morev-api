package com.morev.movies.controller;

import com.morev.movies.dto.auth.*;
import com.morev.movies.exception.AccountNotFoundException;
import com.morev.movies.service.auth.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse register(
            @RequestBody @Valid RegisterRequest request
    ) throws Exception {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) throws Exception {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse changePassword(
            @RequestBody @Valid ChangePasswordRequest request
    ) {
        return authenticationService.changePassword(request);

    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String verifyUser(@Param("code") String code) {
        if (authenticationService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
    @GetMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String sendForgot(@Param("email") String email) throws MessagingException, UnsupportedEncodingException, AccountNotFoundException {
        authenticationService.sendForgotPasswordEmail(email);
        return "email_has_sent";
    }
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String resetPassword(@Param("code") String code, @RequestBody @Valid ResetPasswordRequest request) {
        if (authenticationService.resetPassword(code, request)) {
            return "reset_success";
        } else {
            return "reset_fail";
        }
    }
}
