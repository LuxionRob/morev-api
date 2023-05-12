package com.morev.movies.service.auth.impl;

import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.enumeration.Role;
import com.morev.movies.exception.AccountNotFoundException;
import com.morev.movies.model.User;
import com.morev.movies.dto.auth.AuthenticationRequest;
import com.morev.movies.dto.auth.ChangePasswordRequest;
import com.morev.movies.repository.user.UserRepository;
import com.morev.movies.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    @Override
    public String register(AuthenticationRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered!");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User newUser = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.USER);
        userRepository.save(newUser);
        return "Registration successful!";
    }

    @Override
    public UserDTO authenticate(AuthenticationRequest request) throws AccountNotFoundException {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                return new UserDTO(user.get());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }
        throw new AccountNotFoundException("Email not found!");
    }

    @Override
    public String changePassword(ChangePasswordRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user.get());
                return "Change Password succeeded!";
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password incorrect.");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is not existed!");
    }
}
