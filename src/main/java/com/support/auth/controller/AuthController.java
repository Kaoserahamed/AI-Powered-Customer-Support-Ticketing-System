package com.support.auth.controller;

import com.support.auth.dto.LoginRequest;
import com.support.auth.dto.LoginResponse;
import com.support.auth.dto.MfaVerifyRequest;
import com.support.auth.dto.RegisterRequest;
import com.support.auth.entity.User;
import com.support.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request.getFullName(), request.getEmail(), request.getPassword());
        String token = userService.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(
                token, user.getEmail(), user.getRole().name(), user.getFullName(), user.isMfaEnabled()));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<LoginResponse> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerAdmin(request.getFullName(), request.getEmail(), request.getPassword());
        String token = userService.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(
                token, user.getEmail(), user.getRole().name(), user.getFullName(), user.isMfaEnabled()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        boolean authenticated = userService.authenticate(request.getEmail(), request.getPassword());
        if (!authenticated) {
            throw new BadCredentialsException("Invalid email or password");
        }
        User user = userService.findByEmail(request.getEmail());
        String token = userService.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(
                token, user.getEmail(), user.getRole().name(), user.getFullName(), user.isMfaEnabled()));
    }

    @PostMapping("/mfa/enable")
    public ResponseEntity<String> enableMfa() {
        String secret = userService.enableMfa("placeholder@example.com");
        return ResponseEntity.ok(secret);
    }

    @PostMapping("/mfa/verify")
    public ResponseEntity<Boolean> verifyMfa(@Valid @RequestBody MfaVerifyRequest request) {
        boolean valid = userService.verifyMfa("placeholder@example.com", request.getCode());
        return ResponseEntity.ok(valid);
    }
}
