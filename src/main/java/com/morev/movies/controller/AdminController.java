package com.morev.movies.controller;

import com.morev.movies.dto.movie.MovieDTO;
import com.morev.movies.dto.user.UserDTO;
import com.morev.movies.service.movie.MovieService;
import com.morev.movies.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/management")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final MovieService movieService;

    @PostMapping("/movies")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDto) {
        MovieDTO newMovie = movieService.create(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovie);
    }

    @PutMapping("/movies")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO movieDto) {
        MovieDTO updatedMovie = movieService.update(movieDto);
        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/movies/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteMovie(@PathVariable String id) {
        movieService.delete(id);
        return "Delete successfully";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return "Delete successfully";
    }

    @PutMapping("/users")
    @PreAuthorize("hasAuthority('admin:update')")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO userDto) {
        userService.updateUser(userDto.getEmail(), userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }
}
