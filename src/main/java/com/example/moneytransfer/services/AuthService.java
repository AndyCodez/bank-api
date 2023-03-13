package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Role;
import com.example.moneytransfer.data.entities.User;
import com.example.moneytransfer.data.repositories.UserRepository;
import com.example.moneytransfer.payloads.AuthRequest;
import com.example.moneytransfer.payloads.AuthResponse;
import com.example.moneytransfer.payloads.RegistrationRequest;
import com.example.moneytransfer.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse registerUser(RegistrationRequest request) {
        logger.trace("registerUser");
        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), Role.USER);
        return new AuthResponse(jwtUtil.generateToken(this.userRepository.save(user)));
    }

    public AuthResponse authenticateUser(AuthRequest request) throws Exception {
        logger.trace("authenticateUser");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Username or Password", e);
        }

        var user =  this.userRepository.findByUsername(request.getUsername()).orElseThrow();

        String jwtToken = jwtUtil.generateToken(user);

        return new AuthResponse(jwtToken);
    }
}
