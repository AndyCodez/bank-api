package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Role;
import com.example.moneytransfer.data.entities.User;
import com.example.moneytransfer.data.repositories.UserRepository;
import com.example.moneytransfer.payloads.AuthResponse;
import com.example.moneytransfer.payloads.RegistrationRequest;
import com.example.moneytransfer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtUtil jwtUtil;
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse registerUser(RegistrationRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), request.getPassword(), Role.USER);

        var savedUser = this.userRepository.save(user);

        return new AuthResponse(jwtUtil.generateToken(user));
    }
}
