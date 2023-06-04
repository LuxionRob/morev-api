package com.morev.movies.service.auth.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morev.movies.dto.auth.*;
import com.morev.movies.enumeration.Role;
import com.morev.movies.enumeration.TokenType;
import com.morev.movies.exception.AccountNotFoundException;
import com.morev.movies.model.User;
import com.morev.movies.model.Token;
import com.morev.movies.repository.token.TokenRepository;
import com.morev.movies.repository.user.UserRepository;
import com.morev.movies.service.auth.AuthenticationService;
import com.morev.movies.utils.security.JwtTokenUtils;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;
    @Value("${CLIENT_DOMAIN}")
    private String domain;
    @Value("${CLIENT_PORT}")
    private String port;
    @Value("${API_VERSION}")
    private String apiVersion;

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
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
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

    public AuthenticationResponse register(RegisterRequest request) throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is existed!");
        } else {
            String randomCode = this.randomString();
            var newUser = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .fullName(request.getFullName())
                    .role(Role.USER)
                    .enabled(false)
                    .verificationCode(randomCode)
                    .build();
            var savedUser = userRepository.save(newUser);
            var jwtToken = jwtTokenUtils.generateToken(newUser);
            var refreshToken = jwtTokenUtils.generateRefreshToken(newUser);
            saveUserToken(savedUser, jwtToken);
            sendVerificationEmail(newUser);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
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

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtTokenUtils.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtTokenUtils.isTokenValid(refreshToken, user)) {
                var accessToken = jwtTokenUtils.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private String randomString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 64;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }


    @Override
    public void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String siteURL = "http://" + domain + ":" + port + "/api/" + apiVersion + "/auth";
        String toAddress = user.getEmail();
        String fromAddress = "aidaynhi8@gmail.com";
        String senderName = "Morev Support";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);
        System.out.println("Sending");
        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        Optional<User> user = userRepository.findByVerificationCode(verificationCode);

        if (user.isEmpty() || user.get().isEnabled()) {
            return false;
        } else {
            user.get().setVerificationCode(null);
            user.get().setEnabled(true);
            userRepository.save(user.get());

            return true;
        }
    }

    @Override
    public boolean resetPassword(String code, ResetPasswordRequest request) {
        Optional<User> user = userRepository.findByForgotPasswordCode(code);
        if (user.isPresent()) {
            if (request.isMatches()) {
                user.get().setPassword(passwordEncoder.encode(request.getPassword()));
                user.get().setForgotPasswordCode(null);
                userRepository.save(user.get());
                return true;
            } else {
                throw new InvalidRequestStateException("Password must be match");
            }
        } else {
            return false;
        }
    }

    @Override
    public void sendForgotPasswordEmail(String email) throws MessagingException, UnsupportedEncodingException, AccountNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }
        String code = randomString();
        user.get().setForgotPasswordCode(code);
        userRepository.save(user.get());

        String siteURL = "http://" + domain + ":" + port + "/api/" + apiVersion + "/auth";
        String toAddress = email;
        String fromAddress = "aidaynhi8@gmail.com";
        String senderName = "Morev Support";
        String subject = "Your Morev Account: Forgot password";
        String content = "Dear [[name]],<br>"
                + "You have requested to reset your password.<br>"
                + "Click the link below to change your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Company ThieuNang.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.get().getFullName());
        String verifyURL = siteURL + "/reset-password?code=" + code;

        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
