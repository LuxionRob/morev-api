package com.morev.movies.utils.config;

import com.morev.movies.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.morev.movies.enumeration.Permission.*;
import static com.morev.movies.enumeration.Role.ADMIN;
import static com.morev.movies.enumeration.Role.REVIEWER;
import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/movies",
                        "/api/v1/movies/**",
                        "/api/v1/reviews",
                        "/api/v1/auth/**",
                        "/api/v1/images/**",
                        "/api/v1/test"
                )
                .permitAll()

                .requestMatchers("/api/v1/management/**").hasRole(ADMIN.name())
                .requestMatchers(GET, "/api/v1/management/**").hasAuthority(ADMIN_READ.name())
                .requestMatchers(POST, "/api/v1/management/**").hasAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/api/v1/management/**").hasAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/management/**").hasAuthority(ADMIN_DELETE.name())

                .requestMatchers("/api/v1/review/**").hasRole(REVIEWER.name())
                .requestMatchers(GET, "/api/v1/review/**").hasAuthority(REVIEWER_READ.name())
                .requestMatchers(POST, "/api/v1/review/**").hasAuthority(REVIEWER_CREATE.name())
                .requestMatchers(PUT, "/api/v1/review/**").hasAuthority(REVIEWER_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/review/**").hasAuthority(REVIEWER_DELETE.name())

                .anyRequest()
                .authenticated()
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
