package com.morev.movies.controller;

import com.morev.movies.dto.auth.AuthenticationResponse;
import com.morev.movies.dto.auth.RegisterRequest;
import com.morev.movies.dto.auth.AuthenticationRequest;
import com.morev.movies.dto.auth.ChangePasswordRequest;
import com.morev.movies.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
