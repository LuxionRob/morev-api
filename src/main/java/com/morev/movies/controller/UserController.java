package com.morev.movies.controller;

import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.service.user.UserService;
import com.morev.movies.utils.requestInstance.OnUpdate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<?> getUser() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) user.getPrincipal();
        return ResponseEntity.ok(userService.getUserByEmail(principal.getUsername()));
    }

    @PutMapping()
    @Validated(OnUpdate.class)
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO userDto) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) user.getPrincipal();
        userService.updateUser(principal.getUsername(), userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) user.getPrincipal();
        userService.deleteUserByEmail(principal.getUsername());
        return ResponseEntity.ok("User deleted successfully");
    }
}

