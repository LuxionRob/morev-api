package com.morev.movies.service.auth.impl;

import com.morev.movies.dto.auth.RegisterRequest;
import com.morev.movies.dto.auth.AuthenticationResponse;
import com.morev.movies.enumeration.Role;
import com.morev.movies.enumeration.TokenType;
import com.morev.movies.model.User;
import com.morev.movies.model.Token;
import com.morev.movies.dto.auth.AuthenticationRequest;
import com.morev.movies.dto.auth.ChangePasswordRequest;
import com.morev.movies.repository.token.TokenRepository;
import com.morev.movies.repository.user.UserRepository;
import com.morev.movies.service.auth.AuthenticationService;
import com.morev.movies.utils.security.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtTokenUtils.generateToken(user);
        var refreshToken = jwtTokenUtils.generateRefreshToken(user);
//        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse changePassword(ChangePasswordRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user.get());
                return AuthenticationResponse.builder().build();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password incorrect.");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is not existed!");
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is existed!");
        } else {
            var newUser = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            var savedUser = userRepository.save(newUser);
            var jwtToken = jwtTokenUtils.generateToken(newUser);
            var refreshToken = jwtTokenUtils.generateRefreshToken(newUser);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)F
                    .build();
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
