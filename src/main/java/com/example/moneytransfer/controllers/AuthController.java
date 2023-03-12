package com.example.moneytransfer.controllers;

import com.example.moneytransfer.data.entities.User;
import com.example.moneytransfer.payloads.RegistrationRequest;
import com.example.moneytransfer.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(this.authService.registerUser(request));
    }
}
