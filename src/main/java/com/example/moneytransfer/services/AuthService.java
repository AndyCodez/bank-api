package com.example.moneytransfer.services;

import com.example.moneytransfer.data.entities.Role;
import com.example.moneytransfer.data.entities.User;
import com.example.moneytransfer.data.repositories.UserRepository;
import com.example.moneytransfer.payloads.AuthRequest;
import com.example.moneytransfer.payloads.AuthResponse;
import com.example.moneytransfer.payloads.RegistrationRequest;
import com.example.moneytransfer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse registerUser(RegistrationRequest request) {
        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), Role.USER);
        return new AuthResponse(jwtUtil.generateToken(this.userRepository.save(user)));
    }

    public AuthResponse authenticateUser(AuthRequest request) throws Exception {
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
