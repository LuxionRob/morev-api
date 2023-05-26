package com.morev.movies.service.sercurity;

import com.morev.movies.model.User;
import com.morev.movies.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            User newUser = user.get();

            return new org.springframework.security.core.userdetails.User(
                    newUser.getUsername(),
                    newUser.getPassword(),
                    newUser.getAuthorities()
            );
        } else {
            return new org.springframework.security.core.userdetails.User(
                    "invalid",
                    "invalid",
                    false,
                    false,
                    false,
                    false,
                    Collections.singleton(new SimpleGrantedAuthority("USER"))
            );
        }
    }
}
