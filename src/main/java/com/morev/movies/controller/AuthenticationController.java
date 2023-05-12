package com.morev.movies.controller;

import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.dto.auth.AuthenticationRequest;
import com.morev.movies.dto.auth.ChangePasswordRequest;
import com.morev.movies.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(
            @RequestBody @Valid AuthenticationRequest request
    ) throws Exception {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) throws Exception {
        return authenticationService.authenticate(request);

    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public String changePassword(
            @RequestBody @Valid ChangePasswordRequest request
    ) {
        return authenticationService.changePassword(request);

    }

}
