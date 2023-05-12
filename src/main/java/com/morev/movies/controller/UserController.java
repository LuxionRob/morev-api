package com.morev.movies.controller;

import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.service.user.UserService;
import com.morev.movies.utils.requestInstance.OnUpdate;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Validated(OnUpdate.class)
    public ResponseEntity<?> updateUser(@PathVariable ObjectId id, @RequestBody @Valid UserDTO userDto) {
        userService.updateUser(id, userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}

