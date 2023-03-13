package com.example.moneytransfer.controllers;

import com.example.moneytransfer.payloads.AuthRequest;
import com.example.moneytransfer.payloads.AuthResponse;
import com.example.moneytransfer.payloads.RegistrationRequest;
import com.example.moneytransfer.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(this.authService.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest request) throws Exception {
        return ResponseEntity.ok(this.authService.authenticateUser(request));
    }
}
